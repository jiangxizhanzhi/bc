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

    /**
     * 课程Service
     *
     * @see CourseService
     */
    @Autowired
    private CourseService courseService;

    /**
     * 获取相关映射
     *
     * @param eList E 列表
     * @return 地图<字符串id和名称>
     * @throws
     * @see List<SpaceAlgorithmRole>
     * @see Map<String, IdAndName>
     */
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

    /**
     * 检查值
     *
     * @param entity 实体
     * @throws
     * @see SpaceAlgorithmRole
     */
    @Override
    public void checkValue(SpaceAlgorithmRole entity){
        this.checkPercentageValue(entity.getCoursePercent() + entity.getTrainingPercent());
    }
}
    