package com.cyberpeace.integratation.service.bo.mp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 这段代码定义了一个名为 `MibInfoQueryRequest` 的 Java 类，用于表示查询 MIB（管理信息库）信息时所需的请求参数。这个类使用了 Lombok 库来简化代码，并且通过 Swagger 注解来描述 API 接口。 
 * ### 主要特性： 
 * 1. **注解驱动**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述每个字段在 API 文档中的含义。 
 * 2. **验证约束**： 
 * - 使用了 `javax.validation.constraints.NotEmpty` 和 `javax.validation.constraints.NotNull` 注解来确保某些字段不为空。 
 * - `scene_id`, `element_id`, `oid` 字段必须是非空字符串。 
 * - `version` 字段必须是非空整数。 
 * 3. **字段描述**： 
 * - `scene_id`: 场景 ID，不能为空。 
 * - `element_id`: 节点 ID，不能为空。 
 * - `oid`: 对象标识符（OID），不能为空。 
 * - `version`: SNMP 版本号，可以是 1 (V1), 2 (V2C), 或 3 (V3)。 
 * - `communityName`: 团体字，默认通常是 "public"。 
 * - `username`: 只有在 SNMP 版本为 V3 时才需要。 
 * - `auth_key`: 认证密码，只有在 SNMP 版本为 V3 时才需要。 
 * - `priv_key`: 加密密码，只有在 SNMP 版本为 V3 时才需要。 
 * ### 总结： 
 * - 这个类主要用于构建一个查询 MIB 信息的请求对象。 
 * - 它包含了查询所需的基本信息以及一些与 SNMP 版本相关的高级配置。 
 * - 该类通过注解驱动的方式简化了代码编写，并且通过 Swagger 注解使得这些字段可以在 API 文档中清晰地展示出来。 
 * 这个类的设计合理，能够有效地传递查询 MIB 信息所需的参数，并且具备一定的数据验证功能。 
**/
@Getter
@Setter
public class YXLogRequest implements Serializable {

	private String type;
	private String ip;
	private String time;
	private String content;
	private String action;
	private String result;
	private String username;
	private String roleId;
}

