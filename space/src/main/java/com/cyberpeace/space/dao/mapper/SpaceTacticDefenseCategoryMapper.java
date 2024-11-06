package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.space.service.context.tactic.TacticDefenseCategoryBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceTacticDefenseCategory;

import java.util.List;

@Mapper
@Repository
public interface SpaceTacticDefenseCategoryMapper extends BaseMapper<SpaceTacticDefenseCategory> {

    IPage<TacticDefenseCategoryBo> page(@Param("page") Page page, @Param("pattern") String pattern);

    List<TacticDefenseCategoryBo> listAllInOrder();
}
