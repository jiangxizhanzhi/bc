package com.cyberpeace.oj.practice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.req.PracticeDelReq;
import com.cyberpeace.oj.practice.controller.req.PracticeTheoryListReq;
import com.cyberpeace.oj.practice.controller.req.PracticeTheoryReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "理论基础练习")
@RestController
@RequestMapping("/oj/practice-theory")
public class PracticeTheoryController extends PracticeMixinController<PracticeTheory> {

    @Autowired
    PracticeTheoryMapper practiceTheoryMapper;

    @GetMapping()
    @ApiOperation("获取单个理论基础练习")
    public BaseRsp<PracticeTheory> get(@RequestParam("resource_id") @NotBlank String resourceId) {
        return new BaseRsp<>(practiceTheoryMapper.selectById(resourceId));
    }

    @GetMapping("/list")
    @ApiOperation("获取理论基础练习")
    public BaseRsp<ListRsp<PracticeTheory>> getList(PracticeTheoryListReq queryParams) {
        IPage<PracticeTheory> result = practiceTheoryMapper.getList(
                queryParams.toPageParam(), queryParams.getPattern(), queryParams.getPracticeSet(),
                queryParams.getCategory(), queryParams.getQuestionType()
        );

        return new BaseRsp<>(PageUtil.getListResult(result));
    }

    @PostMapping()
    @ApiOperation("新增理论基础练习")
    public BaseRsp<PracticeTheory> create(@RequestBody PracticeTheoryReq requestData) {
        PracticeTheory object = new PracticeTheory();
        BeanUtils.copyProperties(requestData, object);
        return practiceTheoryMapper.insert(object) != 0 ? new BaseRsp<>(object) : new BaseRsp<>();
    }

    @PatchMapping()
    @ApiOperation("部分更新理论基础练习")
    public BaseRsp<PracticeTheory> partialUpdate(@RequestBody PracticeTheoryReq requestData){
        PracticeTheory object = new PracticeTheory();
        BeanUtils.copyProperties(requestData, object);
        practiceTheoryMapper.updateById(object);

        return new BaseRsp<>(practiceTheoryMapper.selectById(requestData.getResourceId()));
    }

    @DeleteMapping()
    @ApiOperation("批量删除理论基础练习")
    public BaseRsp<Integer> batchDelete(@RequestBody PracticeDelReq requestData) {
        return new BaseRsp<>(practiceTheoryMapper.batchDelete(requestData.getResourceIds()));
    }
}
