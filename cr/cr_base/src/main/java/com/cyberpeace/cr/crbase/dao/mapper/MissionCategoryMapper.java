package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.MissionCategory;

/**
 * @author SN
 */
@Mapper
@Repository
public interface MissionCategoryMapper extends BaseMapper<MissionCategory> {

	List<MissionCategory> selectAll();

	List<MissionCategory> selectByIds(List<String> missionsCategoryIds);
}
