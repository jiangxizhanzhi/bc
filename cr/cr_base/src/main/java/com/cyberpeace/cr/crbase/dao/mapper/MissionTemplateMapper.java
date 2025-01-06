package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWithBT;

@Mapper
@Repository
public interface MissionTemplateMapper extends BaseMapper<MissionTemplate> {

	void batchInsert(@Param("missionTemplates") List<MissionTemplate> missionTemplates);

	void batchUpdateBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId,
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	IPage<MissionTemplateRes> pageWithBusiness(@Param("page") Page page,
			@Param("type") Integer type, @Param("difficulty") Integer difficulty,
			@Param("pattern") String pattern);

	List<MissionTemplateRes>
			listWithBusiness(@Param("missionTemplateIds") List<String> missionTemplateIds);

	List<String> validateIds(@Param("missionTemplateIds") List<String> missionTemplateIds);

	List<String> selectByBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId);

	List<MissionWithBT>
			batchGetWithBusiness(@Param("missionTemplateIds") List<String> missionTemplateIds);
}
