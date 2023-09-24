package com.cyberpeace.cr.singletrain.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;

@Mapper
@Repository
public interface SingleTemplateMapper extends BaseMapper<SingleTemplate> {

    SingleTemplate getByBusinessId(@Param("resource_id") String trainBusinessId);

    List<SingleTemplate> batchGetByBusinessId(List<String> businessTemplateIds);

}
