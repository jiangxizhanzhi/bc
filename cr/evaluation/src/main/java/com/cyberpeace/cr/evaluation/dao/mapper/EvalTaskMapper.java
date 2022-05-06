package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;

@Mapper
@Repository
public interface EvalTaskMapper extends BaseMapper<EvalTask> {

	List<String> selectResourceIdList(String templateId);

}
