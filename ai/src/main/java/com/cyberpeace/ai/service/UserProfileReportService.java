package com.cyberpeace.ai.service;

import java.util.List;

import com.cyberpeace.ai.model.vo.UserProfileReportListVO;
import com.cyberpeace.ai.model.vo.UserProfileReportVO;

public interface UserProfileReportService {

	/**
	 * 保存用户画像报告
	 * @return
	 */
	Boolean save(UserProfileReportVO userProfileReportVO);

	/**
	 * 获取用户画像报告详情
	 * @return
	 */
	UserProfileReportVO getReportDetail(String reportId);

	/**
	 * 获取用户画像报告列表
	 * @return
	 */
	List<UserProfileReportListVO> getReportList();
}