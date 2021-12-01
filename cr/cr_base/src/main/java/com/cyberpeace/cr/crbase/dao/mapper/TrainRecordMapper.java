package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface TrainRecordMapper extends BaseMapper<TrainRecord> {
    void setEndTime(
            @Param("business_template_id") String businessTemplateId,
            @Param("user_id") String userId,
            @Param("business_type") Integer businessType
    );
}
