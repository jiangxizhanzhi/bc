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

/**
 * 课堂组信息Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see ServiceImpl
 * @see ClassroomGroupInfoService
 * @since 1.0.0
 */
@Slf4j
@Service
public class ClassroomGroupInfoServiceImpl extends ServiceImpl<ClassroomGroupInfoMapper, ClassroomGroupInfo> implements ClassroomGroupInfoService {

    /**
     * 课堂Mapper
     *
     * @see ClassroomMapper
     */
    @Autowired
    private ClassroomMapper classroomMapper;

    /**
     * 课堂组信息Mapper
     *
     * @see ClassroomGroupInfoMapper
     */
    @Autowired
    private ClassroomGroupInfoMapper classroomGroupInfoMapper;

    /**
     * 课程课时Mapper
     *
     * @see CourseLessonMapper
     */
    @Autowired
    private CourseLessonMapper courseLessonMapper;

    /**
     * 远程用户Service
     *
     * @see RemoteUserService
     */
    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * 事务模板
     *
     * @see TransactionTemplate
     */
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * OJ当地场景Mapper
     *
     * @see OjLocalSceneMapper
     */
    @Autowired
    private OjLocalSceneMapper ojLocalSceneMapper;

    /**
     * 课堂组档案Mapper
     *
     * @see ClassroomGroupArchiveMapper
     */
    @Autowired
    private ClassroomGroupArchiveMapper classroomGroupArchiveMapper;

    /**
     * OJ当地场景Service
     *
     * @see OjLocalSceneService
     */
    @Autowired
    private OjLocalSceneService ojLocalSceneService;

    /**
     * 当地场景Mapper
     *
     * @see LocalSceneMapper
     */
    @Autowired
    private LocalSceneMapper localSceneMapper;

    /**
     * 我的WebSocket处理器
     *
     * @see MyWebSocketHandler
     */
    @Autowired
    private MyWebSocketHandler myWebSocketHandler;

    /**
     * 课堂验证处理器
     *
     * @see ClassroomValidateHandler
     */
    @Autowired
    private ClassroomValidateHandler classroomValidateHandler;

    /**
     * 查询由课时和用户
     *
     * @param lessonId    课时id
     * @param roomId      房间id
     * @param authContext auth 上下文
     * @return {@code ClassroomGroupInfo }
     * @throws
     * @see String
     * @see String
     * @see AuthContext
     * @see ClassroomGroupInfo
     */
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

    /**
     * 获取组信息由房间
     *
     * @param classroomId 课堂id
     * @param authContext auth 上下文
     * @return {@code List<GroupInfoBo> }
     * @throws
     * @see String
     * @see AuthContext
     * @see List<GroupInfoBo>
     */
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
        Map<String, OjLocalScene> localSceneMap = CollectionUtils.isEmpty(groupIds)?new HashMap<>():ojLocalSceneMapper.selectByGroupIds(groupIds).stream().collect(Collectors.toMap(OjLocalScene::getBusinessId, OjLocalScen -> OjLocalScen));
        return StreamApiUtils.map(sorted,
                classroomGroupInfo -> entityTBoNew(classroomGroupInfo, userInfoMap, localSceneMap));
    }

    /**
     * 更新组信息
     *
     * @param classroomId 课堂id
     * @param groupCount  组计数
     * @return {@code List<GroupInfoBo> }
     * @throws
     * @see String
     * @see Integer
     * @see List<GroupInfoBo>
     */
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

    /**
     * 保存组信息
     *
     * @param classroomId 课堂id
     * @param name        名称
     * @param data        数据
     * @return {@code Integer }
     * @throws
     * @see String
     * @see String
     * @see List<GroupInfoBo>
     * @see Integer
     */
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

    /**
     * 删除 Classroom 存档
     *
     * @param classroomId    课堂id
     * @param groupArchiveId 组档案id
     * @return {@code Integer }
     * @throws
     * @see String
     * @see String
     * @see Integer
     */
    @Override
    public Integer deleteClassroomArchive(String classroomId, String groupArchiveId) {
        // 数据校验
        validateRoom(classroomId);

        return classroomGroupArchiveMapper.deleteById(groupArchiveId);
    }

    /**
     * 用户组信息
     *
     * @param classroomId    课堂id
     * @param groupArchiveId 组档案id
     * @return {@code List<GroupInfoBo> }
     * @throws
     * @see String
     * @see String
     * @see List<GroupInfoBo>
     */
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
     * @param classroom    课堂
     * @throws
     * @see List<ClassroomGroupInfo>
     * @see CourseLesson
     * @see Classroom
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

    /**
     * 设置组场景延迟
     *
     * @param classroomId 课堂id
     * @param end         结束
     * @return {@code Boolean }
     * @throws
     * @see String
     * @see Boolean
     * @see Boolean
     */
    @Override
    public Boolean setGroupSceneDelay(String classroomId, Boolean end) {

        // 基本数据校验
        Classroom classroom = validateRoom(classroomId);
        CourseLesson courseLesson = validateLesson(classroom);

        // 查询分组场景 并根据是否延时设置时间
        List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper.selectByRoom(classroomId);

		// 查询localScene数据
		List<String> groupInfoIdList = groupInfos.stream().map(ClassroomGroupInfo::getResourceId)
				.collect(Collectors.toList());
		List<OjLocalScene> ojLocalSceneList = Lists.newArrayList();
		if (!groupInfoIdList.isEmpty()) {
			ojLocalSceneList = ojLocalSceneMapper.selectByGroupIds(groupInfoIdList);
		}
		List<String> localSceneIdList = ojLocalSceneList.stream().map(OjLocalScene::getLocalSceneId)
				.collect(Collectors.toList());
		List<LocalScene> localSceneList = Lists.newArrayList();
		if (!localSceneIdList.isEmpty()) {
			localSceneList.addAll(localSceneMapper.selectBatchIds(localSceneIdList));
		}
		// 要更新的数据
		Integer ttl = !end ? (courseLesson.getDestroyDelay() * CommonConstant.SIXTY) : null;
		// destroy delay
		String destroyCloseStr = ConfigUtil.getContextProperty("oj.destroy.delay.close");
		Integer destroyDelay = end ? Integer.parseInt(destroyCloseStr)
				: courseLesson.getDestroyDelay();

		// set end time
		for (LocalScene localScene : localSceneList) {
			if (end) {
				localScene.setEndTime(null);
			} else {
				localScene.setStartTime(new Date());
				localScene.setEndTime(TimeUtil.addHours(localScene.getStartTime(),
						courseLesson.getDestroyDelay()));
			}
			localScene.setSceneTtl(ttl);
		}
		// 数据更新
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				if (CollectionUtils.isNotEmpty(localSceneList)) {
					localSceneList
							.forEach(localScene -> localSceneMapper.updateEndTime(localScene));
				}
				if (CollectionUtils.isNotEmpty(groupInfoIdList)) {
					classroomGroupInfoMapper.updateBatchDestroyDelayByIds(groupInfoIdList,
							destroyDelay);
				}
			}
		});

		if (CollectionUtils.isNotEmpty(localSceneList)) {
			Set<String> businessInstanceIdSet = localSceneList.stream()
					.map(LocalScene::getBusinessInstanceId).collect(Collectors.toSet());
			for (OjLocalScene ojLocalScene : ojLocalSceneList) {
				if (businessInstanceIdSet.contains(ojLocalScene.getResourceId())) {
					pushGroupSyncMessage(ojLocalScene);
				}
			}
		}

        return true;
    }

    /**
     * 推送数据延时消息
     *
     * @param ojLocalScene OJ当地场景
     * @throws
     * @see OjLocalScene
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

    /**
     * 获取组场景延迟状态
     *
     * @param classroomId 课堂id
     * @return {@code Boolean }
     * @throws
     * @see String
     * @see Boolean
     */
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

    /**
     * 查询组档案
     *
     * @param classroomId 课堂id
     * @return {@code List<ClassroomGroupArchiveBo> }
     * @throws
     * @see String
     * @see List<ClassroomGroupArchiveBo>
     */
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

    /**
     * 移动组用户
     *
     * @param classroomId 课堂id
     * @param fromGroupId 从组id
     * @param toGroupId   自组id
     * @param userId      用户id
     * @return {@code Boolean }
     * @throws
     * @see String
     * @see String
     * @see String
     * @see String
     * @see Boolean
     */
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
     * @throws
     * @see String
     * @see CourseLesson
     * @see List<String>
     * @see Integer
     * @see List<ClassroomGroupInfo>
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
     * @param groupInfos 组信息
     * @throws
     * @see List<ClassroomGroupInfo>
     */
    public void enableUpdateGroup(List<ClassroomGroupInfo> groupInfos) {
        List<String> groupIds = groupInfos.stream().map(ClassroomGroupInfo::getResourceId).collect(Collectors.toList());

        List<OjLocalScene> ojLocalScene = ojLocalSceneMapper
                .selectByGroupIds(groupIds);
        if (ojLocalScene.size() > 0) {
            log.error("Group scene is running! ojLocalScene=[{}]",
                    ojLocalScene.toString());
            throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
        }

    }

    /**
     * 数据类型转换
     *
     * @param groupInfo
     * @param userInfoMap 用户信息映射
     * @return
     * @throws
     * @see ClassroomGroupInfo
     * @see Map<String, UserInfo>
     * @see GroupInfoBo
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
     * @param userInfoMap   用户信息映射
     * @param localSceneMap 当地场景映射
     * @return
     * @throws
     * @see ClassroomGroupInfo
     * @see Map<String, UserInfo>
     * @see Map<String, OjLocalScene>
     * @see GroupInfoBo
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

        if (localSceneMap.containsKey(groupInfo.getResourceId())) {
            JSONObject sceneData = ojLocalSceneService.getSceneData(localSceneMap.get(groupInfo.getResourceId()));
            groupInfoBo.setSceneData(sceneData);
        }

        return groupInfoBo;
    }

    /**
     * 获取需要修改的组 删除空组(用户被删除导致组员为空)
     *
     * @param classroomId   课堂id
     * @param deleteUserIds 删除用户id列表
     * @return
     * @throws
     * @see String
     * @see List<String>
     * @see List<ClassroomGroupInfo>
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
     * @throws
     * @see String
     * @see Classroom
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
     * @throws
     * @see Classroom
     * @see CourseLesson
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
     * @throws
     * @see String
     * @see ClassroomGroupInfo
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
