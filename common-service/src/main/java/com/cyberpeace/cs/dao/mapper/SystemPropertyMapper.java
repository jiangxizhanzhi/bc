package com.cyberpeace.cs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统属性映射器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface SystemPropertyMapper extends BaseMapper<SystemProperty> {

	/**
	 * 查询由名称
	 *
	 * @param name 名称
	 * @return {@code SystemProperty }
	 * @throws
	 * @see String
	 * @see SystemProperty
	 */
	SystemProperty selectByName(String name);

}
