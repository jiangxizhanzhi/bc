package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.MissionSchemeTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 任务方案模板Mapper
 *
 * @author SN
 * @date 2024/11/13
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
	 * 按标题存在
	 *
	 * @param title 标题
	 * @return {@code Boolean }
	 */
	Boolean existByTitle(@Param("title") String title);

}
