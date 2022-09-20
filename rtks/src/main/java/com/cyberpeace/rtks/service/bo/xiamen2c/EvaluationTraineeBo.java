package com.cyberpeace.rtks.service.bo.xiamen2c;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 人员评估-参训人员信息
 */
@Data
public class EvaluationTraineeBo implements Serializable {

	@ApiModelProperty(value = "参训人id")
	private String userId;

	@ApiModelProperty(value = "姓名")
	private String userName;

	@ApiModelProperty(value = "岗位id")
	private String roleId;

	@ApiModelProperty(value = "岗位名称")
	private String roleName;

	@ApiModelProperty(value = "技能总得分")
	private Integer score = 0;

	@ApiModelProperty(value = "已通过技能数量")
	private Integer qualifiedCount;

	@ApiModelProperty(value = "已通过技能")
	private List<SkillScore> qualifiedSkills = Lists.newArrayList();

	@ApiModelProperty(value = "未通过技能数量")
	private Integer unqualifiedCount;

	@ApiModelProperty(value = "未通过技能")
	private List<SkillScore> unqualifiedSkills = Lists.newArrayList();

	public void addQualified(String content, Integer score) {
		this.qualifiedSkills.add(new SkillScore(content, score));
	}

	public void addUnqualified(String content, Integer score) {
		this.unqualifiedSkills.add(new SkillScore(content, score));
	}

	public void count() {
		this.qualifiedCount = this.qualifiedSkills.size();
		this.unqualifiedCount = this.unqualifiedSkills.size();
		this.score += this.qualifiedSkills.stream().mapToInt(SkillScore::getScore).sum();
		this.score += this.unqualifiedSkills.stream().mapToInt(SkillScore::getScore).sum();
	}
}

@Data
class SkillScore {

	@ApiModelProperty(value = "技能名称")
	private String content;

	@ApiModelProperty(value = "技能得分")
	private Integer score;

	public SkillScore(String content, Integer score) {
		this.content = content;
		this.score = score;
	}
}