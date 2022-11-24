package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.AttackMessageHistory;
import com.cyberpeace.situation.model.AttackTypeCount;
import com.cyberpeace.situation.model.DefenseTypeCount;
import com.cyberpeace.situation.model.HostCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttackMessageHistoryMapper extends BaseMapper<AttackMessageHistory> {

	List<AttackTypeCount> getAttackTop(String sceneId);

	List<DefenseTypeCount> getDefenseTop(String sceneId);

	List<HostCount> getHostTop(@Param("sceneId") String sceneId,@Param("attackTypeList") List<String> attackTypeList);

	void recovery(@Param("sceneId") String sceneId);

}
