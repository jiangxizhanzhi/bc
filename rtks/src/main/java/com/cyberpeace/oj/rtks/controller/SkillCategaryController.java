package com.cyberpeace.oj.rtks.controller;

import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.model.PageReqBase;
import com.cyberpeace.oj.rtks.controller.req.AbilityAddReq;
import com.cyberpeace.oj.rtks.controller.req.DeleteReq;
import com.cyberpeace.oj.rtks.controller.req.SkillCategaryOrSystemAddReq;
import com.cyberpeace.oj.rtks.dao.entity.SkillCategary;
import com.cyberpeace.oj.rtks.service.SkillCategaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "RTKS-技能类别管理")
@RestController
@RequestMapping("/rtks/skill-categary")
public class SkillCategaryController {

    @Autowired
    private SkillCategaryService skillCategaryService;

    @ApiOperation("查询单条")
    @GetMapping()
    public BaseRsp<SkillCategary> getSkill(@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
        // ApiParam中的name和java参数String id的两个id必须同名
        SkillCategary skillCategary = skillCategaryService.getById(resource_id);

        return new BaseRsp<>(skillCategary);
    }

    @ApiOperation("新增单条")
    @PostMapping()
    public BaseRsp<String> addSkill(@RequestBody @Validated SkillCategaryOrSystemAddReq skillCatagaryOrSystemAddReq) {
        SkillCategary skillCategary = new SkillCategary();
        BeanUtils.copyProperties(skillCatagaryOrSystemAddReq, skillCategary);
        skillCategaryService.save(skillCategary);

        return new BaseRsp<>();
    }

    @ApiOperation("修改单条")
    @PutMapping()
    public BaseRsp<String> modifySkill(@RequestBody @Validated AbilityAddReq skillCatagaryOrSystemAddReq) {
        SkillCategary skillCategary = new SkillCategary();
        BeanUtils.copyProperties(skillCatagaryOrSystemAddReq, skillCategary);
        skillCategaryService.updateById(skillCategary);

        return new BaseRsp<>();
    }

    @ApiOperation("删除")
    @DeleteMapping()
    public BaseRsp<String> deleteTask(@RequestBody @Validated DeleteReq deleteReq) {
        skillCategaryService.removeByIds(deleteReq.getIds());

        return new BaseRsp<>();
    }

    @ApiOperation("分页查询")
    @GetMapping("/list")
    public BaseRsp<List<SkillCategary>> list(@Validated PageReqBase pageReqBase) {
        ListRsp<SkillCategary> data = skillCategaryService.page(pageReqBase);

        return new BaseRsp(data);
    }
}
