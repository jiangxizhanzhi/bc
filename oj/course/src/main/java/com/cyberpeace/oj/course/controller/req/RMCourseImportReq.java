package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.oj.ojbase.controller.req.RMImportReq;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * RMCOURSE 课程导入请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RMImportReq
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `RMCourseImportReq` 的 Java 类，该类继承自 `RMImportReq`。这个类用于表示一个课程导入请求的参数。以下是对此代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.controller.req` 
 * - **类名**：`RMCourseImportReq` 
 * - **继承**：从 `RMImportReq` 继承而来 
 * ### 注解 
 * - `@Getter` 和 `@Setter`：这两个注解来自 Lombok 库，它们会自动生成属性的 getter 和 setter 方法。 
 * - `@Getter` 和 `@Setter` 使得类中的属性可以被外部访问和修改。 
 * ### 属性 
 * - `private Map<String, String> envReqs;` 
 * - 描述：这是一个映射（Map），键是课时或习题名称，值是 `topoId`。 
 * - 示例：`{"课时1": "topoId1", "习题2": "topoId2"}` 
 * - `private Map<String, Map<String, String>> operationEnvReqs;` 
 * - 描述：这是一个嵌套的映射（Map），外层映射的键是课时名称，值是一个内部映射。 
 * - 内部映射的键是实际操作题的名称，值是 `topoId`。 
 * - 示例： 
 * ```java 
 * { 
 * "课时1": { 
 * "操作题1": "topoId1", 
 * "操作题2": "topoId2" 
 * }, 
 * "课时2": { 
 * "操作题3": "topoId3" 
 * } 
 * } 
 * ``` 
 * ### 文档注释 
 * - 类的文档注释说明了 `RMCourseImportReq` 是一个课程导入请求体，并引用了父类 `RMImportReq`。 
 * - 每个属性都有详细的文档注释，描述了其用途和数据结构。 
 * ### 总结 
 * - `RMCourseImportReq` 类扩展了 `RMImportReq` 类，并添加了两个复杂的属性来表示课程导入请求中的数据结构。 
 * - 这些属性使用了嵌套的 `Map` 结构，以支持灵活的数据组织方式。 
 * - Lombok 的 `@Getter` 和 `@Setter` 注解简化了代码，减少了样板代码的编写。 
**/
@Getter
@Setter
public class RMCourseImportReq extends RMImportReq {

	/**
	 * key=课时或习题名称 value=topoId
	 *
	 * @see Map<String, String>
	 */
	private Map<String, String> envReqs;

	/**
	 * key=课时名称 value=<实操题名称，topoId>
	 *
	 * @see Map<String, Map<String, String>>
	 */
	private Map<String, Map<String, String>> operationEnvReqs;
}

