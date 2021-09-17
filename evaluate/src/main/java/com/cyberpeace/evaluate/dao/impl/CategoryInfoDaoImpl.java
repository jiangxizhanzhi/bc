package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.CategoryInfoDao;
import com.cyberpeace.evaluate.dao.entity.CategoryInfo;
import com.cyberpeace.evaluate.dao.mapper.CategoryInfoMapper;

@Repository
public class CategoryInfoDaoImpl implements CategoryInfoDao {

    @Autowired
    private CategoryInfoMapper categoryInfoMapper;

    @Override
    public List<CategoryInfo> selectByType(Integer type) {
        return categoryInfoMapper.selectByType(type);
    }
}
