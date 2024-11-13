package com.cyberpeace.cs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统配置Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
public interface SystemPropertyMapper extends BaseMapper<SystemProperty> {

	/**
	 * 查询由名称
	 *
	 * @param name 名称
	 * @return {@code SystemProperty }
	 */
	SystemProperty selectByName(String name);

}
