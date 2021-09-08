package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.req.PracticeListReq;
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
    public ListRsp<PracticeCategory> search(PracticeListReq queryParams) {
        IPage<PracticeCategory> result = categoryMapper.getList(
                queryParams.toPageParam(),
                queryParams.getPattern(),
                queryParams.getPracticeType()
        );
        return PageUtil.getListResult(result);
    }

    @Override
    public Integer batchDelete(List<String> resourceIds) {
        return categoryMapper.batchDelete(resourceIds);
    }
}
