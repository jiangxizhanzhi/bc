package com.cyberpeace.cr.report_score.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.report_score.controller.req.ReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.req.ScoreRecordListReq;
import com.cyberpeace.cr.report_score.controller.req.WebReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.res.ReportScoreRsp;
import com.cyberpeace.cr.report_score.controller.res.ScoreRecordRsp;
import com.cyberpeace.cr.report_score.controller.res.WebReportScoreRsp;
import com.cyberpeace.cr.report_score.dao.entity.ReportScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface ReportScoreMapper extends BaseMapper<ReportScore> {
    IPage<ReportScoreRsp> list(Page page, @Param("listQuery") ReportScoreListReq reportScoreListReq);

    IPage<ScoreRecordRsp> listRecord(Page page, @Param("listQuery") ScoreRecordListReq scoreRecordListReq);

    List<HashMap<String, String>> countSubject();

    List<HashMap<String, String>> countStatus();

    IPage<WebReportScoreRsp> ListWeb(Page page, @Param("listQuery") WebReportScoreListReq webReportScoreListReq, String userId);

    List<HashMap<String, String>> countWebStatus(String userId);
}
