package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import com.cyberpeace.rtks.service.bo.career.TechRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.rtks.dao.entity.AttackTrainRelation;
import com.cyberpeace.rtks.service.bo.career.AttackContext;
import com.cyberpeace.rtks.service.bo.career.RelCount;

/**
 * 攻击集成电路Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface AttackIcsMapper {

	/**
	 * 获取都
	 *
	 * @return {@code List<AttackContext> }
	 */
	List<AttackContext> getAll();

	/**
	 * 获取都相对
	 *
	 * @return {@code List<AttackTrainRelation> }
	 */
	List<AttackTrainRelation> getAllRel();

	/**
	 * 计数rels由攻击id
	 *
	 * @return {@code List<RelCount> }
	 */
	List<RelCount> countRelsByAttackId();

	/**
	 * 获取相对由业务模板id
	 *
	 * @param resourceId 资源id
	 * @return {@code List<String> }
	 */
	List<String> getRelByBusinessTemplateId(@Param("resourceId") String resourceId);

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
	 * 加载 rel
	 *
	 * @param btId         BT系列id
	 * @param attackIcsIds 攻击集成电路id列表
	 */
	void loadRel(@Param("btId") String btId, @Param("attackIcsIds") List<String> attackIcsIds);

	/**
	 * 查询rels由BT系列id列表
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<TechRelation> }
	 */
	List<TechRelation> selectRelsByBtIds(@Param("businessTemplateIds") List<String> businessTemplateIds);
}
