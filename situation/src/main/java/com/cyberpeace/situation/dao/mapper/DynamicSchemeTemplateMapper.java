package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.DynamicSchemeTemplate;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DynamicSchemeTemplateMapper extends BaseMapper<DynamicSchemeTemplate> {

	List<DynamicSchemeTemplate> selectAll();

}
