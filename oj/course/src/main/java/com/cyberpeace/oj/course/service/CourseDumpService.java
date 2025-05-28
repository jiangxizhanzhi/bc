/**
 * 这段代码定义了一个名为 `CourseDumpService` 的接口，该接口用于处理课程数据的导出操作。以下是详细的分析： 
 * ### 类名和包名 
 * - **包名**：`com.cyberpeace.oj.course.service` 
 * - 表示这个类属于 `oj.course.service` 包下的 `cyberpeace` 组织。 
 * - **接口名**：`CourseDumpService` 
 * - 这个接口的作用是将课程数据转换并导出为某种格式。 
 * ### 接口方法 
 * - **方法名**：`rMCourseDump` 
 * - 参数： 
 * - `resource_id`: 资源ID，类型为 `String`。 
 * - `is_dump_topo`: 是否下载拓扑数据，类型为 `Boolean`。 
 * - `sceneNumber`: 场景数，类型为 `String`。 
 * - `disableImageExport`: 是否禁用图像导出，类型为 `Boolean`。 
 * - 返回值： 
 * - 方法返回一个 `String` 类型的结果，可能是文件路径或其他标识符。 
 * - 功能描述： 
 * - 该方法用于根据提供的参数导出课程数据。它允许用户选择是否导出拓扑数据、场景数以及是否禁用图像导出。 
 * ### 注释 
 * - 接口上有详细的注释，说明了接口的功能和作者信息。 
 * - 方法也有注释，详细描述了每个参数的含义和返回值的类型。 
 * ### 总结 
 * - `CourseDumpService` 是一个用于处理课程数据导出的接口。 
 * - 它包含一个方法 `rMCourseDump`，该方法允许用户通过指定参数来控制导出的具体内容（如是否包含拓扑数据、场景数和图像）。 
 * - 该接口的设计目的是为了提供灵活的数据导出功能，适用于需要根据不同需求导出课程数据的场景。 
 * 这段代码是一个典型的业务逻辑接口，主要用于处理课程数据的导出任务。 
**/
package com.cyberpeace.oj.course.service;


/**
 * 课程转 储Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface CourseDumpService {

	/**
	 * 课程数据资管格式导出
	 *
	 * @param resource_id        资源id
	 * @param is_dump_topo       是否下载topo数据
	 * @param sceneNumber        场景数
	 * @param disableImageExport 禁用图像导出
	 * @return file
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see String
	 * @see Boolean
	 * @see String
	 */
	String rMCourseDump(String resource_id, Boolean is_dump_topo, String sceneNumber, Boolean disableImageExport);

}
