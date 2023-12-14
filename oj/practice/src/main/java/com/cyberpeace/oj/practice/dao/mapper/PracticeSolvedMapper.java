package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.controller.res.PracticeLastWeekRes;
import com.cyberpeace.oj.practice.controller.res.OperationRecordRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryListBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Mapper
public interface PracticeSolvedMapper extends BaseMapper<PracticeSolved> {

	PracticeSolved selectUserAndId(@Param("userId") String userId,
			@Param("practiceType") Integer practiceType, @Param("practiceId") String practiceId,
			@Param("isCollect") Boolean isCollect);

	List<PracticeSolved> selectInPracticeIds(@Param("userId") String userId,
			 @Param("practiceType") Integer practiceType, @Param("practiceIds") List<String> practiceIds,
			 @Param("isCollect") Boolean isCollect);

	List<String> selectUserAndSolved(@Param("userId") String userId,
			@Param("practiceType") Integer practiceType, @Param("solved") Boolean solved,
			@Param("isCollect") Boolean isCollect);

	int updateSolved(@Param("solved") PracticeSolved practiceSolved);

	List<PracticeTheoryListBo> selectPracticeTheoryRecord(@Param("userId") String userId,
														  @Param("practiceIds") List<String> practiceIds,
														  @Param("startTime") Date startTime,
														  @Param("endTime") Date endTime,
														  @Param("solved") Integer solved);

	void deleteByPracticeIds(List<String> practiceIds);

	List<OperationRecordRes> getOperationRecord(@Param("userId") String userId,
												@Param("operationIds") List<String> operationIds,
												@Param("solved") Boolean solved,
												@Param("startTime") Date startTime,
												@Param("endTime") Date endTime);

	List<PracticeSolved> selectUserAndIdList(@Param("userId") String userId,
			@Param("practiceType") Integer practiceType, @Param("practiceIdList") List<String> practiceIdList);

	List<PracticeSolved> selectTop5( @Param("practiceId") String practiceId, @Param("userIds") List<String> userIds);

	List<String> selectUserIdByPracticeId(@Param("practiceId") String practiceId);

	List<PracticeLastWeekRes> getCntLastWeek(@Param("practiceType") Integer practiceType, @NotEmpty @Param("ids") List<String> ids);
}
