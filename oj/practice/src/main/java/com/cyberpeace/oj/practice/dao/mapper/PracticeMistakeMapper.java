package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.controller.res.PersonalMistakeRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeMistake;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PracticeMistakeMapper extends BaseMapper<PracticeMistake> {

	Integer countByUserId(@Param("userId") String userId, @Param("practiceType") Integer practiceType);

	void deleteByPracticeIds(List<String> practiceId);

	List<PersonalMistakeRes> selectByPracticeId(@Param("practiceIds") List<String> practiceIds, @Param("userId") String userId);
}
