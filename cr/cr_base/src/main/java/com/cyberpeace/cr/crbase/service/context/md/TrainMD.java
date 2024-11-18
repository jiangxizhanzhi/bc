package com.cyberpeace.cr.crbase.service.context.md;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.TrainTypeConvert;
import com.cyberpeace.common.mdparser.parser.IntegerParser;
import com.cyberpeace.common.mdparser.parser.ListParser;
import com.cyberpeace.common.mdparser.parser.PictureParser;
import com.cyberpeace.cr.crbase.service.context.convert.BusinessTypeConvert;
import com.cyberpeace.cr.crbase.service.context.convert.DifficultyConvert;
import com.cyberpeace.cr.crbase.service.context.paraser.RoleParser;
import com.cyberpeace.cr.crbase.service.context.paraser.TrainSkillParser;
import com.cyberpeace.cs.service.context.RoleBo;

import lombok.Data;

/**
 * 训练Markdown
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TrainMD implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@MdHeadLine(level = 2, name = "场景名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "场景描述")
	private String description;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	@MdHeadLine(level = 3, name = "场景类型", convert = BusinessTypeConvert.class)
	private Integer businessType;

	/**
	 * 训练类型
	 *
	 * @see Integer
	 */
	@MdHeadLine(level = 3, name = "训练类型", convert = TrainTypeConvert.class)
	private Integer trainType;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@MdHeadLine(level = 3, name = "训练难度", convert = DifficultyConvert.class)
	private Integer difficulty;

	/**
	 * 持续时间
	 *
	 * @see Integer
	 */
	@MdHeadLine(level = 3, name = "建议时间", parser = IntegerParser.class)
	private Integer durationTime;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "LOGO", parser = PictureParser.class)
	private String logo;

	/**
	 * 预科技能
	 *
	 * @see List<String>
	 */
	@MdHeadLine(level = 3, name = "前置技能", parser = ListParser.class)
	private List<String> preSkills;

	/**
	 * 训练技能
	 *
	 * @see List<String>
	 */
	@MdHeadLine(level = 3, name = "训练目标", parser = TrainSkillParser.class)
	private List<String> trainSkills;

	/**
	 * 角色
	 *
	 * @see List<RoleBo>
	 */
	@MdHeadLine(level = 3, name = "场景角色", parser = RoleParser.class)
	private List<RoleBo> roles;

	/**
	 * GIS （英语）
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "GIS坐标")
	private String gis;

	/**
	 * 容易
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "APT组织")
	private String apt;
}