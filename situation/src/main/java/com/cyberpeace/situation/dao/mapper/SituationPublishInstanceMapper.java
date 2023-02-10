package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceFilterReq;
import com.cyberpeace.situation.controller.res.SituationPublishInstancePageRes;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;

@Mapper
public interface SituationPublishInstanceMapper extends BaseMapper<SituationPublishInstance> {

	IPage<SituationPublishInstancePageRes> getSituationPublishInstancePage(@Param("page") Page page,
			@Param("situationPublishInstanceFilterReq") SituationPublishInstanceFilterReq situationPublishInstanceFilterReq);

	List<String> getAttributeIds(@Param("resourceIds") List<String> resourceIds);

	SituationPublishInstance selectPublicedBySubjectId(@Param("taskId") String taskId,
			@Param("subjectId") String subjectId);

	String selectXdInstanceBySchemeId(@Param("schemeId") String schemeId);

	SituationPublishInstance selectByTemplateId(@Param("templateId") String templateId);
}
