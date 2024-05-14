package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;

@Mapper
@Repository
public interface CrConstantsConfigMapper {

	List<CrConstantsConfig> getAll();

    List<CrConstantsConfig> selectLables();
}
