package com.cyberpeace.cr.redvsblue.dao.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;

@Mapper
@Repository
public interface RbTemplateMapper extends BaseMapper<RbTemplate> {

	RbTemplate selectByBusinessTemplateId(String businessTemplateId);

}
