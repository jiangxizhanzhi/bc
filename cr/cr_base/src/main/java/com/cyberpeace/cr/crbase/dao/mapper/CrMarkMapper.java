package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrMark;

@Mapper
@Repository
public interface CrMarkMapper extends BaseMapper<CrMark> {

	List<CrMark> selectByRoomId(@Param("roomId") String roomId);

	List<CrMark> selectByRoomIdAndUserId(@Param("roomId") String roomId,
			@Param("userId") String userId);
}
