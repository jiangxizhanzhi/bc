package com.cyberpeace.cs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import com.cyberpeace.cs.dao.mapper.SystemPropertyMapper;
import com.cyberpeace.cs.service.SystemPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统配置Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ServiceImpl
 * @see SystemPropertyService
 * @since 1.0.0
 */
@Service
public class SystemPropertyServiceImpl extends ServiceImpl<SystemPropertyMapper, SystemProperty> implements SystemPropertyService {

	/**
	 * 系统属性映射器
	 *
	 * @see SystemPropertyMapper
	 */
	@Autowired
	private SystemPropertyMapper systemPropertyMapper;

	/**
	 * 获取导出内置
	 *
	 * @return boolean
	 * @throws
	 * @see boolean
	 */
	@Override
	public boolean getExportBuiltin() {
		SystemProperty systemProperty = systemPropertyMapper.selectByName(SystemProperty.NameEnum.EXPORT_BUILTIN.getDesc());
		return systemProperty != null && Boolean.parseBoolean(systemProperty.getContent());
	}
}
