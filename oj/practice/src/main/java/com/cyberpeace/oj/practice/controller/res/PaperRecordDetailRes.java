package com.cyberpeace.oj.practice.controller.res;

import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 试卷记录详情结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PaperRecordDetailRes extends PaperRecordSummaryRes {

	/**
	 * 题目详情
	 */
	@ApiModelProperty("题目详情")
	private List<WebTheoryBo> practiceList;

}
