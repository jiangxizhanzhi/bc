package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 评估系统Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
public interface EvalSystemMapper extends BaseMapper<EvalSystem> {

    /**
     * 列出 EVAL Systems
     *
     * @param page      分页
     * @param listQuery 列表查询
     * @return {@code IPage<EvalSystem> }
     * @throws
     * @see Page
     * @see EvalListQuery
     * @see IPage<EvalSystem>
     */
    IPage<EvalSystem> listEvalSystems(Page page, EvalListQuery listQuery);

    /**
     * 存在由名称
     *
     * @param name       名称
     * @param resourceId 资源id
     * @return boolean
     * @throws
     * @see String
     * @see String
     * @see boolean
     */
    boolean existByName(String name, String resourceId);
}




