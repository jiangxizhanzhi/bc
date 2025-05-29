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
/**
 * 这段代码定义了一个名为 `SpaceAlgorithmRoleController` 的 Spring MVC 控制器类，用于处理与“空间算法岗位”相关的请求。以下是对代码的简要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`SpaceAlgorithmRoleController` 
 * - 继承自：`SpaceControllerBase`，这是一个泛型基类，具体类型包括服务层、实体类、业务对象和请求参数。 
 * - 注解： 
 * - `@RestController`：表明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping`：定义了该控制器的默认请求路径为 `/space/algorithm/algorithm_role`。 
 * - `@RequiresAuthentication`：表示此接口需要用户认证后才能访问。 
 * - `@Slf4j`：引入日志功能。 
 * 2. **依赖注入**： 
 * - 使用 `@Autowired` 注入了 `CourseService`，用于操作课程相关的数据。 
 * 3. **方法实现**： 
 * - `getRelatedMap` 方法： 
 * - 根据传入的 `SpaceAlgorithmRole` 列表，提取其中的课程 ID，并通过 `courseService` 获取对应的课程列表。 
 * - 将课程的资源 ID 和名称封装成 `IdAndName` 对象并存入返回的映射中。 
 * - `checkValue` 方法： 
 * - 调用父类的方法 `checkPercentageValue`，验证 `coursePercent` 和 `trainingPercent` 的总和是否符合要求。 
 * 4. **注释与文档**： 
 * - 类上有详细的 Javadoc，描述了类的功能、作者、版本等信息，并指出了它继承自哪个基类。 
 * 总体来说，这个控制器主要负责管理“空间算法岗位”的相关业务逻辑，特别是与课程关联的部分。它通过调用服务层来获取必要的数据，并提供了一些基础的校验功能。 
**/
@Api(tags = "知识库-算法库-岗位能力评估算法")
@RestController
@RequestMapping("/space/algorithm/algorithm_role")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmRoleController extends SpaceControllerBase<SpaceAlgorithmRoleService, SpaceAlgorithmRole, SpaceAlgorithmRoleBo, SpacePageReqBase> {

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

    @Override
    public void checkValue(SpaceAlgorithmRole entity){
        this.checkPercentageValue(entity.getCoursePercent() + entity.getTrainingPercent());
    }
}
    
