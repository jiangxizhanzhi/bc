package com.cyberpeace.cr.evaluation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalSystem
 */
@Mapper
@Repository
public interface EvalSystemMapper extends BaseMapper<EvalSystem> {

	IPage<EvalSystem> listEvalSystems(Page<EvalSystem> page, EvalListQuery listQuery);

    boolean existByName(String name, String resourceId);

    EvalSystem selectByName(String name);
}




