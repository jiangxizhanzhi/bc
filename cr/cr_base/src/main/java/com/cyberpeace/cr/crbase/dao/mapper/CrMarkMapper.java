package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrMark;

/**
 * 靶场标记Mapper
 *
 * @author SN
 * @date 2024/11/13
 */
@Mapper
@Repository
public interface CrMarkMapper extends BaseMapper<CrMark> {

    /**
     * 查询由房间id
     *
     * @param roomId 房间id
     * @return {@code List<CrMark> }
     */
    List<CrMark> selectByRoomId(@Param("roomId") String roomId);

    /**
     * 查询由房间id和用户id
     *
     * @param roomId 房间id
     * @param userId 用户id
     * @return {@code List<CrMark> }
     */
    List<CrMark> selectByRoomIdAndUserId(@Param("roomId") String roomId,
			@Param("userId") String userId);
}
