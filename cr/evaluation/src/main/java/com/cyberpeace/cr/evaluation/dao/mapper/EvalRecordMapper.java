package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EvalRecordMapper extends BaseMapper<EvalRecord> {
    Page<EvalRecord> pageList(@Param("page") Page page,
                              @Param("room_id") String roomId,
                              @Param("status") Integer status,
                              @Param("pattern") String pattern);
}
