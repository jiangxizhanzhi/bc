package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWithBT;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 任务模板Mapper
 *
 * @author SN
 * @date 2024/11/13
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
	 * @param missionTemplateIds 任务模板id列表
	 */
	void batchUpdateBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId,
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 分页跟业务
	 *
	 * @param page       分页
	 * @param type       类型
	 * @param difficulty 难度
	 * @param pattern    模式
	 * @return {@code IPage<MissionTemplateRes> }
	 */
	IPage<MissionTemplateRes> pageWithBusiness(@Param("page") Page page,
			@Param("type") Integer type, @Param("difficulty") Integer difficulty,
			@Param("pattern") String pattern);

	/**
	 * 列表跟业务
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @return {@code List<MissionTemplateRes> }
	 */
	List<MissionTemplateRes>
			listWithBusiness(@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 验证id列表
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @return {@code List<String> }
	 */
	List<String> validateIds(@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 查询由业务模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code List<String> }
	 */
	List<String> selectByBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId);

	/**
	 * 批量获取跟业务
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @return {@code List<MissionWithBT> }
	 */
	List<MissionWithBT>
			batchGetWithBusiness(@Param("missionTemplateIds") List<String> missionTemplateIds);
}
