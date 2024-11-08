package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;

/**
 * 靶场常数配置Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface CrConstantsConfigMapper {

	/**
	 * 获取都
	 *
	 * @return {@code List<CrConstantsConfig> }
	 */
	List<CrConstantsConfig> getAll();
}
