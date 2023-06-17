package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.controller.req.TrainMangeAddReq;
import com.cyberpeace.oj.course.controller.req.TrainMangeCourseUpdateReq;
import com.cyberpeace.oj.course.controller.req.TrainMangeUpdateReq;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.TrainManage;
import com.cyberpeace.oj.course.dao.mapper.TrainManageMapper;
import com.cyberpeace.oj.course.service.TrainManageService;
import com.cyberpeace.oj.course.service.bo.TrainCourseBo;
import com.cyberpeace.oj.course.service.bo.TrainManageTreeBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TrainManageServiceImpl extends ServiceImpl<TrainManageMapper, TrainManage> implements TrainManageService {

    @Autowired
    private TrainManageMapper trainManageMapper;

    @Override
    public List<TrainManageTreeBo> trainMangeList() {

        List<TrainManageTreeBo> resultList = new ArrayList<>();
        List<TrainManage> trainManageList = trainManageMapper.selectList(new LambdaQueryWrapper<>());
        if (CollectionUtils.isEmpty(trainManageList)) {
            return new ArrayList<>();
        }
        //查询课程信息
        List<String> totalCourseIds = trainManageList.stream().filter(item -> Objects.nonNull(item.getCourseIds()))
                .map(item -> Arrays.stream(item.getCourseIds().split(",")).collect(Collectors.toList()))
                .flatMap(Collection::stream).distinct().collect(Collectors.toList());
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Course::getId, totalCourseIds);
        Map<String, TrainCourseBo> courseMap = trainManageMapper.selectCourseInfo(totalCourseIds).stream().collect(Collectors.toMap(TrainCourseBo::getResourceId, item -> item));

        Map<String, List<TrainManage>> mapByPatent = getMapByPatent(resultList, trainManageList);
        for (TrainManageTreeBo parentTrainManage : resultList) {
            List<TrainManage> trainManages = mapByPatent.get(parentTrainManage.getResourceId());
            if (CollectionUtils.isEmpty(trainManages)) {
                continue;
            }
            List<TrainManageTreeBo> childrenList = trainManages.stream().map(item -> getTrainManageTreeBo(courseMap, parentTrainManage, item)).collect(Collectors.toList());
            parentTrainManage.setChildrenList(childrenList);
        }

        return resultList;
    }

    @Override
    public List<TrainManageTreeBo> webTrainMangeList() {
        List<TrainManageTreeBo> resultList = new ArrayList<>();
        List<TrainManage> trainManageList = trainManageMapper.selectList(new LambdaQueryWrapper<>());
        if (CollectionUtils.isEmpty(trainManageList)) {
            return new ArrayList<>();
        }

        //查询课程信息
        List<String> totalCourseIds = trainManageList.stream().filter(item -> Objects.nonNull(item.getCourseIds()))
                .map(item -> Arrays.stream(item.getCourseIds().split(",")).collect(Collectors.toList()))
                .flatMap(Collection::stream).distinct().collect(Collectors.toList());
        LambdaQueryWrapper<CourseLesson> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CourseLesson::getCourseId, totalCourseIds);
        List<TrainCourseBo> trainCourseBoList = trainManageMapper.selectCourseIntroduce(totalCourseIds);

        Map<String, TrainCourseBo> courseMap = trainCourseBoList.stream().peek(this::jsonStrToStr).collect(Collectors.toMap(TrainCourseBo::getResourceId, item -> item));
        Map<String, List<TrainManage>> mapByPatent = getMapByPatent(resultList, trainManageList);

        for (TrainManageTreeBo trainManageTreeBo : resultList) {
            List<TrainManage> trainManages = mapByPatent.get(trainManageTreeBo.getResourceId());
            if (CollectionUtils.isEmpty(trainManages)) {
                continue;
            }
            List<TrainManageTreeBo> childrenList = trainManages.stream().map(item -> {
                TrainManageTreeBo children = new TrainManageTreeBo();
                children.setResourceId(item.getResourceId());
                children.setName(item.getName());
                if (null == item.getCourseIds()) {
                    return children;
                }
                //组装第二层级
                List<TrainCourseBo> courseList = new ArrayList<>();
                for (String courseId : item.getCourseIds().split(",")) {
                    TrainCourseBo trainCourseBo = courseMap.get(courseId);
                    courseList.add(trainCourseBo);
                }
                children.setCourseList(courseList);
                return children;
            }).collect(Collectors.toList());

            trainManageTreeBo.setChildrenList(childrenList);
        }
        return resultList;
    }

    @Override
    public Boolean add(TrainMangeAddReq req) {
        verifyNameExists(req.getName(), null);
        if (StringUtils.isNotBlank(req.getParent())) {
            LambdaQueryWrapper<TrainManage> queryPatent = new LambdaQueryWrapper<>();
            queryPatent.eq(TrainManage::getParent, req.getParent());
            TrainManage parent = trainManageMapper.selectOne(queryPatent);
            if (Objects.isNull(parent) || StringUtils.isNotBlank(parent.getParent())) {
                log.error("parent TrainManage error! parent=[{}]", req.getParent());
                throw new BusinessException(OjBusinessErrorCode.TRAIN_MANAGE_LEVEL_ERROR);
            }
        }
        TrainManage trainManage = new TrainManage();
        trainManage.setResourceId(IDGeneratorUtil.getID());
        trainManage.setName(req.getName());
        trainManage.setParent(req.getParent());
        return trainManageMapper.insert(trainManage) > 0;
    }

    @Override
    public Boolean update(TrainMangeUpdateReq req) {
        LambdaQueryWrapper<TrainManage> queryResource = new LambdaQueryWrapper<>();
        queryResource.eq(TrainManage::getResourceId, req.getResourceId());
        TrainManage trainByResource = trainManageMapper.selectOne(queryResource);
        if (Objects.isNull(trainByResource)) {
            log.error("TrainManage not found! resourceId=[{}]", req.getResourceId());
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        verifyNameExists(req.getName(), req.getResourceId());
        trainByResource.setName(req.getName());
        return trainManageMapper.updateById(trainByResource) > 0;
    }

    @Override
    public Boolean updateCourse(TrainMangeCourseUpdateReq req) {
        LambdaQueryWrapper<TrainManage> queryResource = new LambdaQueryWrapper<>();
        queryResource.eq(TrainManage::getResourceId, req.getResourceId());
        TrainManage trainByResource = trainManageMapper.selectOne(queryResource);
        if (Objects.isNull(trainByResource)) {
            log.error("TrainManage not found! resourceId=[{}]", req.getResourceId());
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        trainByResource.setCourseIds(req.getCourseIds());
        return trainManageMapper.updateById(trainByResource) > 0;

    }

    private void verifyNameExists(String name, String resourceId) {
        LambdaQueryWrapper<TrainManage> queryName = new LambdaQueryWrapper<>();
        queryName.eq(TrainManage::getName, name);
        TrainManage trainByName = trainManageMapper.selectOne(queryName);

        if (Objects.nonNull(trainByName) && !trainByName.getResourceId().equals(resourceId)) {
            throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
        }
    }

    @Override
    public Boolean copy(String resourceId) {
        LambdaQueryWrapper<TrainManage> queryResource = new LambdaQueryWrapper<>();
        queryResource.eq(TrainManage::getResourceId, resourceId);
        TrainManage trainManage = trainManageMapper.selectOne(queryResource);
        if (Objects.isNull(trainManage)) {
            log.error("TrainManage not found! resourceId=[{}]", resourceId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        TrainManage newTrainManage = new TrainManage();
        newTrainManage.setResourceId(IDGeneratorUtil.getID());
        newTrainManage.setName(trainManage.getName() + "（复制）");
        newTrainManage.setParent(trainManage.getParent());
        newTrainManage.setCourseIds(trainManage.getCourseIds());
        return trainManageMapper.insert(newTrainManage) > 0;

    }

    @Override
    public Boolean delete(String resourceId) {
        LambdaQueryWrapper<TrainManage> queryResource = new LambdaQueryWrapper<>();
        queryResource.eq(TrainManage::getResourceId, resourceId).or().in(TrainManage::getParent, resourceId);
        return trainManageMapper.delete(queryResource) > 0;
    }

    @Override
    public List<TrainCourseBo> getDetail(String resourceId) {
        List<TrainCourseBo> resultList = new ArrayList<>();
        LambdaQueryWrapper<TrainManage> queryTrain = new LambdaQueryWrapper<>();
        queryTrain.eq(TrainManage::getResourceId, resourceId);
        TrainManage trainManage = trainManageMapper.selectOne(queryTrain);
        if (StringUtils.isBlank(trainManage.getCourseIds())) {
            return resultList;
        }
        List<String> courseIds = Arrays.stream(trainManage.getCourseIds().split(",")).collect(Collectors.toList());
        Map<String, TrainCourseBo> courseMap = trainManageMapper.selectCourseInfo(courseIds).stream().collect(Collectors.toMap(TrainCourseBo::getResourceId, item -> item));
        for (String courseId : courseIds) {
            resultList.add(courseMap.get(courseId));
        }
        return resultList;
    }

    @Override
    public List<TrainCourseBo> getWebDetail(String resourceId) {

        LambdaQueryWrapper<TrainManage> queryTrain = new LambdaQueryWrapper<>();
        queryTrain.eq(TrainManage::getResourceId, resourceId);
        TrainManage trainManage = trainManageMapper.selectOne(queryTrain);
        if (StringUtils.isBlank(trainManage.getCourseIds())) {
            return new ArrayList<>();
        }
        List<String> courseIds = Arrays.stream(trainManage.getCourseIds().split(",")).collect(Collectors.toList());
        List<TrainCourseBo> resultList = trainManageMapper.selectCourseIntroduce(courseIds);
        resultList.forEach(this::jsonStrToStr);
        return resultList;
    }

    private TrainManageTreeBo getTrainManageTreeBo(Map<String, TrainCourseBo> courseMap, TrainManageTreeBo parentTrainManage, TrainManage item) {
        List<TrainCourseBo> trainCourseBoList = new ArrayList<>();
        TrainManageTreeBo children = new TrainManageTreeBo();
        children.setResourceId(item.getResourceId());
        children.setName(item.getName());
        children.setParent(parentTrainManage.getResourceId());
        if (null != item.getCourseIds()) {
            List<String> courseIds = Arrays.stream(item.getCourseIds().split(",")).collect(Collectors.toList());
            for (String courseId : courseIds) {
                trainCourseBoList.add(courseMap.get(courseId));
            }
        }
        children.setCourseList(trainCourseBoList);
        return children;
    }

    private Map<String, List<TrainManage>> getMapByPatent(List<TrainManageTreeBo> resultList, List<TrainManage> trainManageList) {
        List<TrainManage> secondTrainManageList = new ArrayList<>();
        for (TrainManage trainManage : trainManageList) {
            if (StringUtils.isBlank(trainManage.getParent())) {
                TrainManageTreeBo trainManageTreeBo = new TrainManageTreeBo();
                trainManageTreeBo.setResourceId(trainManage.getResourceId());
                trainManageTreeBo.setName(trainManage.getName());
                trainManageTreeBo.setParent(null);
                resultList.add(trainManageTreeBo);
            } else {
                secondTrainManageList.add(trainManage);
            }
        }
        return StreamApiUtils.groupBy(secondTrainManageList, TrainManage::getParent);
    }

    private void jsonStrToStr(TrainCourseBo item) {
        List<String> knowledgeList = new ArrayList<>();
        for (String str : item.getKnowledgeList()) {
            String collect = String.join(",", JSONArray.parseArray(str, String.class));
            if (StringUtils.isNotBlank(collect)) {
                knowledgeList.add(collect);
            }
        }
        item.setKnowledgeList(knowledgeList);
    }
}
