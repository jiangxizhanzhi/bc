package com.cyberpeace.ai.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.ai.dao.entity.UserProfileReport;
import com.cyberpeace.ai.model.vo.UserProfileReportListVO;

@Mapper
@Repository
public interface UserProfileReportMapper extends BaseMapper<UserProfileReport> {

	/**
	 * 根据报告ID获取报告详情
	 * @param reportId 报告ID
	 * @return 用户画像报告
	 */
	UserProfileReport selectByReportId(@Param("reportId") String reportId);

	/**
	 * 根据用户ID和维度获取报告列表
	 * @param userId 用户ID
	 * @param dimension 维度（可为空）
	 * @return 报告列表
	 */
	List<UserProfileReportListVO> selectByUserAndDimension(@Param("userId") String userId,
			@Param("dimension") Integer dimension);

}