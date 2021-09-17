package com.cyberpeace.evaluate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.AppendixDao;
import com.cyberpeace.evaluate.dao.entity.Appendix;
import com.cyberpeace.evaluate.dao.mapper.AppendixMapper;

@Repository
public class AppendixDaoImpl implements AppendixDao {

    @Autowired
    private AppendixMapper appendixMapper;

    @Override
    public void insert(Appendix appendix) {
        appendixMapper.insert(appendix);
    }

    @Override
    public Appendix selectById(String logo) {
        return appendixMapper.selectById(logo);
    }
}
