package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.rtks.dao.entity.AttackTrainRelation;
import com.cyberpeace.rtks.service.bo.career.AttackContext;
import com.cyberpeace.rtks.service.bo.career.RelCount;
import com.cyberpeace.rtks.service.bo.career.TechRelation;

@Mapper
@Repository
public interface AttackMapper {

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

	Integer countAll();

	void loadRel(@Param("btId") String btId, @Param("attackIds") List<String> attackIds);

	List<TechRelation>
			selectRelsByBtIds(@Param("businessTemplateIds") List<String> businessTemplateIds);

	void clearAllRel();

	void insertBatch(@Param("relList") List<AttackTrainRelation> relList);
}
