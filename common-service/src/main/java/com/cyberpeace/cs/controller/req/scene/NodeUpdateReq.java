package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 节点更新请求体
 *
 * @author SN
 * @date 2023/05/11
 */
/**
 * 这段代码定义了一个名为 `NodeUpdateReq` 的 Java 类，用于表示节点更新请求的数据结构。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个请求体类，主要用于接收节点更新的相关信息。 
 * 2. **继承与注解**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供了描述信息，便于生成 API 文档。 
 * 3. **字段说明**： 
 * - `mpSceneId`：中台场景 ID，用于标识特定的场景。 
 * - `update`：节点更新的内容，类型为 `JSONObject`，表示可以包含任意 JSON 数据。 
 * 4. **作者与日期**： 
 * - 注释中指定了作者为 `SN`，创建日期为 `2023/05/11`。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装节点更新请求所需的信息，支持序列化且具备良好的可读性和文档生成支持。 
**/
@Getter
@Setter
public class NodeUpdateReq implements Serializable {

	/**
	 * 中台场景id
	 */
	@ApiModelProperty(value = "场景id")
	private String mpSceneId;

	/**
	 * 更新
	 */
	@ApiModelProperty(value = "节点更新内容")
	private JSONObject update;
}

