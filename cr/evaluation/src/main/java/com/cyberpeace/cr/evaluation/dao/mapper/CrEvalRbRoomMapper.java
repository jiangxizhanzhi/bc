package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalRbRoom;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CrEvalRbRoomMapper extends BaseMapper<CrEvalRbRoom> {

    RbRoomData getRbRoomDataByRoomId(@Param("resourceId") String resourceId);

}
