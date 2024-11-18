package com.cyberpeace.cr.teamtrain.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;

/**
 * 团队模板Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface TeamTemplateMapper extends BaseMapper<TeamTemplate> {

	/**
	 * 列出团队模板
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<TeamTemplateData> }
	 * @throws
	 * @see Page
	 * @see ListQuery
	 * @see IPage<TeamTemplateData>
	 */
	IPage<TeamTemplateData> listTeamTemplate(Page page, ListQuery listQuery);

	/**
	 * 获取由业务id
	 *
	 * @param resourceId 资源id
	 * @return {@code TeamTemplate }
	 * @throws
	 * @see String
	 * @see TeamTemplate
	 */
	TeamTemplate getByBusinessId(String resourceId);

	/**
	 * 获取团队模板由业务id列表
	 *
	 * @param ids id列表
	 * @return {@code List<TeamTemplate> }
	 * @throws
	 * @see List<String>
	 * @see List<TeamTemplate>
	 */
	List<TeamTemplate> getTeamTemplateByBusinessIds(List<String> ids);

	/**
	 * 删除由业务模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean deleteByBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId);

	/**
	 * 批量获取由业务id
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<TeamTemplate> }
	 * @throws
	 * @see List<String>
	 * @see List<TeamTemplate>
	 */
	List<TeamTemplate> batchGetByBusinessId(List<String> businessTemplateIds);
}
