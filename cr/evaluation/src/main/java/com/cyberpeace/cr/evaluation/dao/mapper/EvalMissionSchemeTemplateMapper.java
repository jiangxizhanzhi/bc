package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionSchemeTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EvalMissionSchemeTemplateMapper extends BaseMapper<EvalMissionSchemeTemplate> {

	List<EvalMissionSchemeTemplate> getTemplateList(@Param("pattern") String pattern);


}
