package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OjLocalSceneMapper extends BaseMapper<OjLocalScene> {

	OjLocalScene selectPrivateScene(@Param("userId") String userId,
			@Param("businessId") String businessId, @Param("isWeb") Boolean isWeb);

	OjLocalScene selectPublicScene(@Param("practiceId") String businessId,
			@Param("shared") Boolean shared, @Param("isWeb") Boolean isWeb);

}
