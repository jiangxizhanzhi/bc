/**
 * 这段代码定义了一个名为 `CourseAddReq` 的 Java 类，用于处理课程新增请求的数据。以下是该类的主要特性和功能： 
 * ### 类定义与注解 
 * - **包声明**：该类位于 `com.cyberpeace.oj.course.controller.req` 包下。 
 * - **导入的类**： 
 * - `Create` 接口（用于验证）。 
 * - `CrCommonValidator` 类（用于通用验证）。 
 * - `OjBusinessErrorCode` 枚举（业务错误码）。 
 * - `ApiModel` 和 `ApiModelProperty` 注解（用于 Swagger 文档生成）。 
 * - `Getter` 和 `Setter` 注解（Lombok 自动生成 getter 和 setter 方法）。 
 * - `Serializable` 接口（表示该类可以序列化）。 
 * - `StringUtils` 类（Apache Commons Lang 中的字符串工具类）。 
 * ### 类的功能 
 * - **属性**： 
 * - `name`：课程名称，必须为非空字符串。 
 * - `description`：课程描述。 
 * - `categoryId`：课程类别ID，必须为非空字符串。 
 * - `logo`：课程图标。 
 * - `outline`：课程大纲。 
 * - `difficulty`：课程难度，必须为非空整数。 
 * - `rank`：课程等级。 
 * - `attackTag`：攻击知识点列表。 
 * - `defenseTag`：防御知识点列表。 
 * - `isPublic`：是否公开，必须为非空布尔值。 
 * - `courseType`：课程类型。 
 * - `lecturer`：讲师。 
 * - `score`：学分。 
 * - `courseNum`：课程编号，必须为非空字符串。 
 * - `role`：岗位。 
 * - **方法**： 
 * - `validate()`：验证方法，检查分数是否超过最大值，并验证讲师名称长度是否在合理范围内。 
 * - `getName()`：获取课程名称，并去除首尾空白字符。 
 * ### 验证 
 * - 使用了 JSR 380 注解（如 `@NotBlank` 和 `@NotNull`）来确保某些字段在创建时是非空的。 
 * - 自定义验证逻辑：通过调用 `CrCommonValidator` 来验证分数的最大值和讲师名称的长度。 
 * ### 总结 
 * 这个类主要用于接收和验证课程新增请求的数据。它定义了课程的基本信息，并提供了验证方法以确保数据的完整性和有效性。使用 Lombok 注解减少了样板代码，使得类更简洁。 
**/
package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 课程新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("课程新增信息")
public class CourseAddReq implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@NotBlank(groups = { Create.class })
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("课程描述")
	private String description;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向")
	@NotBlank(groups = { Create.class })
	private String categoryId;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty("课程封面")
	private String logo;

	/**
	 * 大纲
	 *
	 * @see String
	 */
	@ApiModelProperty("课程大纲")
	private String outline;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@NotNull(groups = { Create.class })
	@ApiModelProperty("难度，type=51")
	private Integer difficulty;

	/**
	 * 等级
	 *
	 * @see Integer
	 */
	@ApiModelProperty("等级,type=9")
	private Integer rank;

	@ApiModelProperty("攻击知识点")
	private List<List<Integer>> attackTag;

	@ApiModelProperty("防御知识点")
	private List<List<Integer>> defenseTag;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	@NotNull(groups = { Create.class })
	private Boolean isPublic;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程分类 0=基础 2=攻击 3=防御")
	private Integer courseType;

	/**
	 * 讲师
	 *
	 * @see String
	 */
	@ApiModelProperty("讲师")
	private String lecturer;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("学分")
	private Integer score;

	/**
	 * 课程数量
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	@NotBlank
	private String courseNum;

	/**
	 * 岗位
	 *
	 * @see Integer
	 */
	@ApiModelProperty("岗位")
	private Integer role;

	public void validate() {
		// 校验分数最大值
		int scoreMax = 1000;
		if (null != score ) {
			CrCommonValidator.validateScore(score, scoreMax, OjBusinessErrorCode.MAX_SCORE_1000);
		}
		// 验证名称长度
		if(null != lecturer && !lecturer.isEmpty()){
			int lenMin = 1;
			int lenMax = 64;
			CrCommonValidator.validateRange(lecturer.length(),lenMin, lenMax, OjBusinessErrorCode.MAX_64_CHARACTERS);
		}
	}

	public String getName() {
		return StringUtils.isNotBlank(name) ? name.trim() : name;
	}
}
