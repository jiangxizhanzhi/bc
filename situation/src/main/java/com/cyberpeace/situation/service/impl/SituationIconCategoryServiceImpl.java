package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.situation.controller.req.SituationIconCategoryReq;
import com.cyberpeace.situation.controller.req.SituationIconReq;
import com.cyberpeace.situation.dao.entity.SituationIconCategory;
import com.cyberpeace.situation.dao.mapper.SituationIconCategoryMapper;
import com.cyberpeace.situation.service.SituationIconCategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午11:55
 */
@Service
public class SituationIconCategoryServiceImpl extends ServiceImpl<SituationIconCategoryMapper, SituationIconCategory> implements SituationIconCategoryService {

    @Autowired
    private SituationIconCategoryMapper situationIconCategoryMapper;

    @Override
    public List<SituationIconCategory> getSituationCategoryList(String name) {
        List<SituationIconCategory> situationIconCategories = situationIconCategoryMapper.selectAll();
        List<SituationIconCategory> list  = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(situationIconCategories)) {
            if (StringUtils.isEmpty(name)) {
                return situationIconCategories;
            }
            situationIconCategories.forEach(situationIconCategory -> {
                if (situationIconCategory.getName().contains(name)) {
                    list.add(situationIconCategory);
                }
            });
        }
        return list;
    }

    @Override
    public SituationIconCategory getSituationIconCategory(String resourceId) {
        return situationIconCategoryMapper.selectById(resourceId);
    }

    @Override
    public String updateSituationIconCategory(SituationIconCategoryReq situationIconCategoryReq) {
        String name = situationIconCategoryReq.getName();
        String resourceId = situationIconCategoryReq.getResourceId();
        validateName(name, resourceId);
        SituationIconCategory situationIconCategory = changeReqToSituationIconCategory(situationIconCategoryReq);
        situationIconCategoryMapper.updateById(situationIconCategory);
        return situationIconCategoryReq.getResourceId();
    }

    @Override
    public Integer deleteSituationIconCategory(List<String> idList) {
        Integer delete = situationIconCategoryMapper.deleteBatchIds(idList);
        return delete;
    }

    @Override
    public String createSituationIcon(SituationIconCategoryReq situationIconCategoryReq) {
        String name = situationIconCategoryReq.getName();
        validateName(name, null);
        SituationIconCategory situationIconCategory = changeReqToSituationIconCategory(situationIconCategoryReq);
        situationIconCategory.setResourceId(IDGeneratorUtil.getID());
        situationIconCategoryMapper.insert(situationIconCategory);
        return situationIconCategory.getResourceId();
    }

    private void validateName(String name, String resourceId) {
        String selectResourceId = situationIconCategoryMapper.selectByName(name);
        if (StringUtils.isNotBlank(resourceId)) {
            if (StringUtils.isNotBlank(selectResourceId) && !Objects.equals(resourceId, selectResourceId)) {
                throw new BusinessException(ErrorCode.NAME_DUPLICATE);
            }
        } else {
            if (StringUtils.isNotBlank(selectResourceId)) {
                throw new BusinessException(ErrorCode.NAME_DUPLICATE);
            }
        }
    }
    private SituationIconCategory changeReqToSituationIconCategory(SituationIconCategoryReq situationIconCategoryReq){
        return BeanUtils.copy(situationIconCategoryReq, SituationIconCategory.class);
    }
}
