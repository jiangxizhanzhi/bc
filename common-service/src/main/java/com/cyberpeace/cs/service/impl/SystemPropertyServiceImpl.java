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
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为`SystemPropertyServiceImpl`的服务实现类，用于处理系统配置相关的业务逻辑。以下是简要分析： 
 * 1. **继承与接口实现**： 
 * - `SystemPropertyServiceImpl`继承自`ServiceImpl<SystemPropertyMapper, SystemProperty>`，这是MyBatis-Plus提供的基础服务类，简化了数据库操作。 
 * - 实现了`SystemPropertyService`接口，表明该类提供了系统的配置服务功能。 
 * 2. **依赖注入**： 
 * - 使用`@Autowired`注解将`SystemPropertyMapper`注入到类中，用于执行数据库查询操作。 
 * 3. **核心方法**： 
 * - 方法`getExportBuiltin()`根据配置名称`SystemProperty.NameEnum.EXPORT_BUILTIN.getDesc()`从数据库中查询对应的配置项，并返回其内容是否为布尔值`true`。 
 * 4. **用途**： 
 * - 该类主要用于管理系统的特定配置（如导出内置功能的状态），通过数据库中的配置表进行动态读取和应用。 
 * 总结：这是一个典型的基于MyBatis-Plus的服务实现类，专注于获取特定系统配置的功能。 
**/
@Service
public class SystemPropertyServiceImpl extends ServiceImpl<SystemPropertyMapper, SystemProperty> implements SystemPropertyService {

	/**
	 * 系统配置Mapper
	 */
	@Autowired
	private SystemPropertyMapper systemPropertyMapper;

	/**
	 * 获取导出内置
	 *
	 * @return boolean
	 */
	@Override
	public boolean getExportBuiltin() {
		SystemProperty systemProperty = systemPropertyMapper.selectByName(SystemProperty.NameEnum.EXPORT_BUILTIN.getDesc());
		return systemProperty != null && Boolean.parseBoolean(systemProperty.getContent());
	}
}

