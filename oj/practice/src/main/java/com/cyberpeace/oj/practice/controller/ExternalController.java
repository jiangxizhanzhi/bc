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
import com.cyberpeace.oj.practice.service.simpleBo.PracticeCategorySimple;
import com.cyberpeace.oj.practice.service.simpleBo.PracticeSetSimple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "外部接口-题库引用")
@RestController
@RequestMapping("/oj/practice-external")
public class ExternalController {

    @Autowired
    PracticeTheoryMapper practiceTheoryMapper;

    @Autowired
    PracticeSetMapper practiceSetMapper;

    @Autowired
    PracticeCategoryMapper practiceCategoryMapper;

    @ApiOperation("理论基础习题集-全量获取")
    @GetMapping("/theory/practice-set/list")
    public BaseRsp<List<PracticeSetSimple>> getPracticeSetList() {
        return new BaseRsp<>(practiceSetMapper.getAll());
    }

    @ApiOperation("理论基础分类-全量获取")
    @GetMapping("/theory/category/list")
    public BaseRsp<List<PracticeCategorySimple>> getCategoryList() {
        return new BaseRsp<>(practiceCategoryMapper.getAll());
    }

    @ApiOperation("理论基础题库-分页查询")
    @GetMapping("/theory/list")
    public BaseRsp<ListRsp<PracticeTheory>> getTheoryList(PracticeTheoryListReq queryParams) {
        IPage<PracticeTheory> result = practiceTheoryMapper.getList(
                queryParams.toPageParam(),
                queryParams.getPattern(),
                queryParams.getPracticeSet(),
                queryParams.getCategory(),
                queryParams.getQuestionType()
        );
        return new BaseRsp<>(PageUtil.getListResult(result));
    }

    @ApiOperation("理论基础题库-随机获取数条")
    @GetMapping("/theory/random-choice")
    public BaseRsp<List<PracticeTheory>> getTheoryRandom(PracticeRandomChoiceReq queryParams) {
        List<PracticeTheory> result = practiceTheoryMapper.randomChoice(
                queryParams.getPattern(),
                queryParams.getPracticeSet(),
                queryParams.getCategory(),
                queryParams.getQuestionType(),
                queryParams.getCount()
        );
        for (PracticeTheory item : result) {
            item.setScore(queryParams.getScore());
        }
        return new BaseRsp<>(result);
    }

}
