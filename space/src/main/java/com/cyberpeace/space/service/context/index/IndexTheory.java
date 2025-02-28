package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndexTheory {

	@ApiModelProperty(value = "理论课程")
	private Integer theoryCourse = 0;

	@ApiModelProperty(value = "在线试验资源")
	private Integer onlineExp = 0;

	@ApiModelProperty(value = "理论考题资源")
	private Integer theoryExam = 0;

	@ApiModelProperty(value = "试验考题资源")
	private Integer expExam = 0;

	@ApiModelProperty(value = "理论知识库使用次数")
	private Integer use = 0;

	@ApiModelProperty(value = "今日新增理论知识库")
	private Integer addedToday = 0;

	@ApiModelProperty(value = "今日修改理论知识库")
	private Integer updatedToday = 0;

	private Integer sum;

	public void calSum() {
		this.sum = this.theoryCourse + this.onlineExp + this.theoryExam + this.expExam;
	}
}
