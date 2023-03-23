package com.cyberpeace.situation.dao.mapper;

import com.cyberpeace.situation.model.RvbAsset;
import com.cyberpeace.situation.model.RvbBusiness;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WHLrvbMapper {

    RvbBusiness getBusiness(@Param("scene_id") String sceneId);

    List<RvbAsset> getRvbAsset(@Param("business_id") String businessId);
}
