package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.controller.req.SituationConfigFilterReq;
import com.cyberpeace.situation.dao.entity.SituationConfig;

/**
 * 场景自定义配置映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
@Repository
public interface SituationConfigMapper extends BaseMapper<SituationConfig> {

	/**
	 * 过滤配置ID
	 *
	 * @param situationConfigFilterReq 过滤条件
	 * @return {@code List<String>}
	 */
	List<String> filterIds(
			@Param("situationConfigFilterReq") SituationConfigFilterReq situationConfigFilterReq);

}
