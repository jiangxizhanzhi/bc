package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.controller.res.PaperRecordRes;
import com.cyberpeace.oj.practice.controller.res.PaperRecordSummaryRes;
import com.cyberpeace.oj.practice.dao.entity.PracticePaper;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 练习试卷Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
@Repository
public interface PracticePaperMapper extends BaseMapper<PracticePaper> {

	/**
	 * 获取最后试卷由用户id
	 *
	 * @param userId 用户id
	 * @return {@code PracticePaper }
	 */
	PracticePaper getLastPaperByUserId(String userId);

	/**
	 * 获取试卷记录
	 *
	 * @param userId 用户id
	 * @return {@code List<PaperRecordRes> }
	 */
	List<PaperRecordRes> getPaperRecord(String userId);

	/**
	 * 获取都试卷记录
	 *
	 * @param userId 用户id
	 * @return {@code List<PaperRecordSummaryRes> }
	 */
	List<PaperRecordSummaryRes> getAllPaperRecord(@Param("userId") String userId);

	/**
	 * 获取试卷记录由试卷
	 *
	 * @param paperId 试卷id
	 * @return {@code PaperRecordSummaryRes }
	 */
	PaperRecordSummaryRes getPaperRecordByPaper(String paperId);

}
