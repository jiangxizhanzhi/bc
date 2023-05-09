package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.dao.entity.SituationDbAccessAuthority;

/**
 * 数据库访问权限映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
public interface SituationDbAccessAuthorityMapper extends BaseMapper<SituationDbAccessAuthority> {

	/**
	 * 分页查询
	 *
	 * @param page 分页条件
	 * @return {@code IPage<SituationDbAccessAuthority>}
	 */
	IPage<SituationDbAccessAuthority> selectPage(@Param("page") Page page);

	/**
	 * 获取允许访问的IP列表
	 *
	 * @return {@code List<String>}
	 */
	List<String> selectAllowedIps();

}
