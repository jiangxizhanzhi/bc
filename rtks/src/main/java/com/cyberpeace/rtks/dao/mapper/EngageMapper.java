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
 * EngageMapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Mapper
@Repository
public interface EngageMapper {

	/**
	 * 获取全部
	 *
	 * @return {@code List<EngageContext> }
	 * @throws
	 * @see List<EngageContext>
	 */
	List<EngageContext> getAll();

	/**
	 * 计数rels由Engageid
	 *
	 * @return {@code List<RelCount> }
	 * @throws
	 * @see List<RelCount>
	 */
	List<RelCount> countRelsByEngageId();

	/**
	 * 获取相关模板id列表
	 *
	 * @param resourceId 资源id
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	List<String> getRelatedTemplateIds(@Param("resourceId") String resourceId);

	/**
	 * 删除关联
	 *
	 * @param resourceId          资源id
	 * @param businessTemplateIds 业务模板id列表
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	void deleteRel(@Param("resourceId") String resourceId,
			@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 新增关联
	 *
	 * @param resourceId          资源id
	 * @param businessTemplateIds 业务模板id列表
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	void addRel(@Param("resourceId") String resourceId,
			@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 获取关联由业务模板id
	 *
	 * @param resourceId 资源id
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	List<String> getRelByBusinessTemplateId(@Param("resourceId") String resourceId);

	/**
	 * 获取相关模板
	 *
	 * @param resourceId 资源id
	 * @return {@code List<BusinessTemplateSimple> }
	 * @throws
	 * @see String
	 * @see List<BusinessTemplateSimple>
	 */
	List<BusinessTemplateSimple> getRelatedTemplates(@Param("resourceId") String resourceId);

	/**
	 * 清除关联
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @throws
	 * @see List<String>
	 */
	void clearRel(@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 锁关联
	 *
	 * @return {@code Integer }
	 * @throws
	 * @see Integer
	 */
	Integer lockRel();

	/**
	 * 加载关联
	 *
	 * @param btId      BT系列id
	 * @param engageIds Engageid列表
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	void loadRel(@Param("btId") String btId, @Param("engageIds") List<String> engageIds);

	/**
	 * 查询rels由BT系列id列表
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<TechRelation> }
	 * @throws
	 * @see List<String>
	 * @see List<TechRelation>
	 */
	List<TechRelation>
			selectRelsByBtIds(@Param("businessTemplateIds") List<String> businessTemplateIds);

	/**
	 * 获取全部关联
	 *
	 * @return {@code List<EngageTrainRelation> }
	 * @throws
	 * @see List<EngageTrainRelation>
	 */
	List<EngageTrainRelation> getAllRel();

	/**
	 * 清除全部关联
	 *
	 * @throws
	 */
	void clearAllRel();

	/**
	 * 插入批量
	 *
	 * @param relList 关联列表
	 * @throws
	 * @see List<EngageTrainRelation>
	 */
	void insertBatch(@Param("relList") List<EngageTrainRelation> relList);
}
