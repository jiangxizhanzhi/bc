package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.situation.dao.entity.DynamicSchemeTemplate;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Mapper
@Repository
public interface DynamicSchemeTemplateMapper extends BaseMapper<DynamicSchemeTemplate> {

	IPage<DynamicSchemeTemplate> selectAll(Page page);

}
