package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.controller.req.SituationConfigFilterReq;
import com.cyberpeace.situation.dao.entity.SituationConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SituationConfigMapper extends BaseMapper<SituationConfig> {

	/**
	 * 删除配置
	 * @param situationConfigFilterReq
	 * @return
	 */
	List<String> filterIds(
			@Param("situationConfigFilterReq") SituationConfigFilterReq situationConfigFilterReq);

}
