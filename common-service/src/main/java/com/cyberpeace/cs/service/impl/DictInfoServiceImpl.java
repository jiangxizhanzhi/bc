package com.cyberpeace.cs.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cs.dao.entity.DictInfo;
import com.cyberpeace.cs.dao.mapper.DictInfoMapper;
import com.cyberpeace.cs.service.DictInfoService;

import lombok.extern.slf4j.Slf4j;

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

	@Override
	public boolean isOperationOpen() {
		DictInfo info = getDictInfoByKey("system_show_operation");
		return null != info && info.getItemValue().equals("true");
	}

	private DictInfo getDictInfoByKey(String key) {
		LambdaQueryWrapper<DictInfo> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(DictInfo::getKey, key);
		return this.getOne(queryWrapper);
	}
}
