package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;

@Mapper
@Repository
public interface BusinessTemplateMapper extends BaseMapper<BusinessTemplate> {

	IPage<BusinessTemplateData> listTeamTemplate(Page page,
			@Param("businessTemplateQuery") BusinessTemplateQuery businessTemplateQuery);

	List<String> selectIdsByUserId(@Param("userId") String userId);
}
