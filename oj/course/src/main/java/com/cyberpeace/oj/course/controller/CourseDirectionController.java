package com.cyberpeace.oj.course.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.res.DirectionOutPut;
import com.cyberpeace.oj.course.dao.entity.CourseDirection;
import com.cyberpeace.oj.course.dao.mapper.DirectionMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "oj-课程分类相关api")
@RestController
@RequestMapping("/oj/course/direction")
@RequiresAuthentication
public class CourseDirectionController {

    @Autowired
    private DirectionMapper directionMapper;

    // LGD在使用 知识点和技能点绑定课程
    @GetMapping("/list")
    @ApiOperation("课程分类")
    public BaseRsp<List<CourseDirection>> listDirection() {
        List<CourseDirection> courseDirections = directionMapper.selectList(null);

        List<DirectionOutPut> outPuts = courseDirections.stream().map(courseDirection -> {
            DirectionOutPut outPut = BeanUtils.copy(courseDirection, DirectionOutPut.class);
            return outPut;
        }).collect(Collectors.toList());

        BaseRsp rsp = new BaseRsp(outPuts);
        return rsp;
    }



}
