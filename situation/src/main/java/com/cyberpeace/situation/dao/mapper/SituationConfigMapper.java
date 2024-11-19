package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.req.SituationConfigFilterReq;
import com.cyberpeace.situation.dao.entity.SituationConfig;

/**
 * 态势配置Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SituationConfigMapper extends BaseMapper<SituationConfig> {

	/**
	 * 过滤器id列表
	 *
	 * @param situationConfigFilterReq 态势配置过滤器请求体
	 * @return {@code List<String> }
	 * @throws
	 * @see SituationConfigFilterReq
	 * @see List<String>
	 */
	List<String> filterIds(
			@Param("situationConfigFilterReq") SituationConfigFilterReq situationConfigFilterReq);

}
