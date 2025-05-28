/**
 * 这段代码定义了一个名为 `LessonDumpLoadBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类用于表示课程相关的数据传输对象（DTO）。以下是对该类的简要分析： 
 * ### 类名及包结构 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **类名**：`LessonDumpLoadBo` 
 * ### 类注释 
 * - 该类的目的是为了进行课程的序列化和反序列化操作。 
 * - 作者是 `SN`，版本号为 `1.0.0`，创建日期为 `2024/11/08`。 
 * ### 属性 
 * 该类包含多个属性，这些属性用于存储课程的相关信息。每个属性都有对应的注释，说明其用途和类型： 
 * - `name`: 课程名称 (`String`) 
 * - `type`: 课程类型 (`Integer`) 
 * - `handoutType`: 讲义类型 (`Integer`) 
 * - `handoutUrl`: 讲义URL (`String`) 
 * - `videoUrl`: 视频URL (`String`) 
 * - `difficulty`: 难度 (`Integer`) 
 * - `isPublic`: 是否公开 (`Boolean`) 
 * - `filePathList`: 文件路径列表 (`List<String>`) 
 * - `compulsory`: 是否必修 (`Integer`) 
 * - `destroyDelay`: 销毁延迟 (`Integer`) 
 * - `duration`: 期间 (`Integer`) 
 * - `topoConfig`: 拓扑配置 (`JSONObject`) 
 * - `knowledge`: 知识 (`String`) 
 * - `sceneShareMode`: 场景共享模式 (`Integer`) 
 * - `originMarkdownFile`: 原始Markdown文件 (`String`) 
 * - `createUserId`: 创建用户的ID (`String`) 
 * - `paperPublic`: 论文是否公开 (`Boolean`) 
 * - `videoDuration`: 视频时长 (`Integer`) 
 * - `theoryPractice`: 理论实践 (`List<PracticeTheoryDumpLoadBo>`) 
 * - `operationPractice`: 实操实践 (`List<PracticeOperationDumpLoadBo>`) 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 实现了 `Serializable` 接口，使得该类的对象可以被序列化和反序列化。 
 * ### 总结 
 * `LessonDumpLoadBo` 类是一个用于课程数据传输的对象，包含了课程的各种详细信息。它通过实现 `Serializable` 接口支持序列化操作，并且通过 Lombok 提供了自动化的 getter 和 setter 方法。 
**/
package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.practice.service.bo.PracticeOperationDumpLoadBo;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryDumpLoadBo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 课程 dump load bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class LessonDumpLoadBo implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;

	/**
	 * 讲义类型
	 *
	 * @see Integer
	 */
	private Integer handoutType;

	/**
	 * 讲义 URL
	 *
	 * @see String
	 */
	private String handoutUrl;

	/**
	 * 视频 URL
	 *
	 * @see String
	 */
	private String videoUrl;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	private Boolean isPublic;

	/**
	 * 文件路径列表
	 *
	 * @see List<String>
	 */
	private List<String> filePathList;

	/**
	 * 必修
	 *
	 * @see Integer
	 */
	private Integer compulsory;

	/**
	 * 销毁延迟
	 *
	 * @see Integer
	 */
	private Integer destroyDelay;

	/**
	 * 期间
	 *
	 * @see Integer
	 */
	private Integer duration;

	/**
	 * 拓扑配置
	 *
	 * @see JSONObject
	 */
	private JSONObject topoConfig;

	/**
	 * 知识
	 *
	 * @see String
	 */
	private String knowledge;

	/**
	 * 场景共享模式
	 *
	 * @see Integer
	 */
	private Integer sceneShareMode;

	/**
	 * origin markdown 文件
	 *
	 * @see String
	 */
	private String originMarkdownFile;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 论文 公共
	 *
	 * @see Boolean
	 */
	private Boolean paperPublic;

	private Integer videoDuration;

	/**
	 * 理论实践
	 *
	 * @see List<PracticeTheoryDumpLoadBo>
	 */
	private List<PracticeTheoryDumpLoadBo> theoryPractice;

	/**
	 * 实操实践
	 *
	 * @see List<PracticeOperationDumpLoadBo>
	 */
	private List<PracticeOperationDumpLoadBo> operationPractice;
}
