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

/**
 * 聘Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface EngageMapper {

	/**
	 * 获取都
	 *
	 * @return {@code List<EngageContext> }
	 */
	List<EngageContext> getAll();

	/**
	 * 计数rels由聘id
	 *
	 * @return {@code List<RelCount> }
	 */
	List<RelCount> countRelsByEngageId();

	/**
	 * 获取相关模板id列表
	 *
	 * @param resourceId 资源id
	 * @return {@code List<String> }
	 */
	List<String> getRelatedTemplateIds(@Param("resourceId") String resourceId);

	/**
	 * 删除 rel
	 *
	 * @param resourceId          资源id
	 * @param businessTemplateIds 业务模板id列表
	 */
	void deleteRel(@Param("resourceId") String resourceId,
			@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 新增相对
	 *
	 * @param resourceId          资源id
	 * @param businessTemplateIds 业务模板id列表
	 */
	void addRel(@Param("resourceId") String resourceId,
			@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 获取相对由业务模板id
	 *
	 * @param resourceId 资源id
	 * @return {@code List<String> }
	 */
	List<String> getRelByBusinessTemplateId(@Param("resourceId") String resourceId);

	/**
	 * 获取相关模板
	 *
	 * @param resourceId 资源id
	 * @return {@code List<BusinessTemplateSimple> }
	 */
	List<BusinessTemplateSimple> getRelatedTemplates(@Param("resourceId") String resourceId);

	/**
	 * 清除 rel
	 *
	 * @param businessTemplateIds 业务模板id列表
	 */
	void clearRel(@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 锁定 rel
	 *
	 * @return {@code Integer }
	 */
	Integer lockRel();

	/**
	 * 加载 rel
	 *
	 * @param btId      BT系列id
	 * @param engageIds 聘id列表
	 */
	void loadRel(@Param("btId") String btId, @Param("engageIds") List<String> engageIds);

	/**
	 * 查询rels由BT系列id列表
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<TechRelation> }
	 */
	List<TechRelation>
			selectRelsByBtIds(@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 获取都相对
	 *
	 * @return {@code List<EngageTrainRelation> }
	 */
	List<EngageTrainRelation> getAllRel();

	/**
	 * 清除所有 rel
	 */
	void clearAllRel();

	/**
	 * 插入批量
	 *
	 * @param relList rel 列表
	 */
	void insertBatch(@Param("relList") List<EngageTrainRelation> relList);
}
