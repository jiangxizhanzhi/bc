/**
 * 这段代码定义了一个名为 `ClassroomReq` 的 Java 类，用于表示课堂请求体。以下是对其简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.controller.req` 
 * - **实现接口**: `Serializable`（表示该类的对象可以被序列化） 
 * - **注解**: 
 * - `@Getter` 和 `@Setter`: 自动生成属性的 getter 和 setter 方法。 
 * - `@ApiModel`: 表示这是一个 API 模型对象。 
 * ### 属性 
 * - **resourceId**: 课程表编号，字符串类型，必须在更新操作中存在。 
 * - **courseId**: 课程编号，字符串类型，在创建和更新操作中都必须存在，并且不能为空。 
 * - **lessonId**: 课时编号，字符串类型，在创建和更新操作中都必须存在，并且不能为空。 
 * - **organizationId**: 组织编号，字符串类型，在创建和更新操作中都必须存在，并且不能为空。 
 * - **startTime**: 开始时间，日期类型，在创建和更新操作中都必须存在。 
 * - **endTime**: 结束时间，日期类型，在创建和更新操作中都必须存在。 
 * - **trainPlanId**: 训练计划编号，字符串类型，可选。 
 * ### 验证注解 
 * - 使用了 `javax.validation.constraints.NotBlank` 和 `javax.validation.constraints.NotNull` 注解来验证字段是否为空或不为空。 
 * - `@ApiModelProperty` 注解用于描述每个字段的含义。 
 * - `groups` 参数用于区分不同的验证场景（如创建和更新）。 
 * ### 错误信息 
 * - 如果验证失败，会返回相应的错误码 (`OjBusinessErrorCode`)，这些错误码定义在 `com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode` 中。 
 * ### 总结 
 * 这个类主要用于接收课堂相关的请求数据，并进行必要的验证。它确保了在创建和更新课堂信息时，关键字段（如课程编号、课时编号等）是必填项，并提供了详细的错误信息以便客户端处理。 
**/
package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课堂请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class ClassroomReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程表编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	@NotBlank(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.COURSE_INFORMATION_NOT_NULL)
	private String courseId;

	/**
	 * 课id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	@NotBlank(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.LESSON_INFORMATION_NOT_NULL)
	private String lessonId;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty("组织编号")
	@NotBlank(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.ORG_INFORMATION_NOT_NULL)
	private String organizationId;

	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("开始时间")
	@NotNull(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.START_TIME_NOT_NULL)
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("结束时间")
	@NotNull(groups = { Create.class, Update.class },
			message = OjBusinessErrorCode.END_TIME_NOT_NULL)
	private Date endTime;

	/**
	 * 训练计划id
	 *
	 * @see String
	 */
	@ApiModelProperty("训练计划编号")
	private String trainPlanId;

}
