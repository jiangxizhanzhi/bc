/**
 * 这段代码定义了一个名为 `CommentBo` 的 Java 类，用于表示一个评论对象。以下是该类的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.ojbase.service.bo` 
 * - **实现接口**：`Serializable` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 库提供，自动生成 getter 和 setter 方法。 
 * - `@ApiModelProperty`：由 Swagger 注解库提供，用于生成 API 文档。 
 * ### 属性 
 * - **avatar**：头像字符串，表示用户的头像。 
 * - **resourceId**：资源ID字符串，表示评论所属资源的编号。 
 * - **businessId**：业务ID字符串，表示评论的资源编号（如课程或习题）。 
 * - **themeName**：主题名称字符串，表示评论的主题名称。 
 * - **userId**：用户ID字符串，表示评论者的用户ID。 
 * - **userName**：用户名字符串，表示评论者的用户名。 
 * - **content**：评论内容字符串，表示评论的具体内容。 
 * - **businessType**：业务类型整数，表示评论的类型。 
 * - **createTime**：创建时间日期对象，表示评论的创建时间。 
 * - **rootId**：根ID字符串，表示评论的根编号。 
 * - **replyId**：答ID字符串，表示被回复的评论编号。 
 * - **replyUsername**：回复用户名字符串，表示被回复的人的用户名。 
 * - **replyUserId**：答用户ID字符串，表示被回复的人的用户ID。 
 * - **isLike**：布尔值，表示评论是否被点赞。 
 * - **likesCount**：点赞计数整数，表示评论的点赞数量。 
 * - **replyCount**：回复计数整数，表示评论的回复数量。 
 * - **commentReply**：评论回复列表，包含多个 `CommentBo` 对象，表示被回复的评论。 
 * ### 总结 
 * - **用途**：`CommentBo` 类用于表示一个评论对象，包含了评论的所有相关信息，如用户信息、内容、时间等。 
 * - **特性**：使用了 Lombok 和 Swagger 注解库，简化了代码编写和文档生成。 
 * - **结构**：属性丰富，涵盖了评论相关的所有关键信息，并且可以递归地表示评论的回复结构。 
 * 这个类的设计适用于需要详细记录和展示评论信息的应用场景，特别是在在线教育平台或论坛中。 
**/
package com.cyberpeace.oj.ojbase.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评论 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class CommentBo implements Serializable {

	/**
	 * 化身
	 *
	 * @see String
	 */
	@ApiModelProperty("头像")
	private String avatar;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("评论资源编号")
	private String resourceId;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号 课程、习题")
	private String businessId;

	/**
	 * 主题名称
	 *
	 * @see String
	 */
	@ApiModelProperty("评论主题名称")
	private String themeName;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户id")
	private String userId;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("评论内容")
	private String content;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("评论类型")
	private Integer businessType;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 * 根id
	 *
	 * @see String
	 */
	@ApiModelProperty("评论根编号")
	private String rootId;

	/**
	 * 答id
	 *
	 * @see String
	 */
	@ApiModelProperty("被回复评论编号")
	private String replyId;

	/**
	 * 回复用户名
	 *
	 * @see String
	 */
	@ApiModelProperty("被回复人编号")
	private String replyUsername;

	/**
	 * 答用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("被回复人用户名")
	private String replyUserId;

	/**
	 * 就像
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否被点赞")
	private Boolean isLike;

	/**
	 * 喜欢计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("被点赞数量")
	private Integer likesCount;

	/**
	 * 答计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("被回复的数量")
	private Integer replyCount;

	/**
	 * 评论 回复
	 *
	 * @see List<CommentBo>
	 */
	@ApiModelProperty("被回复的评论")
	private List<CommentBo> commentReply;

}
