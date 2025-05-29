package com.cyberpeace.integratation.model.scene;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 流操作上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `FlowOpsContext` 的类，该类继承自 `NodeOpsContext`。以下是对其主要特性和功能的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.integratation.model.scene` 
 * - **类名**：`FlowOpsContext` 
 * - **继承关系**：继承自 `NodeOpsContext` 
 * - **注解**：使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，简化了属性的访问器和修改器的生成。 
 * ### 属性 
 * - `flow_id`：流ID，类型为 `String`。 
 * - `source_ip`：源IP地址，类型为 `String`。 
 * ### 方法 
 * - **构造方法**：默认的无参构造方法。 
 * - **Getter 和 Setter 方法**： 
 * - `getFlowId()` 和 `setFlowId(String flowId)`：用于获取和设置 `flow_id`。 
 * - `getSourceIp()` 和 `setSourceIp(String sourceIp)`：用于获取和设置 `source_ip`。其中 `setSourceIp` 方法在设置值之前会检查传入的 IP 地址是否为空或空白，如果为空或空白，则将 `source_ip` 设置为 `null`。 
 * ### 特性 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动为类中的属性生成 getter 和 setter 方法。 
 * - `setSourceIp` 方法中使用了 Apache Commons Lang 的 `StringUtils.isEmpty` 方法来判断传入的 IP 地址是否为空或空白，确保只有非空且非空白的 IP 地址才会被设置。 
 * ### 总结 
 * `FlowOpsContext` 类主要用于存储与流操作相关的上下文信息，包括流ID和源IP地址。通过继承自 `NodeOpsContext` 类，它可能还包含其他与节点操作相关的上下文信息。Lombok 的注解简化了代码量，而对 `source_ip` 的处理则保证了数据的有效性。 
**/
@Getter
@Setter
public class FlowOpsContext extends NodeOpsContext {

    /**
     * 流id
     */
    private String flow_id;

    /**
     * 源ip地址
     */
    private String source_ip;

    public void setFlowId(String flowId) {
        this.flow_id = flowId;
    }

    public String getFlowId() {
        return this.flow_id;
    }

    public String getSourceIp(){
        return this.source_ip;
    }

    public void setSourceIp(String sourceIp) {
        if (StringUtils.isEmpty(sourceIp)) {
            this.source_ip = null;
        } else {
            this.source_ip = sourceIp;
        }
    }

    public FlowOpsContext() {
    }

}

