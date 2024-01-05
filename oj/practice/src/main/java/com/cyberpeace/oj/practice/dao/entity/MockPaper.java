package com.cyberpeace.oj.practice.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.oj.practice.service.bo.mock.MockStatus;

import lombok.Data;

@Data
@TableName("oj_mock_paper")
public class MockPaper extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	private String title;

	private String presetId;

	/**
	 * 理论length
	 */
	private Integer theoryLength;

	/**
	 * 实操length
	 */
	private Integer operationLength;

	/**
	 * 理论status
	 */
	private Integer theoryStatus;

	private Date theoryStartTime;

	private Date theoryEndTime;

	/**
	 * 实操status
	 */
	private Integer operationStatus;

	private Date operationStartTime;

	private Date operationEndTime;

	/**
	 * 理论描述
	 */
	private String theoryDesc;

	/**
	 * 实操描述
	 */
	private String operationDesc;

	private Integer theoryScore;

	private Integer operationScore;

	private Integer count;

	private String userId;

	private boolean actual;

	private String logo;

	public Date getExpectedTheoryEndTime() {
		return TimeUtil.addMins(this.theoryStartTime, this.theoryLength);
	}

	public Date getExpectedOperationEndTime() {
		return TimeUtil.addMins(this.operationStartTime, this.operationLength);
	}

	public Integer getStatus() {
		if (this.theoryStatus.equals(MockStatus.NOT_STARTED)
				&& this.operationStatus.equals(MockStatus.NOT_STARTED)) {
			return MockStatus.NOT_STARTED;
		}
		if (this.theoryStatus.equals(MockStatus.ONGOING)
				|| this.operationStatus.equals(MockStatus.ONGOING)) {
			return MockStatus.ONGOING;
		}
		if (this.theoryStatus.equals(MockStatus.PASSED)
				&& this.operationStatus.equals(MockStatus.PASSED)) {
			return MockStatus.PASSED;
		}
		return MockStatus.FAILED;
	}
	
	public boolean isFinished() {
		Integer status = this.getStatus();
		return status.equals(MockStatus.PASSED) || status.equals(MockStatus.FAILED);
	}
	
	public boolean isOngoing() {
		Integer status = this.getStatus();
		return status.equals(MockStatus.ONGOING);
	}
	public boolean isTheoryFinished(){
		return this.theoryStatus.equals(MockStatus.PASSED) || this.theoryStatus.equals(MockStatus.FAILED);
	}
	
	

	// private String isTrue;

}
