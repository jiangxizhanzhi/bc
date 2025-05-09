package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.MissionSchemeTemplate;

/**
 * 任务计划模板映射器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface MissionSchemeTemplateMapper extends BaseMapper<MissionSchemeTemplate> {

	/**
	 * 列表
	 *
	 * @param page    分页
	 * @param pattern 模式
	 * @return {@code IPage<MissionSchemeTemplate> }
	 * @throws
	 * @see Page
	 * @see String
	 * @see IPage<MissionSchemeTemplate>
	 */
	IPage<MissionSchemeTemplate> list(Page page, String pattern);

	/**
	 * 批量插入
	 *
	 * @param missionSchemeTemplates 任务计划模板
	 * @throws
	 * @see List<MissionSchemeTemplate>
	 */
	void batchInsert(
			@Param("missionSchemeTemplates") List<MissionSchemeTemplate> missionSchemeTemplates);

	/**
	 * 按标题存在
	 *
	 * @param title 标题
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean existByTitle(@Param("title") String title);

}
