package com.cyberpeace.oj.practice.controller.res;

import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PaperRecordDetailRes extends PaperRecordSummaryRes {

	@ApiModelProperty("题目详情")
	private List<WebTheoryBo> practiceList;

}
