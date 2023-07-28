package com.cyberpeace.oj.practice.dao.mapper;

import com.cyberpeace.oj.practice.entity.PracticeBase;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface PracticeServiceMapper {

    <P extends PracticeBase> void saveSolved(
            @Param("user") String user,
            @Param("answer") String userAnswer,
            @Param("evaluated") EvaluateResult evaluated,
            @Param("object") P practiceObject,
            @Param("now") Date date
    );

    EvaluateResult getSolved(@Param("user") String user, @Param("resource_id") String practice);

}
