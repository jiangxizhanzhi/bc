package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 图像上传响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ImageUploadRsp` 的 Java 类，用于表示图像上传的响应。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **类名**：`ImageUploadRsp` 
 * - **包名**：`com.cyberpeace.integratation.model.evaluation` 
 * - **实现接口**：`Serializable`，表明该类的对象可以被序列化。 
 * ### 注解 
 * - `@Getter` 和 `@Setter`：来自 Lombok 库，用于自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **url**：类型为 `String`，表示图像上传后的网址。 
 * ### 作者信息 
 * - **作者**：`SN` 
 * - **日期**：`2024/11/08` 
 * ### 总结 
 * 这个类非常简单，主要用于封装图像上传操作后的响应信息，其中包含一个 `url` 属性来存储图像的访问链接。通过使用 Lombok 的注解，避免了手动编写 getter 和 setter 方法，简化了代码。 
**/
@Getter
@Setter
public class ImageUploadRsp implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 网址
	 */
	private String url;
}

