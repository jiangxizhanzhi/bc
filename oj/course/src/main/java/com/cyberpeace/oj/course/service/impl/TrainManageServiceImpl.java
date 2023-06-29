package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.oj.course.controller.req.TrainCourseReq;
import com.cyberpeace.oj.course.controller.req.TrainMangeReq;
import com.cyberpeace.oj.course.controller.req.TrainMangeSortReq;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.TrainManage;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.TrainManageMapper;
import com.cyberpeace.oj.course.service.TrainManageService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
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

@Service
@Slf4j
public class TrainManageServiceImpl extends ServiceImpl<TrainManageMapper, TrainManage> implements TrainManageService {

    @Autowired
    private TrainManageMapper trainManageMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    CourseStatisticsServiceImpl courseStatisticsService;

    @Autowired
    private UtilService utilService;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseLessonMapper courseLessonMapper;

    @Override
    public TrainManageTreeBo trainMangeList() {

        TrainManageTreeBo result = new TrainManageTreeBo();
        LambdaQueryWrapper<TrainManage> queryAllWrapper = new LambdaQueryWrapper<>();
        queryAllWrapper.orderByAsc(TrainManage::getSort);
        List<TrainManage> trainManageList = trainManageMapper.selectList(queryAllWrapper);
        //查询课程信息
        List<String> totalCourseIds = trainManageList.stream().filter(item -> !"default".equals(item.getResourceId()))
                .map(item -> JSONArray.parseArray(item.getCourseIds(), String.class))
                .flatMap(Collection::stream).distinct().collect(Collectors.toList());
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Course::getId, totalCourseIds);
        Map<String, List<TrainManage>> mapByPatent = getMapByPatent(result, trainManageList);
        for (TrainManageTreeBo parentTrainManage : result.getChildrenList()) {
            List<TrainManage> trainManages = mapByPatent.get(parentTrainManage.getResourceId());
            if (CollectionUtils.isEmpty(trainManages)) {
                continue;
            }
            List<TrainManageTreeBo> childList = new ArrayList<>();
            for (TrainManage child : trainManages) {
                TrainManageTreeBo children = new TrainManageTreeBo();
                children.setResourceId(child.getResourceId());
                children.setName(child.getName());
                children.setParent(parentTrainManage.getResourceId());
                childList.add(children);
            }
            parentTrainManage.setChildrenList(childList);
        }
        return result;
    }

    @Override
    public List<TrainManageTreeBo> webTrainMangeList() {
        TrainManageTreeBo result = new TrainManageTreeBo();
        String userId = UserInfoContext.getUserContext().getUserId();

        LambdaQueryWrapper<TrainManage> queryAllWrapper = new LambdaQueryWrapper<>();
        queryAllWrapper.orderByAsc(TrainManage::getSort);
        List<TrainManage> trainManageList = trainManageMapper.selectList(queryAllWrapper);

        //根据课程培训trainManageList获取课程ids
        List<String> totalCourseIds = trainManageList.stream().filter(item -> !"default".equals(item.getResourceId()))
                .map(item -> JSONArray.parseArray(item.getCourseIds(), String.class))
                .flatMap(Collection::stream).distinct().collect(Collectors.toList());
        LambdaQueryWrapper<CourseLesson> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CourseLesson::getCourseId, totalCourseIds);

        //获取课程关联知识点内容
        List<CourseKnowledgeInfoBo> trainCourseBoList = CollectionUtils.isEmpty(totalCourseIds) ? new ArrayList<>() : trainManageMapper.selectCourseKnowleage(totalCourseIds);

        //课程关联知识点内容-->课程列表和知识点
        List<TrainCourseBo> trainCourseList = new ArrayList<>();
        List<CourseKnowledgeBo> courseKnowledgeBoList = new ArrayList<>();
        transferCourseAndCourseKnowledge(trainCourseBoList, trainCourseList, courseKnowledgeBoList);

        //获取学习进度
        List<CourseStudyProcessBo> processBos = courseStatisticsService.statisticByIds(totalCourseIds, userId);
        Map<String, CourseStudyProcessBo> processBoMap = processBos.stream().collect(Collectors.toMap(CourseStudyProcessBo::getCourseId, bo -> bo));

        Map<String, List<CourseKnowledgeBo>> courseKnowledgeMap = courseKnowledgeBoList.stream().collect(Collectors.groupingBy(CourseKnowledgeBo::getResourceId));
        trainCourseList.forEach(item -> {
            item.setKnowledgeList(courseKnowledgeMap.get(item.getResourceId()));
            CourseStudyProcessBo courseStudyProcessBo = processBoMap.get(item.getResourceId());
            item.setProcess(courseStudyProcessBo == null ? 0 : MathUtils.ceil(courseStudyProcessBo.getProcess()));
            item.setInterview(courseStudyProcessBo != null ? courseStudyProcessBo.getInterview() : false);
            item.setLogo(utilService.handlerFileUrl(item.getLogo()));
        });
        Map<String, TrainCourseBo> courseMap = StreamApiUtils.toMap(trainCourseList, TrainCourseBo::getResourceId, item -> item);

        //组装结果集(课程训练框架)
        Map<String, List<TrainManage>> mapByPatent = getMapByPatent(result, trainManageList);
        for (TrainManageTreeBo trainManageTreeBo : result.getChildrenList()) {
            List<TrainManage> trainManages = mapByPatent.get(trainManageTreeBo.getResourceId());
            if (CollectionUtils.isEmpty(trainManages)) {
                continue;
            }
            List<TrainManageTreeBo> childrenList = trainManages.stream().map(item -> {
                TrainManageTreeBo children = new TrainManageTreeBo();
                children.setResourceId(item.getResourceId());
                children.setName(item.getName());
                //组装第二层级
                List<TrainCourseBo> courseList = new ArrayList<>();
                if (StringUtils.isNotBlank(item.getCourseIds())) {
                    for (String courseId : JSONArray.parseArray(item.getCourseIds(), String.class)) {
                        TrainCourseBo trainCourseBo = courseMap.get(courseId);
                        if (Objects.nonNull(trainCourseBo)) {
                            courseList.add(trainCourseBo);
                        }
                    }
                }
                children.setCourseList(courseList);
                return children;
            }).collect(Collectors.toList());

            trainManageTreeBo.setChildrenList(childrenList);
        }
        return result.getChildrenList();
    }

    private void transferCourseAndCourseKnowledge(List<CourseKnowledgeInfoBo> trainCourseBoList, List<TrainCourseBo> trainCourseList, List<CourseKnowledgeBo> courseKnowledgeBoList) {
        Set<String> setId = new HashSet<>();
        for (CourseKnowledgeInfoBo courseKnowledgeInfoBo : trainCourseBoList) {
            if (setId.add(courseKnowledgeInfoBo.getResourceId())) {
                TrainCourseBo trainCourseBo = TrainCourseBo.getTrainCourseBoFromKnowledge(courseKnowledgeInfoBo);
                trainCourseList.add(trainCourseBo);
            }
            CourseKnowledgeBo courseKnowledgeBo = new CourseKnowledgeBo();
            courseKnowledgeBo.setResourceId(courseKnowledgeInfoBo.getResourceId());
            courseKnowledgeBo.setRelatedId(courseKnowledgeInfoBo.getRelatedId());
            courseKnowledgeBo.setContent(courseKnowledgeInfoBo.getContent());
            courseKnowledgeBoList.add(courseKnowledgeBo);
        }
    }

    @Override
    public Boolean addLevel(TrainMangeReq req) {

        verifyNameExists(req.getName(), null, req.getParent());
        TrainManage trainManage = new TrainManage();

        LambdaQueryWrapper<TrainManage> queryPatent = new LambdaQueryWrapper<>();
        queryPatent.eq(TrainManage::getResourceId, req.getParent());
        TrainManage parent = trainManageMapper.selectOne(queryPatent);
        if (Objects.isNull(parent)) {
            log.error("parent TrainManage error! parent=[{}]", req.getParent());
            throw new BusinessException(OjBusinessErrorCode.TRAIN_MANAGE_LEVEL_ERROR);
        }
        trainManage.setResourceId(IDGeneratorUtil.getID());
        trainManage.setName(req.getName());
        trainManage.setParent(req.getParent());
        trainManage.setCourseIds(new JSONArray().toJSONString());
        trainManage.setSort(getCurrentLevelSize(req.getParent()) + 1);
        return trainManageMapper.insert(trainManage) > 0;
    }

    private int getCurrentLevelSize(String parent) {
        LambdaQueryWrapper<TrainManage> currentLevel = new LambdaQueryWrapper<>();
        currentLevel.eq(TrainManage::getParent, parent);
        return trainManageMapper.selectCount(currentLevel);
    }

    @Override
    public Boolean updateLevel(TrainMangeReq req) {
        LambdaQueryWrapper<TrainManage> queryResource = new LambdaQueryWrapper<>();
        queryResource.eq(TrainManage::getResourceId, req.getResourceId());
        TrainManage trainByResource = trainManageMapper.selectOne(queryResource);
        if (Objects.isNull(trainByResource)) {
            log.error("TrainManage not found! resourceId=[{}]", req.getResourceId());
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        verifyNameExists(req.getName(), req.getResourceId(), req.getParent());
        trainByResource.setName(req.getName());
        return trainManageMapper.updateById(trainByResource) > 0;
    }

    @Override
    public Boolean deleteLevel(String resourceId) {
        if ("default".equals(resourceId)) {
            return false;
        }
        LambdaQueryWrapper<TrainManage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrainManage::getResourceId, resourceId);
        TrainManage deleteTrainManage = trainManageMapper.selectOne(queryWrapper);
        if (Objects.isNull(deleteTrainManage)) {
            log.error("TrainManage not found! resourceId=[{}]", resourceId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        List<TrainManage> updateList = new ArrayList<>();
        LambdaQueryWrapper<TrainManage> parentWrapper = new LambdaQueryWrapper<>();
        parentWrapper.eq(TrainManage::getParent, deleteTrainManage.getParent());
        parentWrapper.orderByAsc(TrainManage::getSort);
        List<TrainManage> trainManages = trainManageMapper.selectList(parentWrapper);
        int count = 1;
        for (TrainManage trainManage : trainManages) {
            if (!resourceId.equals(trainManage.getResourceId())) {
                trainManage.setSort(count);
                updateList.add(trainManage);
                count++;
            }
        }
        LambdaQueryWrapper<TrainManage> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(TrainManage::getResourceId, resourceId).or().eq(TrainManage::getParent, resourceId);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                trainManageMapper.delete(deleteWrapper);
                if (CollectionUtils.isNotEmpty(updateList)) {
                    updateList.forEach(item -> trainManageMapper.updateById(item));
                }
            }
        });
        return true;
    }

    @Override
    public Boolean move(TrainMangeSortReq req) {
        LambdaQueryWrapper<TrainManage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TrainManage::getParent, req.getParent());
        List<TrainManage> trainManages = trainManageMapper.selectList(queryWrapper);
        Map<String, TrainManage> map = trainManages.stream().collect(Collectors.toMap(TrainManage::getResourceId, item -> item));
        List<String> currentResource = trainManages.stream().map(TrainManage::getResourceId).collect(Collectors.toList());
        if (trainManages.size() == req.getResourceIdList().size() && currentResource.containsAll(req.getResourceIdList())) {
            List<TrainManage> updateList = new ArrayList<>();
            int count = 1;
            for (String id : req.getResourceIdList()) {
                TrainManage trainManage = map.get(id);
                trainManage.setSort(count);
                updateList.add(trainManage);
                count++;
            }
            updateList.forEach(item -> trainManageMapper.updateById(item));
        }
        return true;
    }

    @Override
    public Boolean copy(String resourceId) {
        if ("default".equals(resourceId)) {
            return false;
        }
        LambdaQueryWrapper<TrainManage> queryResource = new LambdaQueryWrapper<>();
        queryResource.eq(TrainManage::getResourceId, resourceId);
        TrainManage trainManage = trainManageMapper.selectOne(queryResource);
        if (Objects.isNull(trainManage)) {
            log.error("TrainManage not found! resourceId=[{}]", resourceId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        LambdaQueryWrapper<TrainManage> queryParent = new LambdaQueryWrapper<>();
        queryParent.eq(TrainManage::getParent, trainManage.getParent());
        List<String> nameList = trainManageMapper.selectList(queryParent).stream().map(TrainManage::getName).collect(Collectors.toList());

        TrainManage newTrainManage = new TrainManage();
        newTrainManage.setResourceId(IDGeneratorUtil.getID());
        String name = trainManage.getName() + "(复制)";
        while (nameList.contains(name)) {
            name = name + "(复制)";
        }
        newTrainManage.setName(name);
        newTrainManage.setParent(trainManage.getParent());
        newTrainManage.setCourseIds(trainManage.getCourseIds());
        newTrainManage.setSort(getCurrentLevelSize(trainManage.getParent()) + 1);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                trainManageMapper.insert(newTrainManage);

                //多层复制
                LambdaQueryWrapper<TrainManage> queryChild = new LambdaQueryWrapper<>();
                queryChild.eq(TrainManage::getParent, resourceId);
                List<TrainManage> childList = trainManageMapper.selectList(queryChild);
                if (CollectionUtils.isNotEmpty(childList)) {
                    for (TrainManage manage : childList) {
                        TrainManage newChild = new TrainManage();
                        newChild.setResourceId(IDGeneratorUtil.getID());
                        newChild.setName(manage.getName() + "(复制)");
                        newChild.setParent(newTrainManage.getResourceId());
                        newChild.setCourseIds(manage.getCourseIds());
                        newChild.setSort(getCurrentLevelSize(newTrainManage.getResourceId()) + 1);
                        trainManageMapper.insert(newChild);
                    }
                }

            }
        });
        return true;
    }

    @Override
    public Boolean addCourse(TrainCourseReq req) {
        LambdaQueryWrapper<TrainManage> queryResource = new LambdaQueryWrapper<>();
        queryResource.eq(TrainManage::getResourceId, req.getResourceId());
        TrainManage trainByResource = trainManageMapper.selectOne(queryResource);
        if (Objects.isNull(trainByResource)) {
            log.error("TrainManage not found! resourceId=[{}]", req.getResourceId());
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        if ("default".equals(trainByResource.getResourceId()) || "default".equals(trainByResource.getParent())) {
            return false;
        }
        //过滤无效的课程(包括原有的)
        List<String> courseIds = StringUtils.isBlank(trainByResource.getCourseIds()) ? new ArrayList<>() : JSONArray.parseArray(trainByResource.getCourseIds(), String.class);
        courseIds.addAll(req.getCourseIdList());
        LambdaQueryWrapper<Course> courseQuery = new LambdaQueryWrapper<>();
        courseQuery.in(Course::getResourceId, courseIds);
        List<String> existCourse = courseMapper.selectList(courseQuery).stream().map(Course::getResourceId).collect(Collectors.toList());
        trainByResource.setCourseIds(JSONArray.toJSONString(existCourse));
        return trainManageMapper.updateById(trainByResource) > 0;
    }

    @Override
    public Boolean updateCourse(TrainCourseReq req) {
        LambdaQueryWrapper<TrainManage> queryResource = new LambdaQueryWrapper<>();
        queryResource.eq(TrainManage::getResourceId, req.getResourceId());
        TrainManage trainByResource = trainManageMapper.selectOne(queryResource);
        if (Objects.isNull(trainByResource)) {
            log.error("TrainManage not found! resourceId=[{}]", req.getResourceId());
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        if ("default".equals(trainByResource.getResourceId()) || "default".equals(trainByResource.getParent())) {
            return false;
        }
        String courseIds = CollectionUtils.isEmpty(req.getCourseIdList()) ? new JSONArray().toJSONString() : JSONArray.toJSONString(req.getCourseIdList());
        trainByResource.setCourseIds(courseIds);
        return trainManageMapper.updateById(trainByResource) > 0;
    }


    @Override
    public Boolean deleteCourse(TrainCourseReq req) {
        LambdaQueryWrapper<TrainManage> queryResource = new LambdaQueryWrapper<>();
        queryResource.eq(TrainManage::getResourceId, req.getResourceId());
        TrainManage trainByResource = trainManageMapper.selectOne(queryResource);
        if (Objects.isNull(trainByResource)) {
            log.error("TrainManage not found! resourceId=[{}]", req.getResourceId());
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        if ("default".equals(trainByResource.getResourceId()) || "default".equals(trainByResource.getParent())) {
            return false;
        }
        List<String> courseIds = StringUtils.isAllBlank(trainByResource.getCourseIds()) ? new ArrayList<>() : JSONArray.parseArray(trainByResource.getCourseIds(), String.class);
        courseIds.removeAll(req.getCourseIdList());
        trainByResource.setCourseIds(JSONArray.toJSONString(courseIds));
        return trainManageMapper.updateById(trainByResource) > 0;
    }

    private void verifyNameExists(String name, String resourceId, String parent) {
        LambdaQueryWrapper<TrainManage> queryName = new LambdaQueryWrapper<>();
        queryName.eq(TrainManage::getName, name);
        queryName.eq(TrainManage::getParent, parent);
        TrainManage trainByName = trainManageMapper.selectOne(queryName);

        if (Objects.nonNull(trainByName) && !trainByName.getResourceId().equals(resourceId)) {
            throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
        }
    }

    @Override
    public List<TrainCourseBo> getDetail(String resourceId) {
        List<TrainCourseBo> resultList = new ArrayList<>();
        LambdaQueryWrapper<TrainManage> queryTrain = new LambdaQueryWrapper<>();
        queryTrain.eq(TrainManage::getResourceId, resourceId);
        TrainManage trainManage = trainManageMapper.selectOne(queryTrain);
        if (Objects.isNull(trainManage)) {
            return resultList;
        }
        List<String> courseIds = JSONArray.parseArray(trainManage.getCourseIds(), String.class);
        if (CollectionUtils.isEmpty(courseIds)) {
            return resultList;
        }
        List<TrainCourseBo> trainCourseBoList = trainManageMapper.selectCourseInfo(courseIds);
        if (CollectionUtils.isEmpty(trainCourseBoList)) {
            return resultList;
        }
        //设置 是否具有课程访问权限
        setCourseInterview(trainCourseBoList);
        Map<String, TrainCourseBo> courseMap = CollectionUtils.isEmpty(courseIds) ? Maps.newHashMap() : trainCourseBoList.stream().collect(Collectors.toMap(TrainCourseBo::getResourceId, item -> item));
        for (String courseId : courseIds) {
            TrainCourseBo trainCourseBo = courseMap.get(courseId);
            if (Objects.nonNull(trainCourseBo)) {
                trainCourseBo.setLogo(utilService.handlerFileUrl(trainCourseBo.getLogo()));
                resultList.add(trainCourseBo);
            }
        }
        return resultList;
    }

    private Map<String, List<TrainManage>> getMapByPatent(TrainManageTreeBo top, List<TrainManage> trainManageList) {
        List<TrainManageTreeBo> second = new ArrayList<>();
        List<TrainManage> secondTrainManageList = new ArrayList<>();
        for (TrainManage trainManage : trainManageList) {
            if ("default".equals(trainManage.getResourceId())) {
                top.setName(trainManage.getName());
                top.setResourceId(trainManage.getResourceId());
            } else if ("default".equals(trainManage.getParent())) {
                TrainManageTreeBo trainManageTreeBo = new TrainManageTreeBo();
                trainManageTreeBo.setResourceId(trainManage.getResourceId());
                trainManageTreeBo.setName(trainManage.getName());
                trainManageTreeBo.setParent(trainManage.getParent());
                second.add(trainManageTreeBo);
            } else {
                secondTrainManageList.add(trainManage);
            }
        }
        top.setChildrenList(second);
        return secondTrainManageList.stream().collect(Collectors.groupingBy(TrainManage::getParent, LinkedHashMap::new, Collectors.toList()));
    }

    private void setCourseInterview(List<TrainCourseBo> courses) {
        List<String> ids = courses.stream().map(TrainCourseBo::getResourceId).collect(Collectors.toList());
        // 查询有权访问课程
        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), true);
        List<String> webCourses = courseMapper.selectByAuth(authContext);

        // 课程数量 以课程编号进行分组
        List<CourseLesson> courseLessons = courseLessonMapper.listByCourses(ids, authContext.getIsWeb());
        Map<String, List<CourseLesson>> courseMap = courseLessons.stream().collect(Collectors.groupingBy(CourseLesson::getCourseId));

        // 课程为零或者课程无权限,都禁止访问访问
        for (TrainCourseBo course : courses) {
            boolean interview = course.getIsPublic() && webCourses.contains(course.getResourceId()) && CollectionUtils.isNotEmpty(courseMap.get(course.getResourceId()));
            course.setInterview(interview);
            course.setIsPublic(null);
        }
    }
}
