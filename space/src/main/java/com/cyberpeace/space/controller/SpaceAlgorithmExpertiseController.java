/**
 * 这段代码是一个基于Spring框架的RESTful控制器类，用于处理与“空间算法专业知识”相关的业务逻辑。以下是代码的主要分析和总结： 
 * ### 类的功能和结构 
 * 1. **类名与继承**： 
 * - 类名为`SpaceAlgorithmExpertiseController`，表示这是一个专门处理“空间算法专业知识”的控制器。 
 * - 它继承自`SpaceControllerBase`，并且通过泛型参数指定了相关的服务、实体和业务对象。 
 * 2. **注解**： 
 * - `@RestController`：表明这是一个RESTful风格的控制器。 
 * - `@RequestMapping`：定义了该控制器的请求路径前缀为`/space/algorithm/algorithm_expertise`。 
 * - `@RequiresAuthentication`：确保只有经过身份验证的用户才能访问这些接口。 
 * - `@Slf4j`：引入日志功能，方便调试和监控。 
 * 3. **依赖注入**： 
 * - 使用`@Autowired`注解注入了`CourseService`，用于操作课程相关的数据。 
 * ### 方法分析 
 * 1. **getRelatedMap** 
 * - 功能：根据传入的专家领域列表（`eList`），生成一个映射，将课程ID映射到对应的课程名称。 
 * - 流程： 
 * - 遍历`eList`，提取每个专家领域的课程ID。 
 * - 调用`courseService.listByIds()`批量查询课程信息。 
 * - 将查询到的课程信息存入`res`映射中，键为课程ID，值为`IdAndName`对象。 
 * - 特点： 
 * - 使用了`JsonUtil`工具类来解析JSON字符串。 
 * - 如果`eList`为空或无有效课程ID，则直接返回空映射。 
 * 2. **checkValue** 
 * - 功能：检查两个百分比字段（`coursePercent`和`trainingPercent`）之和是否符合要求。 
 * - 调用了父类中的`checkPercentageValue`方法进行具体校验。 
 * ### 总结 
 * - **主要职责**：该类负责管理与“空间算法专业知识”相关的业务逻辑，包括根据专家领域生成课程映射以及验证百分比字段的有效性。 
 * - **技术特点**：利用了Spring框架、Apache Shiro认证机制、Guava工具类等现代Java开发技术。 
 * - **适用场景**：适用于需要对算法专业知识进行分类管理和展示的应用场景，比如在线教育平台中的课程推荐系统。 
**/
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
