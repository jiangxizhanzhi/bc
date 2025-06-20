package com.cyberpeace.common.model;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回数据模型
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个泛型类 `ListRsp<T>`，用于封装分页返回的数据模型。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于分页查询时返回数据，包含分页信息和数据列表。 
 * 2. **泛型支持**： 
 * - 使用了泛型 `<T>`，使得该类可以适配任意类型的数据。 
 * 3. **注解说明**： 
 * - 使用了 Lombok 的注解： 
 * - `@Getter` 和 `@Setter`：自动生成 getter 和 setter 方法。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：分别生成全参构造函数和无参构造函数。 
 * - `@Builder`：提供建造者模式以方便对象创建。 
 * - 使用了 Swagger 注解 `@ApiModelProperty` 来描述字段含义。 
 * 4. **字段说明**： 
 * - `list`：存储分页数据的列表，默认初始化为一个空列表。 
 * - `total`：总记录数。 
 * - `lastPage`：最后一页的页码。 
 * - `currentPage`：当前页码。 
 * 5. **实现接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * 总结：这是一个通用的分页返回结果类，适用于需要返回分页数据的场景，具有良好的扩展性和可读性。 
**/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListRsp<T> implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 列表
	 */
	@ApiModelProperty(value = "列表")
	private List<T> list = Lists.newArrayList();

	/**
	 * 总
	 */
	@ApiModelProperty(value = "总页数")
	private long total;

	/**
	 * 最后分页
	 */
	@ApiModelProperty(value = "最后页")
	private long lastPage;

	/**
	 * 当前分页
	 */
	@ApiModelProperty(value = "当前页")
	private long currentPage;
}

