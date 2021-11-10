package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.bo.AdminPracticeSetBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PracticeSetServiceImpl extends ServiceImpl<PracticeSetMapper, PracticeSet> implements PracticeSetService {

    @Autowired
    private PracticeSetMapper practiceSetMapper;


    @Override
    public AdminPracticeSetBo selectById(String resourceId, AuthContext authContext) {

        return null;
    }
}
