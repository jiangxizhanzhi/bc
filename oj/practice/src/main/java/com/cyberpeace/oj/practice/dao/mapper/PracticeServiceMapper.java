package com.cyberpeace.oj.practice.dao.mapper;

import com.cyberpeace.oj.practice.entity.PracticeBase;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 练习ServiceMapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface PracticeServiceMapper {

    /**
     * 保存解决
     *
     * @param user           用户
     * @param userAnswer     用户答案
     * @param evaluated      评价
     * @param practiceObject 练习对象
     * @param date           日期
     */
    <P extends PracticeBase> void saveSolved(
            @Param("user") String user,
            @Param("answer") String userAnswer,
            @Param("evaluated") EvaluateResult evaluated,
            @Param("object") P practiceObject,
            @Param("now") Date date
    );

    /**
     * 获取解决
     *
     * @param user     用户
     * @param practice 练习
     * @return {@code EvaluateResult }
     */
    EvaluateResult getSolved(@Param("user") String user, @Param("resource_id") String practice);

}
