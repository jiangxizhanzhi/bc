package com.cyberpeace.cr.singletrain.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SingleRoomMapper extends BaseMapper<SingleRoom> {

    SingleRoom getSingleRoom(@Param("business_template_id") String businessTemplateId, @Param("user_id") String userId);
}
