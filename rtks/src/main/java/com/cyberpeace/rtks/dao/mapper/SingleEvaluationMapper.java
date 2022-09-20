package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.rtks.service.bo.xiamen2c.EvaluationSkillBo;
import com.cyberpeace.rtks.service.bo.xiamen2c.EvaluationTraineeBo;
import com.cyberpeace.rtks.service.bo.xiamen2c.MissionIdAndTitle;
import com.cyberpeace.rtks.service.bo.xiamen2c.SkillMissionUserContext;

@Mapper
public interface SingleEvaluationMapper {

	IPage<EvaluationSkillBo> listSkills(@Param("page") Page page, @Param("roleId") String roleId);

	List<SkillMissionUserContext>
			getTrainingStatisticsBySkillIds(@Param("skillIds") List<String> skillIds);

	List<MissionIdAndTitle> listMissionBySkillId(@Param("skillId") String skillId);

	List<String> getTraineeIdsByMissionIds(@Param("missionIds") List<String> missionIds);

	IPage<EvaluationTraineeBo> listTrainees(@Param("page") Page page,
			@Param("roleId") String roleId);
}
