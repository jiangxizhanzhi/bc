package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.rtks.dao.entity.AttackTrainRelation;
import com.cyberpeace.rtks.service.bo.career.AttackContext;
import com.cyberpeace.rtks.service.bo.career.RelCount;

@Mapper
@Repository
public interface AttackIcsMapper {

	List<AttackContext> getAll();

	List<AttackTrainRelation> getAllRel();

	List<RelCount> countRelsByAttackId();

	List<String> getRelByBusinessTemplateId(@Param("resourceId") String resourceId);

	List<String> getRelatedTemplateIds(@Param("resourceId") String resourceId);

	void deleteRel(@Param("resourceId") String resourceId,
			@Param("businessTemplateIds") List<String> businessTemplateIds);

	void addRel(@Param("resourceId") String resourceId,
			@Param("businessTemplateIds") List<String> businessTemplateIds);

	List<BusinessTemplateSimple> getRelatedTemplates(@Param("resourceId") String resourceId);

	void clearRel(@Param("businessTemplateIds") List<String> businessTemplateIds);

	void loadRel(@Param("btId") String btId, @Param("attackIcsIds") List<String> attackIcsIds);
}
