package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;

/**
 * 靶场常数配置映射
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Mapper
@Repository
public interface CrConstantsConfigMapper {

	/**
	 * 获取都
	 *
	 * @return {@code List<CrConstantsConfig> }
	 * @throws
	 * @see List<CrConstantsConfig>
	 */
	List<CrConstantsConfig> getAll();
}
