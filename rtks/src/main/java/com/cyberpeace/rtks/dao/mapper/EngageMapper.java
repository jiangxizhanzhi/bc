package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.rtks.dao.entity.EngageTrainRelation;
import com.cyberpeace.rtks.service.bo.career.EngageContext;
import com.cyberpeace.rtks.service.bo.career.RelCount;
import com.cyberpeace.rtks.service.bo.career.TechRelation;

@Mapper
@Repository
public interface EngageMapper {

	List<EngageContext> getAll();

	List<RelCount> countRelsByEngageId();

	List<String> getRelatedTemplateIds(@Param("resourceId") String resourceId);

	void deleteRel(@Param("resourceId") String resourceId,
			@Param("businessTemplateIds") List<String> businessTemplateIds);

	void addRel(@Param("resourceId") String resourceId,
			@Param("businessTemplateIds") List<String> businessTemplateIds);

	List<String> getRelByBusinessTemplateId(@Param("resourceId") String resourceId);

	List<BusinessTemplateSimple> getRelatedTemplates(@Param("resourceId") String resourceId);

	void clearRel(@Param("businessTemplateIds") List<String> businessTemplateIds);

	Integer lockRel();

	List<EngageTrainRelation> getAllRel();

	Integer countAll();

	void loadRel(@Param("btId") String btId, @Param("engageIds") List<String> engageIds);

	List<TechRelation>
			selectRelsByBtIds(@Param("businessTemplateIds") List<String> businessTemplateIds);

	List<EngageTrainRelation> getAllRel();

	void clearAllRel();

	void insertBatch(@Param("relList") List<EngageTrainRelation> relList);
}
