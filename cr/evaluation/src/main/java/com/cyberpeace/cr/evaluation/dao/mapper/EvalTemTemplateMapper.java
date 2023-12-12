package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EvalTemTemplateMapper extends BaseMapper<EvalTemTemplate> {

	IPage<EvalTemTemplate> selectPage(@Param("page") Page page,
									  @Param("type") Integer type,
									  @Param("pattern") String pattern);
}
