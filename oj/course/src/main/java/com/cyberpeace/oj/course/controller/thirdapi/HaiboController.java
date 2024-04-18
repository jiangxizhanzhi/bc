package com.cyberpeace.oj.course.controller.thirdapi;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.annoations.AuthKeyUser;
import com.cyberpeace.common.annoations.AuthOjKey;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.CourseCategoryBo;
import com.cyberpeace.oj.course.service.bo.CourseQueryBo;
import com.cyberpeace.oj.course.service.bo.CourseWebBo;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "oj-前台课程分类相关api")
@RestController
@RequestMapping("/oj/haibo/course")
public class HaiboController {

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseCategoryService courseDirectionService;

    @Autowired
    private LessonConfigService lessonConfigService;

    @GetMapping("/direction")
    @ApiOperation("课程分类")
    @AuthOjKey
    public BaseRsp<List<CourseCategoryBo>>
    listDirection(@RequestParam(value = "children", required = false) Boolean children) {
        List<CourseCategoryBo> categories = courseCategoryService
                .selectAll(null != children ? children : CommonConstant.FALSE);

        return new BaseRsp(categories);
    }

    @GetMapping("/list")
    @ApiOperation("课程分页查询")
    @AuthOjKey
    public BaseRsp<List<CourseWebBo>> getWebCourse(
            @ApiParam(name = "listReq", value = "查询条件") @Validated CourseListReq listReq) {
        // 数据组装
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("3f655d87112f4ae094bbaf70830e20aa");
        userInfo.setSuperuser(true);
        UserInfoContext.setUserContext(userInfo);

        CourseQueryBo context = BeanUtils.copy(listReq, CourseQueryBo.class);
        context.setUserId(UserInfoContext.getUserContext().getUserId());
        if (StringUtils.isNotBlank(listReq.getDirection())) {
            List<String> childrenIds = courseDirectionService.getChildrenIds(listReq.getDirection(),
                    CommonConstant.TRUE);
            context.setDirections(childrenIds);
        }

        Page page = listReq.toPageParam();
        if (CollectionUtils.isEmpty(page.getOrders())) {
            PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
        }

        // 权限数据获取
        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
                true);

        // 数据查询
        ListRsp<CourseWebBo> boListRsp = courseService.selectPage(page, context, authContext, true);
        return new BaseRsp(boListRsp);
    }

    @GetMapping("/lessonconfig")
    @ApiOperation("课时查询")
    @AuthOjKey
    public BaseRsp<LessonConfigBo> getLessonConfig(@ApiParam(name = "course_id",
            value = "课程编号") @RequestParam("course_id") String courseId) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("3f655d87112f4ae094bbaf70830e20aa");
        userInfo.setSuperuser(true);
        UserInfoContext.setUserContext(userInfo);

        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
                CommonConstant.TRUE);
        LessonConfigBo config = lessonConfigService.getLessonConfig(courseId, authContext);
        return new BaseRsp<>(config);
    }




}
