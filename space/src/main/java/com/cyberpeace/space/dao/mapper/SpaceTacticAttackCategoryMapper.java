package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.space.dao.entity.SpaceTacticAttackCategory;
import com.cyberpeace.space.service.context.tactic.TacticAttackCategoryBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 空间战法攻击类别Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceTacticAttackCategoryMapper extends BaseMapper<SpaceTacticAttackCategory> {
    IPage<TacticAttackCategoryBo> page(@Param("page") Page page, @Param("pattern") String pattern);
}
