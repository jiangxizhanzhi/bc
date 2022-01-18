package com.cyberpeace.cr.report_score.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.report_score.controller.res.ReportScoreRsp;
import com.cyberpeace.cr.report_score.dao.entity.ReportScore;

public interface ReportScoreService extends IService<ReportScore> {
    IPage<ReportScoreRsp> selectPage(Page page);
}
