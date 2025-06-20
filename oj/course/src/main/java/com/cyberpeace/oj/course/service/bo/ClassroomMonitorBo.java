package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 教室监视器 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomMonitorBo` 的 Java 类，用于表示教室监视器的业务对象（BO, Business Object）。以下是对其主要特性的简要分析： 
 * 1. **包声明**：代码位于 `com.cyberpeace.oj.course.service.bo` 包中，这表明该类是课程服务的一部分。 
 * 2. **导入语句**： 
 * - 导入了 `Serializable` 接口，意味着这个类可以序列化，便于在网络上传输或持久化。 
 * - 导入了 Swagger 相关注解，用于生成 API 文档。 
 * - 导入了 Lombok 注解，简化了代码，如 `@Getter` 和 `@Setter` 自动生成 getter 和 setter 方法。 
 * 3. **类声明**： 
 * - 类名是 `ClassroomMonitorBo`，继承了 `Serializable` 接口。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModel` 注解来描述该类作为一个模型。 
 * 4. **属性**： 
 * - 类中有多个私有属性，每个属性都有相应的 Swagger 注解 `@ApiModelProperty` 来描述其用途。这些属性包括： 
 * - `userId`: 用户ID 
 * - `userName`: 用户名称 
 * - `name`: 姓名 
 * - `organizationId`: 组织ID 
 * - `organizationName`: 组织名称 
 * - `signIn`: 是否已签到 
 * - `signInTime`: 签到时间 
 * - `needHelp`: 是否需要帮助 
 * - `helpUrl`: 远程协助地址 
 * 5. **文档**： 
 * - 类上有 Javadoc 注释，描述了类的作用、作者、版本信息等。 
 * - 每个属性也有对应的 Javadoc 注释，描述了每个属性的作用。 
 * 总体来说，`ClassroomMonitorBo` 类是一个数据传输对象（DTO），用于在系统内部或者与其他系统之间传递关于教室监视器的信息。通过使用 Swagger 和 Lombok 注解，提高了代码的可读性和简洁性。 
**/
@Getter
@Setter
@ApiModel
public class ClassroomMonitorBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("姓名")
	private String name;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty("组织编号")
	private String organizationId;

	/**
	 * 组织名称
	 *
	 * @see String
	 */
	@ApiModelProperty("组织名称")
	private String organizationName;

	/**
	 * 登录
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否已签到")
	private Boolean signIn;

	/**
	 * 签到时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("签到时间")
	private Date signInTime;

	/**
	 * 需要帮助
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否需要帮助")
	private Boolean needHelp;

	/**
	 * 帮助 URL
	 *
	 * @see String
	 */
	@ApiModelProperty("远程协助地址")
	private String helpUrl;
}

