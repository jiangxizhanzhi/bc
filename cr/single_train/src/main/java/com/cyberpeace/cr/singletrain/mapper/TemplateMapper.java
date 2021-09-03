package com.cyberpeace.cr.singletrain.mapper;

import com.cyberpeace.cr.singletrain.model.template.Template;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
@Repository
public interface TemplateMapper extends BaseMapper<Template> {

}
