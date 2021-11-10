package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.Mission;

@Mapper
@Repository
public interface MissionMapper extends BaseMapper<Mission> {

	List<Mission> batchGet(@Param("resource_ids") List<String> resourceIds);
}
