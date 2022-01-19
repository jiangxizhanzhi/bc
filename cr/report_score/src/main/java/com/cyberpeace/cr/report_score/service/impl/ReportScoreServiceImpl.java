package com.cyberpeace.cr.report_score.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.report_score.controller.res.ReportScoreRsp;
import com.cyberpeace.cr.report_score.dao.entity.ReportScore;
import com.cyberpeace.cr.report_score.dao.mapper.ReportScoreMapper;
import com.cyberpeace.cr.report_score.service.ReportScoreService;
import org.springframework.stereotype.Service;

@Service
public class ReportScoreServiceImpl extends ServiceImpl<ReportScoreMapper, ReportScore> implements ReportScoreService {

	@Override
	public IPage<ReportScoreRsp> selectPage(Page page) {
		return null;
	}
}
