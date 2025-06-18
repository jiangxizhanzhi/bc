package com.cyberpeace.ai.service;

import com.cyberpeace.ai.model.vo.UserProfileReportVO;

public interface UserProfileService {

	UserProfileReportVO getUserProfileData(String dimension);
}
