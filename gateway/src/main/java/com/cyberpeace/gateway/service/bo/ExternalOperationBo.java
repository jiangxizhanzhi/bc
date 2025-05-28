/**
 * 这段代码定义了一个名为 `ExternalOperationBo` 的 Java 类，用于表示外部实践操作的相关信息。以下是该类的简要分析： 
 * ### 包和导入 
 * - **包声明**：`com.cyberpeace.gateway.service.bo` 
 * - **导入**： 
 * - `com.cyberpeace.oj.practice.service.bo.PracticeOperationFileBo`：用于处理附件文件的信息。 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：用于生成 API 文档注解。 
 * - `lombok.Getter` 和 `lombok.Setter`：用于自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`：表示该类可以序列化。 
 * - `java.util.Date` 和 `java.util.List`：用于处理日期和列表数据。 
 * ### 类声明 
 * - **类名**：`ExternalOperationBo` 
 * - **注解**： 
 * - `@ApiModel`：表示这是一个模型对象，通常用于 Swagger API 文档生成。 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * - `implements Serializable`：表示该类支持序列化。 
 * ### 成员变量 
 * - **基本信息**： 
 * - `resourceId`：习题编号。 
 * - `practiceSetId`：习题集编号。 
 * - `categoryId`：分类编号。 
 * - `categoryInfo`：分类信息。 
 * - `title`：标题。 
 * - `answer`：答案（以 JSON 字符串形式存储）。 
 * - `difficulty`：难度级别（0-初级，1-中级，2-高级）。 
 * - `knowledge`：知识点（以 JSON 字符串形式存储）。 
 * - `score`：分值。 
 * - `createUser`：创建人。 
 * - `content`：内容。 
 * - `fileUrl`：附件地址。 
 * - `fileName`：附件名称。 
 * - `isPublic`：是否公开。 
 * - `writeup`：题解。 
 * - `writeupPublic`：题解是否公开。 
 * - `isDynamicEnv`：是否动态答题环境。 
 * - `isDynamicFlag`：是否动态 FLAG。 
 * - `shareMode`：共享模式（1-个人，2-共享）。 
 * - `destroyDelay`：销毁时间（单位: h）。 
 * - `topoId`：场景拓扑 ID。 
 * - **时间信息**： 
 * - `createTime`：创建时间。 
 * - `updateTime`：更新时间。 
 * - **附件信息**： 
 * - `attachment`：附件列表，类型为 `List<PracticeOperationFileBo>`。 
 * ### 总结 
 * 这个类主要用于表示和管理外部实践操作的相关信息，包括习题的基本属性、答案、知识点、附件等，并且支持序列化。通过 Lombok 注解，自动提供了 getter 和 setter 方法，简化了代码编写。同时，使用 Swagger 注解来帮助生成 API 文档。 
**/
package com.cyberpeace.gateway.service.bo;

import com.cyberpeace.oj.practice.service.bo.PracticeOperationFileBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 外部实操博
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class ExternalOperationBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("习题编号")
	private String resourceId;

	/**
	 * 实践设置id
	 */
	@ApiModelProperty("习题集编号")
	private String practiceSetId;

	/**
	 * 类别id
	 */
	@ApiModelProperty("分类编号")
	private String categoryId;

	/**
	 * 类别信息
	 */
	@ApiModelProperty("分类信息")
	private String categoryInfo;

	/**
	 * 标题
	 */
	@ApiModelProperty("标题")
	private String title;

	/**
	 * 答案
	 */
	@ApiModelProperty("答案|FLAG(json string)")
	private String answer;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度(0-初级，1-中级，2-高级)")
	private Integer difficulty;

	/**
	 * 知识
	 */
	@ApiModelProperty("知识点(json string)")
	private String knowledge;

	/**
	 * 得分
	 */
	@ApiModelProperty("分值")
	private Double score;

	/**
	 * 创建用户
	 */
	@ApiModelProperty("创建人")
	private String createUser;

	/**
	 * 内容
	 */
	@ApiModelProperty("内容")
	private String content;

	/**
	 * 文件 URL
	 */
	@ApiModelProperty("附件地址")
	private String fileUrl;

	/**
	 * 文件名称
	 */
	@ApiModelProperty("附件名称")
	private String fileName;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 撰写
	 */
	@ApiModelProperty("题解")
	private String writeup;

	/**
	 * 公开报道
	 */
	@ApiModelProperty("题解是否公开")
	private Boolean writeupPublic;

	/**
	 * 是动态环境
	 */
	@ApiModelProperty("是否动态答题环境")
	private Boolean isDynamicEnv;

	/**
	 * 是动态标志
	 */
	@ApiModelProperty("是否动态FLAG")
	private Integer isDynamicFlag;

	/**
	 * 共享模式
	 */
	@ApiModelProperty("共享模式(1-个人，2-共享)")
	private Integer shareMode;

	/**
	 * 销毁延迟
	 */
	@ApiModelProperty("销毁时间(单位:h)")
	private Integer destroyDelay;

	/**
	 * 拓扑id
	 */
	@ApiModelProperty("场景拓扑ID")
	private String topoId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;

	/**
	 * 附件
	 */
	@ApiModelProperty("随机flag文件")
	private List<PracticeOperationFileBo> attachment;
}
