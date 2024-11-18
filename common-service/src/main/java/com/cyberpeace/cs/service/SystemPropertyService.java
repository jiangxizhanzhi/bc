package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.SystemProperty;

/**
 * 系统配置Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see IService
 * @since 1.0.0
 */
public interface SystemPropertyService extends IService<SystemProperty> {

	/**
	 * 获取导出内置
	 *
	 * @return boolean
	 * @throws
	 * @see boolean
	 */
	boolean getExportBuiltin();
}
