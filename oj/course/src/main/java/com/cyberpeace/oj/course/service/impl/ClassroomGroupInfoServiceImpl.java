package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupArchive;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupArchiveMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.ClassroomGroupInfoService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClassroomGroupInfoServiceImpl extends ServiceImpl<ClassroomGroupInfoMapper, ClassroomGroupInfo> implements ClassroomGroupInfoService {

    @Autowired
    private ClassroomMapper classroomMapper;

    @Autowired
    private ClassroomGroupInfoMapper classroomGroupInfoMapper;

    @Autowired
    private CourseLessonMapper courseLessonMapper;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private OjLocalSceneMapper ojLocalSceneMapper;

    @Autowired
    private ClassroomGroupArchiveMapper classroomGroupArchiveMapper;

    @Autowired
    private OjLocalSceneService ojLocalSceneService;

    @Autowired
    private LocalSceneMapper localSceneMapper;

    @Autowired
    private MyWebSocketHandler myWebSocketHandler;

    @Autowired
    private ClassroomValidateHandler classroomValidateHandler;

    @Override
    public ClassroomGroupInfo selectByLessonAndUser(String lessonId, String roomId, AuthContext authContext) {

        if (authContext.getIsWeb() && StringUtils.isNotBlank(roomId)) {
            // 查询课堂信息
            Classroom classroom = classroomMapper.selectById(roomId);
            if (null == classroom) {
                return null;
            }

            // 查询分组信息
            ClassroomGroupInfo groupInfo = classroomGroupInfoMapper
                    .selectByRoomAndUser(classroom.getResourceId(), authContext.getUserId());
            if (null == groupInfo) {
                return null;
            }

            return groupInfo;
        }

        return null;
    }

    @Override
    public List<GroupInfoBo> getGroupInfoByRoom(String classroomId, AuthContext authContext) {

        // 数据校验
        Classroom classroom = validateRoom(classroomId);
        CourseLesson courseLesson = validateLesson(classroom);

        List<UserInfo> userInfos = remoteUserService.ListByOrgId(classroom.getOrganizationId(),
                true);

        List<String> orgUserIds = StreamApiUtils.map(userInfos, UserInfo::getUserId);
        List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper.selectByRoom(classroomId);

        List<String> classroomUserIds = groupInfos.stream()
                .map(classroomGroupInfo -> JsonUtil
                        .toJsonObjectArray(classroomGroupInfo.getGroupUserIds(), String.class))
                .flatMap(Collection::stream).collect(Collectors.toList());

        // 计算新用户 给新用户创建组
        List<String> newUserIds = (List<String>) CollectionUtils.removeAll(orgUserIds,
                classroomUserIds);
        Integer lastGroupNo = classroomGroupInfoMapper.selectLastGroupByRoom(classroomId);
        List<ClassroomGroupInfo> groupInfoList = initClassroomGroupInfo(classroomId, courseLesson,
                newUserIds,
                null != lastGroupNo ? lastGroupNo + CommonConstant.ONE : CommonConstant.ONE);

        // 计算删除的用户 将用户从组中删除 创建者可能不在本组织内
        List<String> deleteUserIds = (List<String>) CollectionUtils.removeAll(classroomUserIds,
                orgUserIds);
        if (CollectionUtils.isNotEmpty(deleteUserIds)) {
            deleteUserIds.remove(classroom.getCreateUserId());
        }

        List<ClassroomGroupInfo> updateGroups = getUpdateAndDeleteNoneGroup(classroomId,
                deleteUserIds);

        // 更新组织信息
        List<String> orgIds = classroomValidateHandler
                .validateClassroomOrgHasUser(classroom.getOrganizationId());

        String orgIdsString = JsonUtil.arrayToString(orgIds);
        if (!StringUtils.equals(classroom.getOrganizationIds(), orgIdsString)) {
            classroom.setOrganizationIds(orgIdsString);
            classroomMapper.updateById(classroom);
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                if (CollectionUtils.isNotEmpty(groupInfoList)) {
                    saveBatch(groupInfoList);
                }
                if (CollectionUtils.isNotEmpty(updateGroups)) {
                    updateBatchById(updateGroups);
                }
            }
        });

        // 数据合并 数据类型转换
        groupInfoList.addAll(updateGroups);
        List<ClassroomGroupInfo> sorted = StreamApiUtils.sortByColumn(groupInfoList,
                ClassroomGroupInfo::getGroupNo);
        Map<String, UserInfo> userInfoMap = remoteUserService.allUsers();
        List<String> groupIds = groupInfos.stream().map(ClassroomGroupInfo::getResourceId).collect(Collectors.toList());
        Map<String, OjLocalScene> localSceneMap = ojLocalSceneMapper.selectByGroupIds(groupIds).stream().collect(Collectors.toMap(OjLocalScene::getBusinessId, OjLocalScen -> OjLocalScen));
        return StreamApiUtils.map(sorted,
                classroomGroupInfo -> entityTBoNew(classroomGroupInfo, userInfoMap, localSceneMap));
    }

    @Override
    public List<GroupInfoBo> updateGroupInfo(String classroomId, Integer groupCount) {
        // 基本数据校验
        Classroom classroom = validateRoom(classroomId);
        CourseLesson courseLesson = validateLesson(classroom);

        // 场景数据校验
        List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper.selectByRoom(classroomId);
        enableUpdateGroup(groupInfos);

        List<String> classroomUserIds = groupInfos.stream()
                .map(classroomGroupInfo -> JsonUtil
                        .toJsonObjectArray(classroomGroupInfo.getGroupUserIds(), String.class))
                .flatMap(Collection::stream).distinct().collect(Collectors.toList());

        // 无用户直接返回
        if (CollectionUtils.isEmpty(classroomUserIds)) {
            return Lists.newArrayList();
        }

        int remainUser = classroomUserIds.size() % groupCount;
        int totalGroup = remainUser == CommonConstant.ZERO ? (classroomUserIds.size() / groupCount)
                : (classroomUserIds.size() / groupCount) + CommonConstant.ONE;

        int start = CommonConstant.ZERO;
        int end = groupCount;
        List<ClassroomGroupInfo> groupInfoList = Lists.newArrayList();

        // 实体类数据组装
        for (int i = 1; i < totalGroup + 1; i++) {
            ClassroomGroupInfo groupInfo = new ClassroomGroupInfo();
            groupInfo.setGroupNo(i);
            groupInfo.setClassroomId(classroomId);
            groupInfo.setResourceId(IDGeneratorUtil.getID());
            groupInfo.setLessonId(courseLesson.getResourceId());
            groupInfo.setTopoId(courseLesson.getTopoId());
            groupInfo.setSceneShareMode(courseLesson.getSceneShareMode());
            groupInfo.setDestroyDelay(courseLesson.getDestroyDelay());

            // 用户数据处理
            if (classroomUserIds.size() < end) {
                end = classroomUserIds.size();
            }
            groupInfo.setGroupUserIds(JsonUtil.arrayToString(classroomUserIds.subList(start, end)));
            start = end;
            end += groupCount;
            groupInfoList.add(groupInfo);
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                classroomGroupInfoMapper.deleteByRoom(classroomId);
                saveBatch(groupInfoList);
            }
        });

        List<ClassroomGroupInfo> sorted = StreamApiUtils.sortByColumn(groupInfoList,
                ClassroomGroupInfo::getGroupNo);
        Map<String, UserInfo> userInfoMap = remoteUserService.allUsers();
        return StreamApiUtils.map(sorted, groupInfo -> entityTBo(groupInfo, userInfoMap));
    }

    @Override
    public Integer saveGroupInfo(String classroomId, String name, List<GroupInfoBo> data) {

        // 数据校验
        validateRoom(classroomId);

        ClassroomGroupArchive archive = classroomGroupArchiveMapper.selectByRoomAndName(classroomId,
                name);
        if (null != archive) {
            log.error("ClassroomGroupArchive name is exist! classroom_id=[{}], name=[{}]",
                    classroomId, name);
            throw new BusinessException(ErrorCode.DUPLICATE_KEY);
        }

        ClassroomGroupArchive groupArchive = new ClassroomGroupArchive();
        groupArchive.setGroups(JsonUtil.arrayToString(data));
        groupArchive.setName(name);
        groupArchive.setClassroomId(classroomId);

        return classroomGroupArchiveMapper.insert(groupArchive);
    }

    @Override
    public Integer deleteClassroomArchive(String classroomId, String groupArchiveId) {
        // 数据校验
        validateRoom(classroomId);

        return classroomGroupArchiveMapper.deleteById(groupArchiveId);
    }

    @Override
    public List<GroupInfoBo> userGroupInfo(String classroomId, String groupArchiveId) {

        // 基本数据校验
        Classroom classroom = validateRoom(classroomId);
        CourseLesson courseLesson = validateLesson(classroom);

        // 场景数据校验
        List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper.selectByRoom(classroomId);
        enableUpdateGroup(groupInfos);

        ClassroomGroupArchive archive = classroomGroupArchiveMapper.selectById(groupArchiveId);
        if (null == archive) {
            log.error("ClassroomGroupArchive not found! classroomGroupArchive_id=[{}]",
                    groupArchiveId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        // 这个时候肯定没有场景启动 所以不用查询场景信息
        List<ArchiveGroupBo> groupBos = JsonUtil.toJsonObjectArray(archive.getGroups(),
                ArchiveGroupBo.class);

        List<ClassroomGroupInfo> groupInfoList = StreamApiUtils.map(groupBos,
                archiveGroupBo -> BeanUtils.copy(archiveGroupBo, ClassroomGroupInfo.class));
        resetEntity(groupInfoList, courseLesson, classroom);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                classroomGroupInfoMapper.deleteByRoom(classroomId);
                if (CollectionUtils.isNotEmpty(groupInfoList)) {
                    saveBatch(groupInfoList);
                }
            }
        });

        List<ClassroomGroupInfo> sorted = StreamApiUtils.sortByColumn(groupInfoList,
                ClassroomGroupInfo::getGroupNo);
        Map<String, UserInfo> userInfoMap = remoteUserService.allUsers();
        List<String> groupIds = groupInfos.stream().map(ClassroomGroupInfo::getResourceId).collect(Collectors.toList());
        Map<String, OjLocalScene> localSceneMap = ojLocalSceneMapper.selectByGroupIds(groupIds).stream().collect(Collectors.toMap(OjLocalScene::getBusinessId, OjLocalScen -> OjLocalScen));
        return StreamApiUtils.map(sorted, groupInfo -> entityTBoNew(groupInfo, userInfoMap, localSceneMap));
    }

    /**
     * 使用模板时重新设置分组数据
     *
     * @param groupInfos
     * @param courseLesson
     * @return
     */
    private void resetEntity(List<ClassroomGroupInfo> groupInfos, CourseLesson courseLesson,
                             Classroom classroom) {

        for (ClassroomGroupInfo groupInfo : groupInfos) {
            groupInfo.setDestroyDelay(courseLesson.getDestroyDelay());
            groupInfo.setClassroomId(classroom.getResourceId());
            groupInfo.setTopoId(courseLesson.getTopoId());
            groupInfo.setLessonId(courseLesson.getResourceId());
            groupInfo.setSceneShareMode(courseLesson.getSceneShareMode());
        }

    }

    @Override
    public Boolean setGroupSceneDelay(String classroomId, Boolean end) {

        // 基本数据校验
        Classroom classroom = validateRoom(classroomId);
        CourseLesson courseLesson = validateLesson(classroom);

        // 查询分组场景 并根据是否延时设置时间
        List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper.selectByRoom(classroomId);

        // 统计更新数据
        List<LocalScene> localScenes = Lists.newArrayList();
        List<ClassroomGroupInfo> groupInfoList = Lists.newArrayList();

		// 查询localScene数据
		List<String> groupInfoIdList = groupInfos.stream().map(ClassroomGroupInfo::getResourceId)
				.collect(Collectors.toList());
		List<OjLocalScene> ojLocalScenes = Lists.newArrayList();
		if (!groupInfoIdList.isEmpty()) {
			ojLocalScenes = ojLocalSceneMapper.selectByGroupIds(groupInfoIdList);
		}
		Map<String, OjLocalScene> ojLocalSceneMap = StreamApiUtils.toMap(ojLocalScenes,
				OjLocalScene::getBusinessId, item -> item);
		List<String> localSceneIdList = ojLocalScenes.stream().map(OjLocalScene::getLocalSceneId)
				.collect(Collectors.toList());
		List<LocalScene> localSceneList = Lists.newArrayList();
		if (!localSceneIdList.isEmpty()) {
			localSceneList = localSceneMapper.selectBatchIds(localSceneIdList);
		}
		Map<String, LocalScene> localSceneMap = StreamApiUtils.toMap(localSceneList,
				LocalScene::getResourceId, localScene -> localScene);

		for (ClassroomGroupInfo groupInfo : groupInfos) {
			OjLocalScene ojLocalScene = ojLocalSceneMap.get(groupInfo.getResourceId());
			// 需要更新的场景数据
			if (null != ojLocalScene) {
				LocalScene localScene = localSceneMap.get(ojLocalScene.getLocalSceneId());

                if (!end) {
                    localScene.setStartTime(new Date());
                }

                localScene.setEndTime(!end ? TimeUtil.addHours(localScene.getStartTime(),
                        courseLesson.getDestroyDelay()) : null);
                localScene.setSceneTtl(
                        !end ? (courseLesson.getDestroyDelay() * CommonConstant.SIXTY) : null);
                localScenes.add(localScene);
            }

            // 需要更新的分组数据
            String destroyCloseStr = ConfigUtil.getContextProperty("oj.destroy.delay.close");
            groupInfo.setDestroyDelay(
                    end ? Integer.parseInt(destroyCloseStr) : courseLesson.getDestroyDelay());

            groupInfoList.add(groupInfo);
        }

        // 数据更新
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                if (CollectionUtils.isNotEmpty(localScenes)) {
                    localScenes.forEach(localScene -> localSceneMapper.updateEndTime(localScene));
                }
                if (CollectionUtils.isNotEmpty(groupInfoList)) {
                    groupInfoList
                            .forEach(groupInfo -> classroomGroupInfoMapper.updateById(groupInfo));
                }
            }
        });

        if (CollectionUtils.isNotEmpty(localScenes)) {
            List<String> ojLocalSceneIds = StreamApiUtils.map(localScenes,
                    LocalScene::getBusinessInstanceId);
            List<OjLocalScene> ojLocalSceneList = ojLocalSceneMapper
                    .selectBatchIds(ojLocalSceneIds);
            ojLocalSceneList.forEach(ojLocalScene -> pushGroupSyncMessage(ojLocalScene));
        }

        return true;
    }

    /**
     * 推送数据延时消息
     *
     * @param ojLocalScene
     */
    private void pushGroupSyncMessage(OjLocalScene ojLocalScene) {
        GroupWsMessageIdentifier identifier = new GroupWsMessageIdentifier();
        identifier.setPath(WsPath.CLASSROOM_GROUP_SCENE);
        identifier.setGroupId(ojLocalScene.getBusinessId());
        identifier.setUserId(ojLocalScene.getUserId());

        JSONObject data = new JSONObject();
        data.put(JsonKeyConstant.CODE, OjEvent.SCENE_DELETE_SYNC.getCode());
        data.put(JsonKeyConstant.DATA, CommonConstant.EMPTY_STRING);

        WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
                .builder().messageIdentifier(identifier).data(data).build();
        myWebSocketHandler.pushMessage(messageContext, true);
    }

    @Override
    public Boolean getGroupSceneDelayStatus(String classroomId) {

        List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper.selectByRoom(classroomId);
        for (ClassroomGroupInfo groupInfo : groupInfos) {
            if (groupInfo.getDestroyDelay().intValue() == Integer
                    .parseInt(ConfigUtil.getContextProperty("oj.destroy.delay.close"))) {
                return CommonConstant.TRUE;
            }
        }

        return CommonConstant.FALSE;
    }

    @Override
    public List<ClassroomGroupArchiveBo> selectGroupArchive(String classroomId) {

        // 基本数据校验
        Classroom classroom = validateRoom(classroomId);
        validateLesson(classroom);

        List<ClassroomGroupArchive> archiveList = classroomGroupArchiveMapper
                .selectByRoom(classroomId);
        return StreamApiUtils.map(archiveList,
                archive -> BeanUtils.copy(archive, ClassroomGroupArchiveBo.class));

    }

    @Override
    public Boolean moveGroupUser(String classroomId, String fromGroupId, String toGroupId,
                                 String userId) {

        // 基本数据校验
        validateRoom(classroomId);
        ClassroomGroupInfo fromGroup = validateGroup(fromGroupId);
        ClassroomGroupInfo toGroup = validateGroup(toGroupId);

        // 移动用户
        List<String> fromUserIds = JsonUtil.toJsonObjectArray(fromGroup.getGroupUserIds(),
                String.class);
        fromUserIds.remove(userId);
        fromGroup.setGroupUserIds(JsonUtil.arrayToString(fromUserIds));

        List<String> toUserIds = JsonUtil.toJsonObjectArray(toGroup.getGroupUserIds(),
                String.class);
        toUserIds.add(userId);
        toGroup.setGroupUserIds(JsonUtil.arrayToString(toUserIds));

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                classroomGroupInfoMapper.updateById(fromGroup);
                classroomGroupInfoMapper.updateById(toGroup);
            }
        });

        return true;
    }

    /**
     * 初始化用户分组信息
     *
     * @param classroomId
     * @param courseLesson
     * @param userIds
     * @param groupNo
     * @return
     */
    @Override
    public List<ClassroomGroupInfo> initClassroomGroupInfo(String classroomId,
                                                           CourseLesson courseLesson, List<String> userIds, Integer groupNo) {

        List<ClassroomGroupInfo> groupInfos = Lists.newArrayList();

        // 数据初始化
        for (String userId : userIds) {
            ClassroomGroupInfo groupInfo = new ClassroomGroupInfo();
            groupInfo.setResourceId(IDGeneratorUtil.getID());
            groupInfo.setGroupNo(groupNo++);
            groupInfo.setClassroomId(classroomId);
            groupInfo.setGroupUserIds(JsonUtil.arrayToString(Arrays.asList(userId)));
            groupInfo.setDestroyDelay(courseLesson.getDestroyDelay());
            groupInfo.setSceneShareMode(SceneShare.GROUP.getCode());
            groupInfo.setLessonId(courseLesson.getResourceId());
            groupInfo.setTopoId(courseLesson.getTopoId());
            groupInfos.add(groupInfo);
        }

        return groupInfos;
    }

    /**
     * 校验场景是否存在
     *
     * @param groupInfos
     */
    public void enableUpdateGroup(List<ClassroomGroupInfo> groupInfos) {
        List<String> groupIds = groupInfos.stream().map(ClassroomGroupInfo::getResourceId).collect(Collectors.toList());
//		for (ClassroomGroupInfo groupInfo : groupInfos) {
        List<OjLocalScene> ojLocalScene = ojLocalSceneMapper
                .selectByGroupIds(groupIds);
        if (ojLocalScene.size() > 0) {
            log.error("Group scene is running! ojLocalScene=[{}]",
                    ojLocalScene.toString());
            throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
        }
//		}
    }

    /**
     * 数据类型转换
     *
     * @param groupInfo
     * @return
     */
    private GroupInfoBo entityTBo(ClassroomGroupInfo groupInfo, Map<String, UserInfo> userInfoMap) {

        GroupInfoBo groupInfoBo = BeanUtils.copy(groupInfo, GroupInfoBo.class);
        List<String> userIds = JsonUtil.toJsonObjectArray(groupInfo.getGroupUserIds(),
                String.class);

        // 中台查询用户信息
        if (CollectionUtils.isNotEmpty(userIds)) {
            List<SimpleUserData> userData = StreamApiUtils.map(userIds, user -> {
                UserInfo userInfo = userInfoMap.get(user);
                if (null == userInfo) {
                    return null;
                }
                return BeanUtils.copy(userInfo, SimpleUserData.class);
            });
            groupInfoBo.setUserInfos(userData);
        }

        // 场景数据查询
        OjLocalScene ojLocalScene = ojLocalSceneMapper.selectGroupScene(groupInfo.getResourceId());
        if (null != ojLocalScene) {
            JSONObject sceneData = ojLocalSceneService.getSceneData(ojLocalScene);
            groupInfoBo.setSceneData(sceneData);
        }

        return groupInfoBo;
    }

    /**
     * 数据类型转换
     *
     * @param groupInfo
     * @return
     */
    private GroupInfoBo entityTBoNew(ClassroomGroupInfo groupInfo, Map<String, UserInfo> userInfoMap, Map<String, OjLocalScene> localSceneMap) {

        GroupInfoBo groupInfoBo = BeanUtils.copy(groupInfo, GroupInfoBo.class);
        List<String> userIds = JsonUtil.toJsonObjectArray(groupInfo.getGroupUserIds(),
                String.class);

        // 中台查询用户信息
        if (CollectionUtils.isNotEmpty(userIds)) {
            List<SimpleUserData> userData = StreamApiUtils.map(userIds, user -> {
                UserInfo userInfo = userInfoMap.get(user);
                if (null == userInfo) {
                    return null;
                }
                return BeanUtils.copy(userInfo, SimpleUserData.class);
            });
            groupInfoBo.setUserInfos(userData);
        }

        // 场景数据查询
//        OjLocalScene ojLocalScene = ojLocalSceneMapper.selectGroupScene(groupInfo.getResourceId());

        if (localSceneMap.containsKey(groupInfo.getResourceId())) {
            JSONObject sceneData = ojLocalSceneService.getSceneData(localSceneMap.get(groupInfo.getResourceId()));
            groupInfoBo.setSceneData(sceneData);
        }

        return groupInfoBo;
    }

    /**
     * 获取需要修改的组 删除空组(用户被删除导致组员为空)
     *
     * @return
     */
    private List<ClassroomGroupInfo> getUpdateAndDeleteNoneGroup(String classroomId,
                                                                 List<String> deleteUserIds) {
        // 数据查询 校验
        List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper.selectByRoom(classroomId);
        if (CollectionUtils.isEmpty(groupInfos)) {
            return Lists.newArrayList();
        }

        // 组数据map
        Map<String, ClassroomGroupInfo> groupInfoMap = StreamApiUtils.toMap(groupInfos,
                ClassroomGroupInfo::getResourceId, groupInfo -> groupInfo);

        // 获取组与用户的关系数据
        Map<String, List<String>> groupUserMap = StreamApiUtils.toMap(groupInfos,
                ClassroomGroupInfo::getResourceId,
                groupInfo -> JsonUtil.toJsonObjectArray(groupInfo.getGroupUserIds(), String.class));

        // 获取用户与组的关系
        Map<String, String> userGroupMap = Maps.newHashMap();
        for (ClassroomGroupInfo groupInfo : groupInfos) {
            List<String> groupUserIds = JsonUtil.toJsonObjectArray(groupInfo.getGroupUserIds(),
                    String.class);
            for (String groupUserId : groupUserIds) {
                userGroupMap.put(groupUserId, groupInfo.getResourceId());
            }
        }

        // 删除空组 返回修改组
        for (String deleteUserId : deleteUserIds) {
            // 获取组编号
            String groupId = userGroupMap.get(deleteUserId);

            // 删除组用户 删除无用户组
            groupUserMap.get(groupId).remove(deleteUserId);
            if (CollectionUtils.isEmpty(groupUserMap.get(groupId))) {
                groupUserMap.remove(groupId);
                classroomGroupInfoMapper.deleteById(groupId);
            }
        }

        // 获取更新组数据
        List<ClassroomGroupInfo> updateGroup = Lists.newArrayList();
        for (Map.Entry<String, List<String>> entry : groupUserMap.entrySet()) {
            ClassroomGroupInfo groupInfo = groupInfoMap.get(entry.getKey());
            groupInfo.setGroupUserIds(JsonUtil.arrayToString(entry.getValue()));
            updateGroup.add(groupInfo);
        }

        return updateGroup;
    }

    /**
     * 课堂数据校验
     *
     * @param classroomId
     * @return
     */
    private Classroom validateRoom(String classroomId) {

        Classroom classroom = classroomMapper.selectById(classroomId);
        if (null == classroom) {
            log.error("Classroom not found! classroom_id=[{}]", classroomId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        return classroom;
    }

    /**
     * 课时数据校验
     *
     * @param classroom
     * @return
     */
    private CourseLesson validateLesson(Classroom classroom) {
        CourseLesson courseLesson = courseLessonMapper.selectById(classroom.getLessonId());
        if (null == courseLesson) {
            log.error("Courselesson not found! lesson_id=[{}]", classroom.getLessonId());
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        return courseLesson;
    }

    /**
     * 组数据
     *
     * @param groupId
     * @return
     */
    private ClassroomGroupInfo validateGroup(String groupId) {
        ClassroomGroupInfo groupInfo = classroomGroupInfoMapper.selectById(groupId);
        if (null == groupInfo) {
            log.error("ClassroomGroupInfo not found! group_id=[{}]", groupId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return groupInfo;
    }
}
