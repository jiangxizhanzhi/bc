package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.dao.entity.AttackType;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AttackTypeMapper extends BaseMapper<AttackType> {

	IPage<AttackType> selectAll(@Param("page") Page page, @Param("pattern") String pattern);
}
