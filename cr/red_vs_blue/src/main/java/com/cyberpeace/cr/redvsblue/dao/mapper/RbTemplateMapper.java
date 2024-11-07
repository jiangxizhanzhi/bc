package com.cyberpeace.cr.redvsblue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateData;

/**
 * RB模板Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
public interface RbTemplateMapper extends BaseMapper<RbTemplate> {

	/**
	 * 查询由业务模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code RbTemplate }
	 */
	RbTemplate selectByBusinessTemplateId(String businessTemplateId);

	/**
	 * 列出 RB 模板
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<RbTemplateData> }
	 */
	IPage<RbTemplateData> listRbTemplate(Page page, ListQuery listQuery);

	/**
	 * 获取RB模板由业务id列表
	 *
	 * @param ids id列表
	 * @return {@code List<RbTemplate> }
	 */
	List<RbTemplate> getRbTemplateByBusinessIds(List<String> ids);

	/**
	 * 删除由业务模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code Boolean }
	 */
	Boolean deleteByBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId);

	/**
	 * 批量获取由业务id
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<RbTemplate> }
	 */
	List<RbTemplate> batchGetByBusinessId(List<String> businessTemplateIds);
}
