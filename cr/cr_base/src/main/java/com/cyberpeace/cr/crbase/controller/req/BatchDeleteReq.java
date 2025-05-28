/**
 * 这段代码定义了一个名为 `BatchDeleteReq` 的类，用于处理批量删除请求的请求体。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - 该类实现了 `Serializable` 接口，表明它可以被序列化。 
 * - 它的主要功能是接收一个包含资源 ID 列表的请求体，并在必要时验证这些 ID 是否存在。 
 * 2. **字段**： 
 * - `ids`: 一个字符串列表，存储需要删除的资源 ID。 
 * 3. **注解**： 
 * - `@Getter` 和 `@Setter`: 自动生成 getter 和 setter 方法。 
 * - `@Slf4j`: 提供日志记录功能。 
 * - `@ApiModelProperty`: 为 Swagger 文档生成提供说明，这里描述了 `ids` 字段的作用。 
 * 4. **方法**： 
 * - `getIds()`: 重写了默认的 getter 方法，用于在获取 `ids` 时进行空值检查。如果 `ids` 为空或大小为零，则记录错误日志并抛出业务异常 `BusinessException`，使用特定的错误码 `RESOURCE_ID_MISSING`。 
 * 5. **异常处理**： 
 * - 当 `ids` 为空时，会抛出 `BusinessException`，提示资源 ID 缺失。 
 * 6. **日志记录**： 
 * - 使用 SLF4J 进行日志记录，便于调试和监控。 
 * 总体而言，这个类设计简洁，通过注解减少了冗余代码，同时确保了输入数据的有效性。 
**/
package com.cyberpeace.cr.crbase.controller.req;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * 批量删除请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@Slf4j
public class BatchDeleteReq implements Serializable {

	/**
	 * id列表
	 */
	@ApiModelProperty("resource_id列表")
	List<String> ids;

	public List<String> getIds() {
		if (this.ids == null || this.ids.size() == 0) {
			log.error("Resource id missing! templateModifyReq = [{}]", JsonUtil.toJsonString(ids));
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		return this.ids;
	}
}
