package com.cyberpeace.ai.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.ai.dao.entity.UserProfileReport;
import com.cyberpeace.ai.dao.mapper.UserProfileReportMapper;
import com.cyberpeace.ai.model.enums.UserProfileDimensionEnum;
import com.cyberpeace.ai.model.vo.UserProfileReportListVO;
import com.cyberpeace.ai.model.vo.UserProfileReportVO;
import com.cyberpeace.ai.service.UserProfileReportService;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;

@Service
public class UserProfileReportServiceImpl implements UserProfileReportService {

	@Autowired
	private UserProfileReportMapper userProfileReportMapper;

	@Override
	public Boolean save(UserProfileReportVO userProfileReportVO) {

		// 参数验证
		if (userProfileReportVO == null || userProfileReportVO.getReportId() == null) {
			return false;
		}

		// 构造报告数据
		UserProfileReport report = new UserProfileReport();
		report.setResourceId(userProfileReportVO.getReportId());
		report.setName(userProfileReportVO.getReportName());
		report.setUserId(UserInfoContext.getUserContext().getUserId());
		report.setDimension(Objects
				.requireNonNull(
						UserProfileDimensionEnum.fromLabel(userProfileReportVO.getDimension()))
				.getValue());
		report.setContent(JsonUtil.toJsonString(userProfileReportVO));

		// 检查是否已存在相同报告
		UserProfileReport exist = userProfileReportMapper
				.selectById(userProfileReportVO.getReportId());
		if (exist == null) {
			// 新增报告
			return userProfileReportMapper.insert(report) > 0;
		} else {
			return false;
		}
	}

	@Override
	public UserProfileReportVO getReportDetail(String reportId) {

		// 参数验证
		if (reportId == null || reportId.trim().isEmpty()) {
			return null;
		}

		UserProfileReport report = userProfileReportMapper.selectByReportId(reportId);
		if (report == null)
			return null;

		UserProfileReportVO vo = null;
		// 从JSON字符串反序列化数据
		if (report.getContent() != null && !report.getContent().isEmpty()) {
			try {
				vo = JsonUtil.toJsonObject(report.getContent(), UserProfileReportVO.class);
			} catch (Exception e) {
				// 如果反序列化失败，使用基本字段
				return null;
			}
		} else {
			return null;
		}

		return vo;
	}

	@Override
	public List<UserProfileReportListVO> getReportList() {

		List<UserProfileReportListVO> reports = Lists.newArrayList();

		// 获取当前用户信息
		UserInfo currentUser = UserInfoContext.getUserContext();
		if (currentUser == null) {
			return reports;
		}

		reports = userProfileReportMapper.selectByUserAndDimension(currentUser.getUserId(), null);

		return reports;
	}

}