package com.cyberpeace.evaluate.service.bo;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.evaluate.service.bo.HistoryDataBO;
import com.cyberpeace.evaluate.service.bo.TestHistoryBO;
import lombok.Data;

@Data
public class TestRecordBO implements Serializable {

	/**测试记录 */
	private List<TestHistoryBO> tests;

	/**测试记录数据 */
	private List<HistoryDataBO> testData;

}
