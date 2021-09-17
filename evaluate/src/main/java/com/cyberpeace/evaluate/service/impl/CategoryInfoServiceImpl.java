package com.cyberpeace.evaluate.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.evaluate.dao.CategoryInfoDao;
import com.cyberpeace.evaluate.dao.entity.CategoryInfo;
import com.cyberpeace.evaluate.service.CategoryInfoService;
import com.cyberpeace.evaluate.service.bo.CategoryInfoBO;
import com.google.common.collect.Lists;

@Service
public class CategoryInfoServiceImpl implements CategoryInfoService {

    @Autowired
    private CategoryInfoDao categoryInfoDao;

    @Override
    public List<CategoryInfoBO> selectByType(Integer type) {
        List<CategoryInfo> categoryInfos = categoryInfoDao.selectByType(type);
        List<CategoryInfoBO> categoryInfoBOS = Lists.newArrayList();

        categoryInfos.forEach(categoryInfo -> {
            CategoryInfoBO infoBO = new CategoryInfoBO();
            BeanUtils.copyProperties(categoryInfo,infoBO);
            categoryInfoBOS.add(infoBO);
        });

        return categoryInfoBOS;
    }
}
