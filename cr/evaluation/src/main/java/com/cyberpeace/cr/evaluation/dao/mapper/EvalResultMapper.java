package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalResultListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EvalResultMapper extends BaseMapper<EvalResult> {
    Page<EvalResultListRsp> pageList(@Param("page") Page page,
                                     @Param("startTime") String startTime,
                                     @Param("endTime") String endTime,
                                     @Param("type") Integer type,
                                     @Param("pattern") String pattern);

}
