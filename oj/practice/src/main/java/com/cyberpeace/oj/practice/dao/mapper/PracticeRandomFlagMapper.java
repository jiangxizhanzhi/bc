package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeRandomFlag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 实践随机旗Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface PracticeRandomFlagMapper extends BaseMapper<PracticeRandomFlag> {

	/**
	 * 查询由用户和实践
	 *
	 * @param userId     用户id
	 * @param practiceId 实践id
	 * @param type       类型
	 * @return {@code PracticeRandomFlag }
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 * @see PracticeRandomFlag
	 */
	PracticeRandomFlag selectByUserAndPractice(@Param("userId") String userId,
			@Param("practiceId") String practiceId, @Param("type") Integer type);

	/**
	 * 按练习删除
	 *
	 * @param resourceId 资源id
	 * @param code       代号
	 * @throws
	 * @see String
	 * @see Integer
	 */
	void deleteByPractice(@Param("practiceId") String resourceId, @Param("type") Integer code);
}
