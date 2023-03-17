package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.dao.entity.SituationDbAccessAuthority;

@Mapper
public interface SituationDbAccessAuthorityMapper extends BaseMapper<SituationDbAccessAuthority> {

	IPage<SituationDbAccessAuthority> selectPage(@Param("page") Page page);

	List<String> selectAllowedIps();

}
