package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.SystemProperty;

/**
 * 系统财产Service
 *
 * @author SN
 * @date 2024/11/07
 */
public interface SystemPropertyService extends IService<SystemProperty> {

	/**
	 * 获取导出内置
	 *
	 * @return boolean
	 */
	boolean getExportBuiltin();
}
