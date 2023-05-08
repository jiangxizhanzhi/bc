package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.practice.controller.res.PaperRecordRes;
import com.cyberpeace.oj.practice.controller.res.PaperRecordSummaryRes;
import com.cyberpeace.oj.practice.dao.entity.PersonalPaper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PersonalPaperMapper extends BaseMapper<PersonalPaper> {

	PersonalPaper getLastPaperByUserId(String userId);

	List<PaperRecordRes> getPaperRecord(String userId);

	List<PaperRecordSummaryRes> getAllPaperRecord(String userId);

}
