package com.cyberpeace.oj.practice.controller.res;

import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 纸记录详情结果
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class PaperRecordDetailRes extends PaperRecordSummaryRes {

	/**
	 * 执业名单
	 */
	@ApiModelProperty("题目详情")
	private List<WebTheoryBo> practiceList;

}
