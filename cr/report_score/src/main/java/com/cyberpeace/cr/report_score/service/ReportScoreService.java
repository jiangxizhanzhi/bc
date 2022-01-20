package com.cyberpeace.cr.report_score.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.report_score.controller.req.ReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.req.ScoreRecordListReq;
import com.cyberpeace.cr.report_score.controller.res.ReportScoreDetailRsp;
import com.cyberpeace.cr.report_score.controller.res.ReportScoreRsp;
import com.cyberpeace.cr.report_score.controller.res.ScoreRecordRsp;
import com.cyberpeace.cr.report_score.dao.entity.ReportScore;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ReportScoreService extends IService<ReportScore> {
    IPage<ReportScoreRsp> selectPage(Page page, ReportScoreListReq reportScoreListReq);

    ReportScoreDetailRsp selectById(@Param("resourceId") String resourceId);

    IPage<ScoreRecordRsp> selectRecordPage(Page page, ScoreRecordListReq scoreRecordListReq);

    List<HashMap<String, String>> selectCountBySubject();

    List<HashMap<String, String>> selectCountByStatus();
}
