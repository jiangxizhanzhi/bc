package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.req.PracticeListReq;
import com.cyberpeace.oj.practice.dao.constants.PracticeType;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PracticeCategoryImpl extends ServiceImpl<PracticeCategoryMapper, PracticeCategory> implements PracticeCategoryService {

    @Autowired
    PracticeCategoryMapper categoryMapper;

    @Override
    public PracticeCategory get(String resource_id) {
        return categoryMapper.getSingle(resource_id);
    }

    @Override
    public ListRsp<PracticeCategory> search(PracticeListReq queryParams) {
        String tableName;
        if (queryParams.getPracticeType().equals(PracticeType.THEORY.ordinal())) {
            tableName = "practice_theory_category";
        } else if (queryParams.getPracticeType().equals(PracticeType.OPERATION.ordinal())) {
            tableName = "practice_exercise_category";
        } else {
            throw new InvalidParamException("无效参数");
        }
        IPage<PracticeCategory> result = categoryMapper.getList(queryParams.toPageParam(), tableName);
        return PageUtil.getListResult(result);
    }

    @Override
    public Integer partialUpdate(String tableName, String id, String name) {
        return categoryMapper.partialUpdate(tableName, id, name);
    }

    @Override
    public Integer batchDelete(List<String> resourceIds) {
        Integer matchCount1 = categoryMapper.batchDelete("practice_theory_category", resourceIds);
        Integer matchCount2 = categoryMapper.batchDelete("practice_exercise_category", resourceIds);
        return matchCount1 + matchCount2;
    }
}
