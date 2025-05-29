package com.cyberpeace.cr.crbase.controller.req.resource;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.Serializable;
import java.util.Map;

/**
 * 批量加载Markdown请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `BatchLoadMdReq` 的 Java 类，用于处理批量加载 Markdown 文件的请求体。以下是对其功能和结构的简要分析： 
 * ### 主要功能： 
 * 1. **数据结构**：类实现了 `Serializable` 接口，表明它可以被序列化，通常用于网络传输或持久化。 
 * 2. **属性**： 
 * - `envReqs`：一个非空的字符串键值对映射，表示环境参数。 
 * - `path`：一个非空字符串，表示文件路径。 
 * 3. **方法**： 
 * - `validate()`：验证路径是否有效。如果路径不存在或不是一个目录，则抛出业务异常。 
 * ### 特点： 
 * - 使用了 Lombok 的 `@Getter`, `@Setter` 和 `@Slf4j` 注解来简化代码，减少样板代码。 
 * - 引入了 Swagger 注解 `@ApiModelProperty` 来描述字段用途，便于文档生成。 
 * - 异常处理通过自定义的 `BusinessException` 抛出，并使用 `ErrorCode` 定义错误码。 
 * ### 总结： 
 * 该类主要用于接收和验证批量加载 Markdown 文件所需的请求参数，确保路径的有效性，是典型的请求封装类。 
**/
@Getter
@Setter
@Slf4j
public class BatchLoadMdReq implements Serializable {

	/**
	 * env reqs
	 */
	@ApiModelProperty(value = "环境参数")
	@NotEmpty
	private Map<String, String> envReqs;

	/**
	 * 路径
	 */
	@ApiModelProperty(value = "资管文件路径")
	@NotEmpty
	private String path;

	/**
	 * 验证
	 */
	public void validate() {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			log.error(String.format("invalid path [%s]", path));
			throw new BusinessException(ErrorCode.INVALID_FILE_PATH);
		}
	}
}

