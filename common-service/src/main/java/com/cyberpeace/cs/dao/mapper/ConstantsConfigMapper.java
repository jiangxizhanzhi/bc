package com.cyberpeace.cs.dao.mapper;

import com.cyberpeace.cs.dao.entity.ConstantsConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ConstantsConfigMapper {

	List<ConstantsConfig> getAll();
}
