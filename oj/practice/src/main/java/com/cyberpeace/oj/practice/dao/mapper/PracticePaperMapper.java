package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.controller.res.PaperRecordRes;
import com.cyberpeace.oj.practice.controller.res.PaperRecordSummaryRes;
import com.cyberpeace.oj.practice.dao.entity.PracticePaper;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PracticePaperMapper extends BaseMapper<PracticePaper> {

	PracticePaper getLastPaperByUserId(String userId);

	List<PaperRecordRes> getPaperRecord(String userId);

	List<PaperRecordSummaryRes> getAllPaperRecord(@Param("userId") String userId);

	PaperRecordSummaryRes getPaperRecordByPaper(String paperId);

}
