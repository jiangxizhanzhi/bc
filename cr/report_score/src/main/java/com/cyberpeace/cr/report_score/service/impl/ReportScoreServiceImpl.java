package com.cyberpeace.cr.report_score.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.report_score.controller.req.ReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.req.ScoreRecordListReq;
import com.cyberpeace.cr.report_score.controller.req.WebReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.res.*;
import com.cyberpeace.cr.report_score.dao.entity.ReportScore;
import com.cyberpeace.cr.report_score.dao.mapper.ReportScoreMapper;
import com.cyberpeace.cr.report_score.service.ReportScoreService;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReportScoreServiceImpl extends ServiceImpl<ReportScoreMapper, ReportScore> implements ReportScoreService {

    @Autowired
    private RemoteUserService remoteUserService;

    private final String savePath = "report" + File.separator + "report" + File.separator;

    private String uploadPath;

    @PostConstruct
    public void init() {
        uploadPath = ConfigUtil.getContextProperty("cr.local.upload.path") + File.separator
                + savePath;
        ensureDir(uploadPath);
    }

    private void ensureDir(String uploadPath) {
        File f = new File(uploadPath);

        if (!f.exists() || !f.isDirectory()) {
            f.mkdirs();
        }
    }


    @Autowired
    private ReportScoreMapper reportScoreMapper;

    @Override
    public IPage<ReportScoreRsp> selectPage(Page page, ReportScoreListReq reportScoreListReq) {
        IPage<ReportScoreRsp> result = reportScoreMapper.list(page, reportScoreListReq);
        return result;
    }

    @Override
    public ReportScoreDetailRsp selectById(String resourceId) {
        ReportScore reportScore = getById(resourceId);
        ReportScoreDetailRsp copy = BeanUtils.copy(reportScore, ReportScoreDetailRsp.class);
        return copy;
    }

    @Override
    public IPage<ScoreRecordRsp> selectRecordPage(Page page, ScoreRecordListReq scoreRecordListReq) {
        IPage<ScoreRecordRsp> result = reportScoreMapper.listRecord(page, scoreRecordListReq);
        return result;
    }

    @Override
    public List<HashMap<String, String>> selectCountBySubject() {
        List<HashMap<String, String>> result = reportScoreMapper.countSubject();
        return result;
    }

    @Override
    public List<HashMap<String, String>> selectCountByStatus() {
        List<HashMap<String, String>> result = reportScoreMapper.countStatus();
        return result;
    }

    @Override
    public IPage<WebReportScoreRsp> selectWebPage(Page page, WebReportScoreListReq webReportScoreListReq, String userId) {
        IPage<WebReportScoreRsp> result = reportScoreMapper.ListWeb(page, webReportScoreListReq, userId);
        return result;
    }

    @Override
    public WebReportScoreDetailRsp selectWebById(String resourceId) {
        ReportScore reportScore = getById(resourceId);
        WebReportScoreDetailRsp copy = BeanUtils.copy(reportScore, WebReportScoreDetailRsp.class);
        return copy;
    }

    @Override
    public String saveFile(MultipartFile multipartFile) throws IOException {
        File file = FileUtil.saveFile(multipartFile, uploadPath);
        return file.getAbsolutePath();
    }

    @Override
    public List<HashMap<String, String>> selectWebCountByStatus(String userId) {
        List<HashMap<String, String>> result = reportScoreMapper.countWebStatus(userId);
        return result;
    }

    @Override
    public RankingRep getRankScore(String userId) {
        List<RankingRep> rankingRepList = reportScoreMapper.getRankScore();
        for (RankingRep rankingRep : rankingRepList) {
            if(rankingRep.getUserId().equals(userId)) {
                return rankingRep;
            }
        }
        return null;
    }

    @Override
    public void validateReportScoreNameExist(ReportScore reportScore) {
        // 名称重复性校验
        ReportScore reportScoreObject = reportScoreMapper.selectByName(reportScore.getReportName(), reportScore.getUserId());
        if (reportScoreObject != null) {
            throw new BusinessException(CrBusinessErrorCode.DUPLICATED_REPORT_SCORE_NAME);
        }
    }
}
