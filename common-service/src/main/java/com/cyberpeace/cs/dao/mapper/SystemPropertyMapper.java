package com.cyberpeace.cs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemPropertyMapper extends BaseMapper<SystemProperty> {

	SystemProperty selectByName(String name);

}
