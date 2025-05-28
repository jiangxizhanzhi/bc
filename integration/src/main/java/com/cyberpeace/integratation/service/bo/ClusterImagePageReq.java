/**
 * 这段代码定义了一个名为 `ClusterImagePageReq` 的 Java 类，用于处理簇图像分页请求。以下是该类的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.integratation.service.bo` 
 * - **导入**： 
 * - `com.baomidou.mybatisplus.core.toolkit.StringUtils`：用于字符串操作。 
 * - `lombok.Getter` 和 `lombok.Setter`：用于自动生成 getter 和 setter 方法。 
 * ### 类注释 
 * - 注释说明了这个类是用于簇图像分页请求的。 
 * ### 类属性 
 * - **page**：整数类型，表示当前页码。 
 * - **per_page**：整数类型，表示每页显示的记录数。 
 * - **image_type**：字符串类型，表示图像类型。 
 * - **role**：整数类型，表示岗位。 
 * - **main_category**：整数类型，表示主要分类。 
 * - **sub_category**：整数类型，表示子类别。 
 * - **name**：字符串类型，表示名称。 
 * - **vmStarted**：布尔类型，表示 VM 是否已启动。 
 * ### 方法 
 * - **toQuery()**：生成查询字符串的方法。 
 * - 使用 `StringBuilder` 构建查询字符串。 
 * - 根据属性值的不同情况，添加相应的查询参数。 
 * - 返回构建好的查询字符串。 
 * ### 总结 
 * - 这个类主要用于构建一个簇图像分页请求的参数对象，并提供一个方法将这些参数转换为查询字符串。 
 * - 使用 Lombok 库简化了 getter 和 setter 方法的编写。 
 * - 通过 `StringUtils` 提供的工具方法来检查字符串是否为空或空白。 
 * 这个类的设计简洁明了，能够满足基本的分页请求参数封装需求。 
**/
package com.cyberpeace.integratation.service.bo;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 簇图像分页请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ClusterImagePageReq {

	/**
	 * 分页
	 */
	private Integer page;

	/**
	 * 每分页
	 */
	private Integer per_page;

	/**
	 * 图像类型
	 */
	private String image_type;

	/**
	 * 岗位
	 */
	private Integer role;

	/**
	 * 主要分类
	 */
	private Integer main_category;

	/**
	 * 子类别
	 */
	private Integer sub_category;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * VM 已启动
	 */
	private Boolean vmStarted;

	public String toQuery() {
		StringBuilder builder = new StringBuilder();
		builder.append("?per_page=").append(this.per_page);
		builder.append("&page=").append(this.page);
		if (StringUtils.isNotBlank(this.image_type)) {
			builder.append("&image_type=").append(this.image_type);
		}
		if (null != this.role) {
			builder.append("&role=").append(this.role);
		}
		if (null != this.main_category) {
			builder.append("&main_category=").append(this.main_category);
		}
		if (null != this.sub_category) {
			builder.append("&sub_category=").append(this.sub_category);
		}
		if (StringUtils.isNotBlank(this.name)) {
			builder.append("&name=").append(this.name);
		}
		if (Boolean.TRUE.equals(this.vmStarted)) {
			builder.append("&vmStarted=true");
		} else {
			builder.append("&vmStarted=false");
		}
		return builder.toString();
	}
}
