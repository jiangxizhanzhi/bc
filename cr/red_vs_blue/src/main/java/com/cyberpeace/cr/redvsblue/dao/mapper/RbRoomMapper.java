package com.cyberpeace.cr.redvsblue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRoomData;

@Mapper
@Repository
public interface RbRoomMapper extends BaseMapper<RbRoom> {

	List<RbRoom> selectAll();

	Integer countAll();

	RbRoomData getRbRoomDataByRoomId(String resourceId);
}
