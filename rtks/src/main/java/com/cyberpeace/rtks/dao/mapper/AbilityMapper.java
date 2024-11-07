package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.rtks.dao.entity.Ability;

/**
 * 能力Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface AbilityMapper extends BaseMapper<Ability> {

    /**
     * list 能力
     *
     * @param page    分页
     * @param pattern 模式
     * @return {@code IPage<Ability> }
     */
    IPage<Ability> listAbility(Page page, @Param("pattern") String pattern);

    /**
     * 滤波器id列表
     *
     * @param ids id列表
     * @return {@code List<String> }
     */
    List<String> filterIds(List<String> ids);
}
