package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.controller.res.OperationRecordRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryListBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 实践解决Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
public interface PracticeSolvedMapper extends BaseMapper<PracticeSolved> {

	/**
	 * 查询用户和id
	 *
	 * @param userId       用户id
	 * @param practiceType 执业类型
	 * @param practiceId   实践id
	 * @param isCollect    是收集
	 * @return {@code PracticeSolved }
	 */
	PracticeSolved selectUserAndId(@Param("userId") String userId,
			@Param("practiceType") Integer practiceType, @Param("practiceId") String practiceId,
			@Param("isCollect") Boolean isCollect);

	/**
	 * 查询在实践id列表
	 *
	 * @param userId       用户id
	 * @param practiceType 执业类型
	 * @param practiceIds  实践id列表
	 * @param isCollect    是收集
	 * @return {@code List<PracticeSolved> }
	 */
	List<PracticeSolved> selectInPracticeIds(@Param("userId") String userId,
			 @Param("practiceType") Integer practiceType, @Param("practiceIds") List<String> practiceIds,
			 @Param("isCollect") Boolean isCollect);

	/**
	 * 查询用户和解决
	 *
	 * @param userId       用户id
	 * @param practiceType 执业类型
	 * @param solved       解决
	 * @param isCollect    是收集
	 * @return {@code List<String> }
	 */
	List<String> selectUserAndSolved(@Param("userId") String userId,
			@Param("practiceType") Integer practiceType, @Param("solved") Boolean solved,
			@Param("isCollect") Boolean isCollect);

	/**
	 * 更新已解决
	 *
	 * @param practiceSolved 练习解决
	 * @return int
	 */
	int updateSolved(@Param("solved") PracticeSolved practiceSolved);

	/**
	 * 查询实践理论记录
	 *
	 * @param userId      用户id
	 * @param practiceIds 实践id列表
	 * @param startTime   开始时间
	 * @param endTime     结束时间
	 * @param solved      解决
	 * @return {@code List<PracticeTheoryListBo> }
	 */
	List<PracticeTheoryListBo> selectPracticeTheoryRecord(@Param("userId") String userId,
														  @Param("practiceIds") List<String> practiceIds,
														  @Param("startTime") Date startTime,
														  @Param("endTime") Date endTime,
														  @Param("solved") Integer solved);

	/**
	 * 删除由实践id列表
	 *
	 * @param practiceIds 实践id列表
	 */
	void deleteByPracticeIds(List<String> practiceIds);

	/**
	 * 获取实操记录
	 *
	 * @param userId       用户id
	 * @param operationIds 实操id列表
	 * @param solved       解决
	 * @param startTime    开始时间
	 * @param endTime      结束时间
	 * @return {@code List<OperationRecordRes> }
	 */
	List<OperationRecordRes> getOperationRecord(@Param("userId") String userId,
												@Param("operationIds") List<String> operationIds,
												@Param("solved") Boolean solved,
												@Param("startTime") Date startTime,
												@Param("endTime") Date endTime);
}
