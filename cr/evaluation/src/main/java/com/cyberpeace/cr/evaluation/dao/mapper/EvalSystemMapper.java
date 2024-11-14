package com.cyberpeace.cr.evaluation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 测评系统Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface EvalSystemMapper extends BaseMapper<EvalSystem> {

    /**
     * 列表测评系统
     *
     * @param page      分页
     * @param listQuery 列表查询
     * @return {@code IPage<EvalSystem> }
     */
    IPage<EvalSystem> listEvalSystems(Page<EvalSystem> page, EvalListQuery listQuery);

    /**
     * 存在由名称
     *
     * @param name       名称
     * @param resourceId 资源id
     * @return boolean
     */
    boolean existByName(String name, String resourceId);
}




