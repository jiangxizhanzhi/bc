package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWithBT;

/**
 * 任务模板映射器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface MissionTemplateMapper extends BaseMapper<MissionTemplate> {

	/**
	 * 批量插入
	 *
	 * @param missionTemplates 任务模板
	 */
	void batchInsert(@Param("missionTemplates") List<MissionTemplate> missionTemplates);

	/**
	 * 批量更新业务模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @param missionTemplateIds 任务模板id
	 */
	void batchUpdateBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId,
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 业务分页数据
	 *
	 * @param page       页面
	 * @param type       类型
	 * @param difficulty 难度
	 * @param pattern    模式
	 * @return {@link IPage}<{@link MissionTemplateRes}>
	 */
	IPage<MissionTemplateRes> pageWithBusiness(@Param("page") Page page,
			@Param("type") Integer type, @Param("difficulty") Integer difficulty,
			@Param("pattern") String pattern);

	/**
	 * 业务列表
	 *
	 * @param missionTemplateIds 任务模板id
	 * @return {@link List}<{@link MissionTemplateRes}>
	 */
	List<MissionTemplateRes>
			listWithBusiness(@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 验证id
	 *
	 * @param missionTemplateIds 任务模板id
	 * @return {@link List}<{@link String}>
	 */
	List<String> validateIds(@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 通过业务模板id获取
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@link List}<{@link String}>
	 */
	List<String> selectByBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId);

	/**
	 * 批量获取业务
	 *
	 * @param missionTemplateIds 任务模板id
	 * @return {@link List}<{@link MissionWithBT}>
	 */
	List<MissionWithBT>
			batchGetWithBusiness(@Param("missionTemplateIds") List<String> missionTemplateIds);
}
