package com.cyberpeace.oj.person.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDataClearanceMapper {
    void clearCourseStudyHis(@Param("userId") String userId);

    void clearLessonRecord(@Param("userId") String userId);

    void clearOjNote(@Param("userId") String userId);

    void clearPracticeCount(@Param("userId") String userId);

    void clearPracticeMistake(@Param("userId") String userId);

    void clearPracticeSolved(@Param("userId") String userId);

    void clearResourceCollection(@Param("userId") String userId);
}
