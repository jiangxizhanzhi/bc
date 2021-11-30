package com.cyberpeace.cr.singletrain.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SingleTemplateMapper extends BaseMapper<SingleTemplate> {

    SingleTemplate getByBusinessId(@Param("resource_id") String trainBusinessId);

}
