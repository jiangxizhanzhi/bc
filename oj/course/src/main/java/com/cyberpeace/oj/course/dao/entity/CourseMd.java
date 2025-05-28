/**
 * 这段代码定义了一个名为 `CourseMd` 的 Java 类，用于表示课程的元数据。以下是对其主要特性和功能的简要分析： 
 * ### 包与导入 
 * - **包声明**：`com.cyberpeace.oj.course.dao.entity` 
 * - **导入的类和注解**： 
 * - `com.cyberpeace.common.mdparser.annotations.MdHeadLine`: 用以标记类字段对应于 Markdown 文件中的特定部分。 
 * - `com.cyberpeace.common.mdparser.convert.DifficultyConvert`: 用于将字符串转换为难度等级。 
 * - `com.cyberpeace.common.mdparser.parser.ListParser`: 用于解析列表类型的字段。 
 * - `lombok.Getter` 和 `lombok.Setter`: 自动生成 getter 和 setter 方法。 
 * ### 类定义 
 * - **类名**：`CourseMd` 
 * - **修饰符**：`public` 
 * - **注解**：使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * - **描述**：这是一个课程的 Markdown 元数据实体类。 
 * ### 属性 
 * - **name** (`String`): 课程名称。 
 * - **categoryId** (`String`): 方向ID。 
 * - **courseNum** (`String`): 课程编号。 
 * - **createUserId** (`String`): 创建者ID。 
 * - **label** (`List<String>`): 课程标签。 
 * - **logo** (`String`): 课程图标链接。 
 * - **difficulty** (`Integer`): 课程难度。 
 * - **description** (`String`): 课程简介。 
 * ### 特殊方法 
 * - **setLogo** 方法: 
 * - 参数：`String logo` 
 * - 功能：修改传入的 `logo` 字符串格式，并将其转换为 Markdown 格式的图片链接。如果传入的 `logo` 不为空，则提取其扩展名并添加到链接中，最后将其设置为 Markdown 格式（例如：`![](logo.png)`）。 
 * ### 总结 
 * - 这个类主要用于从 Markdown 文件中读取和写入课程的元数据信息。 
 * - 使用了 Lombok 库来简化代码，避免手动编写 getter 和 setter 方法。 
 * - 通过 `@MdHeadLine` 注解，可以方便地将类的属性映射到 Markdown 文件中的相应部分。 
 * - `setLogo` 方法提供了一种自定义处理 logo 字符串的方式，使其符合 Markdown 图片链接的格式。 
**/
package com.cyberpeace.oj.course.dao.entity;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import com.cyberpeace.common.mdparser.parser.ListParser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 课程 MD
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Getter
@Setter
public class CourseMd {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@MdHeadLine(level = 2, name = "课程名称")
	private String name;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "方向")
	private String categoryId;

	/**
	 * 课程数量
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "课程编号")
	private String courseNum;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "作者")
	private String createUserId;

	/**
	 * 标签
	 *
	 * @see List<String>
	 */
	@MdHeadLine(level = 3, name = "标签", parser = ListParser.class)
	private List<String> label;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "LOGO")
	private String logo;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@MdHeadLine(level = 3, name = "课程难度", convert = DifficultyConvert.class)
	private Integer difficulty;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "简介")
	private String description;

	public void setLogo(String logo) {
		if(null != logo){
			String[] split = logo.split("\\.");
			if(split.length > 0){
				logo = "logo." + split[split.length - 1];
			}

		}
		this.logo = "![](" + logo + ")";
	}
}
