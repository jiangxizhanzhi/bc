package com.cyberpeace.cr.teamtrain.dao.mapper;

import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplateData;
import com.cyberpeace.cr.teamtrain.dao.entity.CourseLessonUserRelation;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface XdDemoDataMapper {

    List<TeamTemplateData> selectTemplates();

    List<CrTask> selectCompletedTasks(@Param("templateId") String templateId, @Param("type") int type,
                                     @Param("teacherId") String teacherId, @Param("start") Date start,
                                     @Param("end") Date end);

    List<CourseLessonUserRelation> selectCourseRelations(@Param("templateId") String templateId);

    void insertRun(@Param("businessDate") LocalDate businessDate);

    int lockRun(@Param("businessDate") LocalDate businessDate);

    void completeRun(@Param("businessDate") LocalDate businessDate);

    void insertRoom(@Param("roomId") String roomId, @Param("sourceRoomId") String sourceRoomId,
                    @Param("businessDate") LocalDate businessDate, @Param("topoDigest") String topoDigest);
}
