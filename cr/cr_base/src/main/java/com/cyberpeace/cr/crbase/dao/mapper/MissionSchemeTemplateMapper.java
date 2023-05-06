package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.MissionSchemeTemplate;

/**
 * 任务计划模板映射器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface MissionSchemeTemplateMapper extends BaseMapper<MissionSchemeTemplate> {

	/**
	 * 获取列表
	 *
	 * @param page    页面
	 * @param pattern 模式
	 * @return {@link IPage}<{@link MissionSchemeTemplate}>
	 */
	IPage<MissionSchemeTemplate> list(Page page, String pattern);

	/**
	 * 批量插入
	 *
	 * @param missionSchemeTemplates 任务计划模板
	 */
	void batchInsert(
			@Param("missionSchemeTemplates") List<MissionSchemeTemplate> missionSchemeTemplates);

	/**
	 * 存在标题
	 *
	 * @param title 标题
	 * @return {@link Boolean}
	 */
	Boolean existByTitle(@Param("title") String title);

}
