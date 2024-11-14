package com.cyberpeace.gateway.controller;

import com.cyberpeace.common.model.BaseRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "外部接口-课程课时api")
@Slf4j
@RestController
@RequestMapping("/gateway/course")
public class ExternalCourseController {

    @ApiOperation("课程列表")
    @GetMapping("/page")
    public BaseRsp getCourseList(){

        return new BaseRsp();
    }

    @ApiOperation("课程详情")
    @GetMapping("/")
    public BaseRsp getCourseDetail(){

        return new BaseRsp();
    }

    @ApiOperation("小节详情")
    @GetMapping("/lesson")
    public BaseRsp getLessonDetail(){

        return new BaseRsp();
    }
}
