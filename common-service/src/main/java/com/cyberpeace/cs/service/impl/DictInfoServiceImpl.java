package com.cyberpeace.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cs.dao.entity.DictInfo;
import com.cyberpeace.cs.dao.mapper.DictInfoMapper;
import com.cyberpeace.cs.service.DictInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/7 17:48
 * @desc :
 **/
@Slf4j
@Service
public class DictInfoServiceImpl extends ServiceImpl<DictInfoMapper, DictInfo> implements DictInfoService {

    @Override
    public List<DictInfo> getDictInfo(String parentKey) {
        LambdaQueryWrapper<DictInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DictInfo::getParentKey,parentKey);
        return list(queryWrapper);
    }
}
