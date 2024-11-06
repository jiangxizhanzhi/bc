package com.cyberpeace.space.controller;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmRole;
import com.cyberpeace.space.model.IdAndName;
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

@Api(tags = "知识库-算法库-岗位能力评估算法")
@RestController
@RequestMapping("/space/algorithm/algorithm_role")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmRoleController extends SpaceControllerBase<SpaceAlgorithmRoleService, SpaceAlgorithmRole, SpaceAlgorithmRoleBo> {

    @Autowired
    private CourseService courseService;

    @Override
    protected Map<String, IdAndName> getRelatedMap(List<SpaceAlgorithmRole> eList) {
        Map<String, IdAndName> res = Maps.newHashMap();
        List<String> courseIds = Lists.newArrayList();
        for (SpaceAlgorithmRole e : eList) {
            courseIds.addAll(JsonUtil.toJsonObjectArray(e.getCourse(), String.class));
        }
        if (courseIds.isEmpty()) {
            return res;
        }
        List<Course> courseList = courseService.listByIds(courseIds);
        for (Course course : courseList) {
            res.put(course.getResourceId(),
                    new IdAndName(course.getResourceId(), course.getName()));
        }
        return res;
    }
}
    