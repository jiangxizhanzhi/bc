package com.cyberpeace.oj.rtks.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.rtks.dao.entity.Ability;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AbilityMapper extends BaseMapper<Ability> {

    IPage<Ability> listAbility(Page page, @Param("pattern") String pattern);
}
