package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.req.SituationConfigFilterReq;
import com.cyberpeace.situation.dao.entity.SituationConfig;

@Mapper
@Repository
public interface SituationConfigMapper extends BaseMapper<SituationConfig> {

	List<String> filterIds(
			@Param("situationConfigFilterReq") SituationConfigFilterReq situationConfigFilterReq);

}
