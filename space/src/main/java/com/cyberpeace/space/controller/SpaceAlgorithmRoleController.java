package com.cyberpeace.space.controller;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmRole;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.SpaceAlgorithmRoleService;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmRoleBo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 空间算法岗位Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "知识库-算法库-岗位能力评估算法")
@RestController
@RequestMapping("/space/algorithm/algorithm_role")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmRoleController extends SpaceControllerBase<SpaceAlgorithmRoleService, SpaceAlgorithmRole, SpaceAlgorithmRoleBo, SpacePageReqBase> {

    @Autowired
    private CourseService courseService;

    /**
     * 根据给定的SpaceAlgorithmRole列表获取相关的课程ID和名称映射
     * 此方法用于处理空间算法角色与课程之间的关系，提取并返回课程的ID和名称信息
     *
     * @param eList SpaceAlgorithmRole对象列表，包含课程信息的JSON字符串
     * @return 返回一个映射，键为课程ID，值为包含课程ID和名称的IdAndName对象
     */
    @Override
    protected Map<String, IdAndName> getRelatedMap(List<SpaceAlgorithmRole> eList) {
        // 初始化一个空的HashMap，用于存储课程ID和名称的映射
        Map<String, IdAndName> res = Maps.newHashMap();

        // 创建一个空列表，用于收集所有课程ID
        List<String> courseIds = Lists.newArrayList();

        // 遍历SpaceAlgorithmRole列表，提取并解析课程ID
        for (SpaceAlgorithmRole e : eList) {
            // 将每个SpaceAlgorithmRole对象中的课程信息解析为字符串数组，并添加到课程ID列表中
            courseIds.addAll(JsonUtil.toJsonObjectArray(e.getCourse(), String.class));
        }

        // 如果课程ID列表为空，则直接返回空映射
        if (courseIds.isEmpty()) {
            return res;
        }

        // 调用课程服务，根据课程ID列表获取相应的课程对象列表
        List<Course> courseList = courseService.listByIds(courseIds);

        // 遍历课程列表，创建并添加课程ID和名称的映射到结果映射中
        for (Course course : courseList) {
            // 将课程的资源ID和名称封装到IdAndName对象中，并以资源ID为键存储到映射中
            res.put(course.getResourceId(),
                    new IdAndName(course.getResourceId(), course.getName()));
        }

        // 返回填充了课程ID和名称映射的Map对象
        return res;
    }

    @Override
    public void checkValue(SpaceAlgorithmRole entity){
        this.checkPercentageValue(entity.getCoursePercent() + entity.getTrainingPercent());
    }
}
    