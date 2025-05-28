package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号理论
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexTheory {

	/**
	 * 理论课程
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "理论课程")
	private Integer theoryCourse = 0;

	/**
	 * 在线经验
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "在线试验资源")
	private Integer onlineExp = 0;

	/**
	 * 理论考试
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "理论考题资源")
	private Integer theoryExam = 0;

	/**
	 * EXP 考试
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "试验考题资源")
	private Integer expExam = 0;

	/**
	 * 使用
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "理论知识库使用次数")
	private Integer use = 0;

	/**
	 * 今天添加
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "今日新增理论知识库")
	private Integer addedToday = 0;

	/**
	 * 今天更新
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "今日修改理论知识库")
	private Integer updatedToday = 0;

	/**
	 * 视频计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "课程视频数")
	private Integer videoCount = 0;

	/**
	 * 视频时长
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "课程视频时长")
	private Integer videoDuration = 0;

	/**
	 * 和
	 *
	 * @see Integer
	 */
	private Integer sum;

	/**
	 * Cal Sum
	 *
	 * @throws
	 */
	public void calSum() {
		this.sum = this.theoryCourse + this.onlineExp + this.theoryExam + this.expExam;
	}
}
