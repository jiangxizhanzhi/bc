package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 评估记录Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface EvalRecordMapper extends BaseMapper<EvalRecord> {
    /**
     * 分页列表
     *
     * @param page    分页
     * @param task_id 任务id
     * @param status  状态
     * @param pattern 模式
     * @return {@code Page<EvalRecordListRsp> }
     */
    Page<EvalRecordListRsp> pageList(@Param("page") Page page,
                                     @Param("task_id") String task_id,
                                     @Param("status") Integer status,
                                     @Param("pattern") String pattern);
}
