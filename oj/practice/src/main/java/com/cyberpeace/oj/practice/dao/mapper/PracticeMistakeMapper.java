package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.controller.res.PersonalMistakeRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeMistake;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 练习错误Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
@Repository
public interface PracticeMistakeMapper extends BaseMapper<PracticeMistake> {

	/**
	 * 计数由用户id
	 *
	 * @param userId      用户id
	 * @param practiceIds 练习id列表
	 * @return {@code Integer }
	 */
	Integer countByUserId(@Param("userId") String userId, @Param("practiceIds") List<String> practiceIds);

	/**
	 * 删除由练习id列表
	 *
	 * @param practiceIds 练习id列表
	 * @param userId      用户id
	 */
	void deleteByPracticeIds(@Param("practiceIds") List<String> practiceIds, @Param("userId") String userId);

	/**
	 * 查询由练习id
	 *
	 * @param practiceIds 练习id列表
	 * @param userId      用户id
	 * @return {@code List<PersonalMistakeRes> }
	 */
	List<PersonalMistakeRes> selectByPracticeId(@Param("practiceIds") List<String> practiceIds, @Param("userId") String userId);
}
