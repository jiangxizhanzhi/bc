package com.cyberpeace.cr.report_score.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.report_score.controller.req.ReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.req.ScoreRecordListReq;
import com.cyberpeace.cr.report_score.controller.req.WebReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.res.*;
import com.cyberpeace.cr.report_score.dao.entity.ReportScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface ReportScoreService extends IService<ReportScore> {
    IPage<ReportScoreRsp> selectPage(Page page, ReportScoreListReq reportScoreListReq);

    ReportScoreDetailRsp selectById(@Param("resourceId") String resourceId);

    IPage<ScoreRecordRsp> selectRecordPage(Page page, ScoreRecordListReq scoreRecordListReq);

    List<HashMap<String, String>> selectCountBySubject();

    List<HashMap<String, String>> selectCountByStatus();

    IPage<WebReportScoreRsp> selectWebPage(Page page, WebReportScoreListReq webReportScoreListReq, String userId);

    WebReportScoreDetailRsp selectWebById(@Param("resourceId") String resourceId);

    String saveFile(MultipartFile multipartFile) throws IOException;

    List<HashMap<String, String>> selectWebCountByStatus(String userId);

    HashMap<String, String> getRankScore(String userId);

    void validateReportScoreNameExist(ReportScore reportScore);
}
