package com.cyberpeace.oj.practice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.req.PracticeRandomChoiceReq;
import com.cyberpeace.oj.practice.controller.req.PracticeTheoryListReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.externalBo.PracticeCategorySimple;
import com.cyberpeace.oj.practice.service.externalBo.PracticeSetSimple;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "oj-外部接口-题库引用")
@RestController
@RequestMapping("/oj/practice-external")
public class ExternalController {

    @Autowired
    PracticeTheoryMapper practiceTheoryMapper;

    @Autowired
    PracticeSetMapper practiceSetMapper;

    @Autowired
    PracticeCategoryMapper practiceCategoryMapper;

    private final String secretKey = "Cmycjs1bWUHsKb8r";

    private final String headerKey = "OPT-OJ-KEY";

    /**
     * 接口认证(key获取方式后续可能调整)
     * @param key headers: OPT-OJ-KEY
     */
    private void allowAccess(String key) {
        if (!key.equals(this.secretKey)) {
            throw new UnauthorizedException("secretKey is failed.");
        }
    }

    @ApiOperation("理论基础习题集-全量获取")
    @GetMapping("/theory/practice-set/list")
    public BaseRsp<List<PracticeSetSimple>> getPracticeSetList(@RequestHeader(headerKey) @NotBlank String key) {
        allowAccess(key);
        return new BaseRsp<>(practiceSetMapper.getAll());
    }

    @ApiOperation("理论基础分类-全量获取")
    @GetMapping("/theory/category/list")
    public BaseRsp<List<PracticeCategorySimple>> getCategoryList(@RequestHeader(headerKey) @NotBlank String key) {
        allowAccess(key);
        return new BaseRsp<>(practiceCategoryMapper.getAll());
    }

    @ApiOperation("理论基础题库-分页查询")
    @GetMapping("/theory/list")
    public BaseRsp<ListRsp<PracticeTheory>> getTheoryList(@RequestHeader(headerKey) @NotBlank String key, PracticeTheoryListReq queryParams) {
        allowAccess(key);
        IPage<PracticeTheory> result = practiceTheoryMapper.getList(
                queryParams.toPageParam(),
                queryParams.getPattern(),
                queryParams.getPracticeSet(),
                queryParams.getCategory(),
                queryParams.getQuestionType(),
                queryParams.getDifficulty()
        );
        return new BaseRsp<>(PageUtil.getListResult(result));
    }

    @ApiOperation("理论基础题库-批量查询")
    @GetMapping("/theory/batch-get")
    public BaseRsp<List<PracticeTheory>> batchGet(@RequestHeader(headerKey) @NotBlank String key, @RequestParam("resource_ids") @NotBlank List<String> resourceIds) {
        allowAccess(key);
        if (resourceIds == null) {
            return new BaseRsp<>(Lists.newArrayList());
        }
        return new BaseRsp<>(practiceTheoryMapper.batchGet(resourceIds));
    }

    @ApiOperation("理论基础题库-随机获取数条")
    @GetMapping("/theory/random-choice")
    public BaseRsp<List<PracticeTheory>> getTheoryRandom(@RequestHeader(headerKey) @NotBlank String key, PracticeRandomChoiceReq queryParams) {
        allowAccess(key);
        List<PracticeTheory> result = practiceTheoryMapper.randomChoice(
                queryParams.getPattern(),
                queryParams.getPracticeSet(),
                queryParams.getCategory(),
                queryParams.getQuestionType(),
                queryParams.getDifficulty(),
                queryParams.getCount()
        );
        for (PracticeTheory item : result) {
            item.setScore(queryParams.getScore());
        }
        return new BaseRsp<>(result);
    }

}
