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
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Mapper
@Repository
public interface AttackIcsMapper {

	/**
	 * 获取全部
	 *
	 * @return {@code List<AttackContext> }
	 * @throws
	 * @see List<AttackContext>
	 */
	List<AttackContext> getAll();

	/**
	 * 获取全部关联
	 *
	 * @return {@code List<AttackTrainRelation> }
	 * @throws
	 * @see List<AttackTrainRelation>
	 */
	List<AttackTrainRelation> getAllRel();

	/**
	 * 计数rels由攻击id
	 *
	 * @return {@code List<RelCount> }
	 * @throws
	 * @see List<RelCount>
	 */
	List<RelCount> countRelsByAttackId();

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
	 * 加载关联
	 *
	 * @param btId         BT系列id
	 * @param attackIcsIds 攻击集成电路id列表
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	void loadRel(@Param("btId") String btId, @Param("attackIcsIds") List<String> attackIcsIds);

	/**
	 * 查询rels由BT系列id列表
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<TechRelation> }
	 * @throws
	 * @see List<String>
	 * @see List<TechRelation>
	 */
	List<TechRelation> selectRelsByBtIds(@Param("businessTemplateIds") List<String> businessTemplateIds);
}
