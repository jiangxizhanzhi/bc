package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.SystemProperty;

/**
 * 系统配置Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface SystemPropertyService extends IService<SystemProperty> {

	/**
	 * 获取导出内置
	 *
	 * @return boolean
	 */
	boolean getExportBuiltin();
}
