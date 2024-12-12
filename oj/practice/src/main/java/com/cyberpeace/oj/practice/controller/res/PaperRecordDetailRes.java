package com.cyberpeace.oj.practice.controller.res;

import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 纸记录详情结果
 *
 * @author SN
 * @see PaperRecordSummaryRes
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/08
 */
@Getter
@Setter
public class PaperRecordDetailRes extends PaperRecordSummaryRes {

	/**
     * 执业名单
     *
     * @see List<WebTheoryBo>
     */
	@ApiModelProperty("题目详情")
	private List<WebTheoryBo> practiceList;

}
