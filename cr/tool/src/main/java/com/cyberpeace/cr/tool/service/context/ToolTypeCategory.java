package com.cyberpeace.cr.tool.service.context;

import com.cyberpeace.cr.tool.controller.res.WebToolCategoryRsp;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 工具类型类别
 *
 * @author SN
 * @date 2024/11/13
 */
/**
 * 这段代码定义了一个名为 `ToolTypeCategory` 的类，该类用于表示工具类型类别。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.cr.tool.service.context` 
 * - **类名**：`ToolTypeCategory` 
 * ### 类属性 
 * - **type**：表示工具类型的字符串。 
 * - **typeName**：表示工具类型名称的字符串。 
 * - **categories**：一个包含 `WebToolCategoryRsp` 对象的列表，用于存储工具类别信息。 
 * ### 注解 
 * - **@Getter 和 @Setter**：这两个注解由 Lombok 库提供，自动生成了 `type`、`typeName` 和 `categories` 属性的 getter 和 setter 方法。 
 * ### 类用途 
 * - 这个类的主要目的是封装工具类型及其相关类别信息，便于在系统中管理和传递这些信息。 
 * ### 依赖 
 * - **WebToolCategoryRsp**：这是一个控制器响应类，用于表示工具类别响应信息。 
 * - **List<WebToolCategoryRsp>**：使用 Apache Commons Compress 提供的 `Lists.newArrayList()` 方法初始化列表。 
 * ### 总结 
 * - `ToolTypeCategory` 类是一个数据传输对象（DTO），用于表示工具类型及其相关的类别信息。 
 * - 通过 Lombok 的注解减少了样板代码，提高了代码的可读性和简洁性。 
 * 这个类的设计使得工具类型及其相关类别信息能够方便地进行封装和传递。 
**/
@Getter
@Setter
public class ToolTypeCategory {

    /**
     * 类型
     */
    private String type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类别
     */
    private List<WebToolCategoryRsp> categories = Lists.newArrayList();
}

