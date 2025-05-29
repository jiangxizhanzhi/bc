package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 用于设置中台拓扑的录屏选项
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `TopoSwitchRecordingContext` 的 Java 类，主要用于设置中台拓扑的录屏选项。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类实现了 `Serializable` 接口，表明它可以被序列化，通常用于在网络中传输或持久化存储。 
 * - 它提供了录屏选项的上下文信息，包括是否启用录制。 
 * 2. **字段**： 
 * - `id`：表示拓扑的唯一标识符，类型为 `String`。 
 * - `enable_recording`：布尔值，表示是否启用录制功能。 
 * 3. **构造方法**： 
 * - 提供了一个构造方法，接受两个参数：`topoId` 和 `enableRecording`，用于初始化对象的 `id` 和 `enable_recording` 字段。 
 * 4. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成了字段的getter和setter方法，简化了代码。 
 * 5. **用途**： 
 * - 该类可以作为参数在服务层或控制器层传递，用于配置录屏功能。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装录屏选项的上下文信息，并支持序列化。 
**/
@Getter
@Setter
public class TopoSwitchRecordingContext implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 启用录制
	 */
	private Boolean enable_recording;

	public TopoSwitchRecordingContext(String topoId, Boolean enableRecording) {
		this.id = topoId;
		this.enable_recording = enableRecording;
	}

}

