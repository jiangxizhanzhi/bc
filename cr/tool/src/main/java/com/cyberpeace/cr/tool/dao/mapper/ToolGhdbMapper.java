package com.cyberpeace.cr.tool.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.tool.dao.entity.ToolGhdb;

/**
 * 工具GHDB 数据库Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface ToolGhdbMapper extends BaseMapper<ToolGhdb> {

	/**
	 * 列表
	 *
	 * @param page   分页
	 * @param author 作者
	 * @param title  标题
	 * @return {@code IPage<ToolGhdb> }
	 */
	IPage<ToolGhdb> list(Page<ToolGhdb> page, @Param("author") String author, @Param("title") String title);
}
