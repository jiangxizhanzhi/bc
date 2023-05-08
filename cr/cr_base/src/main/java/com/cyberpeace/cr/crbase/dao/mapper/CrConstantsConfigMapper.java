package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;

/**
 * cr常量配置映射器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface CrConstantsConfigMapper {

	/**
	 * 获取所有
	 *
	 * @return {@link List}<{@link CrConstantsConfig}>
	 */
	List<CrConstantsConfig> getAll();
}
