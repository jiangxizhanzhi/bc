package com.cyberpeace.cr.teamtrain.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 共性平台
 */
@Mapper
@Repository
public interface GXPTSceneMapper {

    void updateTeamRoomByResourceId(@Param("resourceId") String resourceId, @Param("isSychronized") String isSychronized,
                                    @Param("returnData") String data, @Param("returnInfo") String returnData);

    void updateTeamRoomTopoByResourceId(@Param("resourceId") String resourceId, @Param("isSychronized") String isSychronized,
                                        @Param("returnData") String data, @Param("returnInfo") String returnData);
}
