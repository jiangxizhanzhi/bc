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
 * 实践纸Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface PracticePaperMapper extends BaseMapper<PracticePaper> {

	/**
	 * 获取最后纸由用户id
	 *
	 * @param userId 用户id
	 * @return {@code PracticePaper }
	 * @throws
	 * @see String
	 * @see PracticePaper
	 */
	PracticePaper getLastPaperByUserId(String userId);

	/**
	 * 获取纸记录
	 *
	 * @param userId 用户id
	 * @return {@code List<PaperRecordRes> }
	 * @throws
	 * @see String
	 * @see List<PaperRecordRes>
	 */
	List<PaperRecordRes> getPaperRecord(String userId);

	/**
	 * 获取都纸记录
	 *
	 * @param userId 用户id
	 * @return {@code List<PaperRecordSummaryRes> }
	 * @throws
	 * @see String
	 * @see List<PaperRecordSummaryRes>
	 */
	List<PaperRecordSummaryRes> getAllPaperRecord(@Param("userId") String userId);

	/**
	 * 获取纸记录由纸
	 *
	 * @param paperId 纸id
	 * @return {@code PaperRecordSummaryRes }
	 * @throws
	 * @see String
	 * @see PaperRecordSummaryRes
	 */
	PaperRecordSummaryRes getPaperRecordByPaper(String paperId);

}
