package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 评估名称列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalNameListReq` 的 Java 类，该类用于处理评估名称列表请求的数据。以下是对其的简要分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.cr.evaluation.controller.req; 
 * ``` 
 * 这个包声明表明该类位于 `com.cyberpeace.cr.evaluation.controller.req` 包中。 
 * ### 导入语句 
 * ```java 
 * import com.cyberpeace.common.model.PageReqBase; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * ``` 
 * - `PageReqBase` 是一个基础请求类，可能包含了分页相关的属性和方法。 
 * - `@Getter` 和 `@Setter` 是 Lombok 提供的注解，用于自动生成 getter 和 setter 方法。 
 * - `Serializable` 是 Java 中的一个接口，表示该类的对象可以被序列化（例如，用于网络传输或存储）。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * public class EvalNameListReq extends PageReqBase implements Serializable { 
 * } 
 * ``` 
 * - `EvalNameListReq` 类继承了 `PageReqBase` 类，并实现了 `Serializable` 接口。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来简化代码，自动生成 getter 和 setter 方法。 
 * ### 总结 
 * - **用途**：`EvalNameListReq` 类用于处理评估名称列表的请求数据，可能包含分页参数。 
 * - **特性**： 
 * - 继承自 `PageReqBase` 类，因此可能包含了分页相关的属性和方法。 
 * - 实现了 `Serializable` 接口，支持对象的序列化。 
 * - 使用 Lombok 注解减少样板代码，提高可读性。 
 * 这段代码简洁且功能明确，适合用于处理与评估名称列表相关的请求。 
**/
@Getter
@Setter
public class EvalNameListReq extends PageReqBase implements Serializable {



}

