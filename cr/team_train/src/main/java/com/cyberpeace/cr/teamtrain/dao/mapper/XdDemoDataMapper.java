package com.cyberpeace.cr.teamtrain.dao.mapper;

import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface XdDemoDataMapper {

    List<TeamRoom> selectSources();

    void insertRun(@Param("businessDate") LocalDate businessDate);

    void completeRun(@Param("businessDate") LocalDate businessDate, @Param("count") int count);

    void insertRoom(@Param("roomId") String roomId, @Param("sourceRoomId") String sourceRoomId,
                    @Param("businessDate") LocalDate businessDate, @Param("topoDigest") String topoDigest);
}
