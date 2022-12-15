package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.situation.dao.entity.SituationIconCategory;
import com.cyberpeace.situation.dao.mapper.SituationIconCategoryMapper;
import com.cyberpeace.situation.service.SituationIconCategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午11:55
 */
@Service
public class SituationIconCategoryServiceImpl extends ServiceImpl<SituationIconCategoryMapper, SituationIconCategory> implements SituationIconCategoryService {

    @Autowired
    private SituationIconCategoryMapper situationIconCategoryMapper;

    private volatile Map<String, SituationIconCategory> iconCategoryCache = new HashMap<>();

    @Override
    public List<SituationIconCategory> getSituationCategoryList() {
        if (iconCategoryCache.isEmpty()) {
            init();
        }
        return new ArrayList<>(iconCategoryCache.values());
    }

    public SituationIconCategory getSituationIconCategory(String resourceId) {
        if (iconCategoryCache.containsKey(resourceId)) {
            return iconCategoryCache.get(resourceId);
        }
        return null;
    }

    private void init() {
        List<SituationIconCategory> situationIconCategories = situationIconCategoryMapper.selectAll();
        if (CollectionUtils.isNotEmpty(situationIconCategories)) {
            for (SituationIconCategory situationIconCategory : situationIconCategories) {
                iconCategoryCache.put(situationIconCategory.getResourceId(), situationIconCategory);
            }
        }
    }
}
