package com.cyberpeace.integratation.model.mp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 中台收集事件
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MpCollectionEvent` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示某种“中台收集事件”的数据模型。以下是详细的分析： 
 * ### 包声明和导入 
 * ```java 
 * package com.cyberpeace.integratation.model.mp; 
 * ``` 
 * - 这个类位于 `com.cyberpeace.integratation.model.mp` 包下。 
 * ```java 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * import java.util.Date; 
 * ``` 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * - 引入了 `Serializable` 接口，使得这个类可以序列化。 
 * - 引入了 `Date` 类用于处理日期时间相关的属性。 
 * ### 类定义 
 * ```java 
 * @Getter 
 * @Setter 
 * public class MpCollectionEvent implements Serializable { 
 * ``` 
 * - `MpCollectionEvent` 类使用了 `@Getter` 和 `@Setter` 注解，这意味着所有属性都有相应的 getter 和 setter 方法。 
 * - 类实现了 `Serializable` 接口，表明它可以被序列化。 
 * ### 属性 
 * ```java 
 * private String content; 
 * private Date time; 
 * private String probeData; 
 * private String probeId; 
 * private String nodeId; 
 * private String sceneId; 
 * ``` 
 * - `content`: 一个字符串类型的属性，用于存储内容信息。 
 * - `time`: 一个 `Date` 类型的属性，用于存储时间信息。 
 * - `probeData`: 一个字符串类型的属性，用于存储探测数据。 
 * - `probeId`: 一个字符串类型的属性，用于存储探针ID。 
 * - `nodeId`: 一个字符串类型的属性，用于存储节点ID。 
 * - `sceneId`: 一个字符串类型的属性，用于存储场景ID。 
 * ### 总结 
 * - `MpCollectionEvent` 类是一个简单的数据传输对象（DTO），用于封装与“中台收集事件”相关的各种信息。 
 * - 所有属性都是字符串类型或日期类型，方便进行数据存储和传输。 
 * - 通过 Lombok 提供的注解，减少了样板代码，使类更简洁。 
**/
@Getter
@Setter
public class MpCollectionEvent implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;    /**
     * 内容
     */
    private String content;

    /**
     * 时间
     */
    private Date time;

    /**
     * 探测数据
     */
    private String probeData;

    /**
     * 探针id
     */
    private String probeId;

    /**
     * 节点id
     */
    private String nodeId;

    /**
     * 场景id
     */
    private String sceneId;
}

