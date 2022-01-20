package com.cyberpeace.cr.report_score.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.report_score.controller.req.ReportScoreListReq;
import com.cyberpeace.cr.report_score.controller.req.ScoreRecordListReq;
import com.cyberpeace.cr.report_score.controller.res.ReportScoreDetailRsp;
import com.cyberpeace.cr.report_score.controller.res.ReportScoreRsp;
import com.cyberpeace.cr.report_score.controller.res.ScoreRecordRsp;
import com.cyberpeace.cr.report_score.dao.entity.ReportScore;
import com.cyberpeace.cr.report_score.dao.mapper.ReportScoreMapper;
import com.cyberpeace.cr.report_score.service.ReportScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ReportScoreServiceImpl extends ServiceImpl<ReportScoreMapper, ReportScore> implements ReportScoreService {
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
}
