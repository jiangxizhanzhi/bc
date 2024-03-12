package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCloudTemplate;
import com.cyberpeace.cr.evaluation.service.context.EvalCloudTemplateListContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface EvalCloudTemplateMapper extends BaseMapper<EvalCloudTemplate> {

	List<EvalCloudTemplateListContext> selectList(@Param("type") Integer type, @Param("name") String name);

	String getTopoIdById(@Param("resourceId") String resourceId);
}




