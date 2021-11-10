package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.crbase.dao.entity.MissionCategory;
import com.cyberpeace.cr.crbase.dao.mapper.MissionCategoryMapper;
import com.cyberpeace.cr.crbase.service.MissionCategoryService;

/**
 * @author SN
 */
@Service
public class MissionCategoryServiceImpl extends ServiceImpl<MissionCategoryMapper, MissionCategory> implements MissionCategoryService {

	@Autowired
	private MissionCategoryMapper missionCategoryMapper;

	@Override
	public List<MissionCategory> list() {
		return missionCategoryMapper.listMissionCategories();
	}
}
