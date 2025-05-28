package com.cyberpeace.space.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmExpertise;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.SpaceAlgorithmExpertiseService;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmExpertiseBo;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间算法专业知识控制器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "知识库-算法库-专业能力评估算法")
@RestController
@RequestMapping("/space/algorithm/algorithm_expertise")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmExpertiseController extends SpaceControllerBase<SpaceAlgorithmExpertiseService, SpaceAlgorithmExpertise, SpaceAlgorithmExpertiseBo, SpacePageReqBase> {

	/**
	 * 课程Service
	 */
	@Autowired
	private CourseService courseService;

	/**
	 * 获取相关地图
	 *
	 * 该方法用于根据专家领域列表生成一个映射，该映射将课程ID映射到其ID和名称的对
	 * 主要目的是为了方便地获取与给定专家领域相关的课程信息
	 *
	 * @param eList 专家领域列表，包含课程信息的JSON字符串
	 * @return {@code Map<String, IdAndName> } 返回一个映射，键是课程ID，值是包含课程ID和名称的对象
	 */
	@Override
	protected Map<String, IdAndName> getRelatedMap(List<SpaceAlgorithmExpertise> eList) {
	    // 初始化结果映射
	    Map<String, IdAndName> res = Maps.newHashMap();

	    // 用于存储从专家领域列表中提取的所有课程ID
	    List<String> courseIds = Lists.newArrayList();

	    // 遍历专家领域列表，提取课程ID并添加到课程ID列表中
	    for (SpaceAlgorithmExpertise e : eList) {
	        courseIds.addAll(JsonUtil.toJsonObjectArray(e.getCourse(), String.class));
	    }

	    // 如果课程ID列表为空，则直接返回空映射
	    if (courseIds.isEmpty()) {
	        return res;
	    }

	    // 根据课程ID列表，查询课程信息
	    List<Course> courseList = courseService.listByIds(courseIds);

	    // 遍历课程列表，将课程的资源ID和名称添加到结果映射中
	    for (Course course : courseList) {
	        res.put(course.getResourceId(),
	                new IdAndName(course.getResourceId(), course.getName()));
	    }

	    // 返回填充了课程信息的映射
	    return res;
	}

	@Override
	public void checkValue(SpaceAlgorithmExpertise entity) {
		this.checkPercentageValue(entity.getCoursePercent() + entity.getTrainingPercent());
	}
}
