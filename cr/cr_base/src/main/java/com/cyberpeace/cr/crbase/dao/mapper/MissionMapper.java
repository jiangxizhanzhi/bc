package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;

@Mapper
@Repository
public interface MissionMapper extends BaseMapper<Mission> {

	List<Mission> batchSelect(List<String> resourceIds);

	List<Mission> selectByRoomId(String roomId);

	void batchInsert(List<Mission> missions);

	void batchInsertMissionTemplate(List<MissionTemplate> missionTemplates);

	Mission selectByTitleAndIds(@Param("title") String title,
			@Param("missionIds") List<String> missionIds);
}
