package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.controller.res.PersonalMistakeRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeMistake;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实践错误Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface PracticeMistakeMapper extends BaseMapper<PracticeMistake> {

	/**
	 * 计数由用户id
	 *
	 * @param userId      用户id
	 * @param practiceIds 实践id列表
	 * @return {@code Integer }
	 */
	Integer countByUserId(@Param("userId") String userId, @Param("practiceIds") List<String> practiceIds);

	/**
	 * 删除由实践id列表
	 *
	 * @param practiceIds 实践id列表
	 * @param userId      用户id
	 */
	void deleteByPracticeIds(@Param("practiceIds") List<String> practiceIds, @Param("userId") String userId);

	/**
	 * 查询由实践id
	 *
	 * @param practiceIds 实践id列表
	 * @param userId      用户id
	 * @return {@code List<PersonalMistakeRes> }
	 */
	List<PersonalMistakeRes> selectByPracticeId(@Param("practiceIds") List<String> practiceIds, @Param("userId") String userId);
}
