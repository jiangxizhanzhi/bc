package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.MissionCategory;

/**
 * @author SN
 */
public interface MissionCategoryService extends IService<MissionCategory> {

	@Override
	List<MissionCategory> list();
}
