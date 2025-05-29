package com.cyberpeace.space.service.context;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * id列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `IdListReq` 的类，用于处理包含 ID 列表的请求体。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于接收和验证一个字符串类型的 ID 列表。 
 * - 它实现了基本的 getter 和 setter 方法，并在 `getIds` 方法中增加了业务逻辑以确保 ID 列表不为空。 
 * 2. **注解与框架**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法，简化了代码。 
 * - Swagger 注解 `@ApiModelProperty` 被用来描述 `ids` 字段的含义，方便 API 文档生成。 
 * 3. **方法逻辑**： 
 * - `getIds()` 方法在获取 ID 列表时会检查是否为空。如果为空，则抛出 `BusinessException` 异常并返回特定的错误码 `ErrorCode.BLANK_ID`。 
 * 4. **依赖**： 
 * - 使用了 Apache Commons Collections 的 `CollectionUtils` 工具类来判断集合是否为空。 
 * - 自定义异常 `BusinessException` 和错误码 `ErrorCode` 来处理业务逻辑中的异常情况。 
 * 总结：这是一个简单的数据传输对象（DTO），通过封装和校验功能确保了请求参数的安全性和有效性。 
**/
@Getter
@Setter
public class IdListReq {

	/**
	 * id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("ID列表")
	private List<String> ids;

	/**
	 * 获取id列表
	 *
	 * @return list<字符串>
	 * @throws
	 * @see List<String>
	 */
	public List<String> getIds() {
		if (CollectionUtils.isEmpty(ids)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		return this.ids;
	}
}

