package com.cyberpeace.common.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 这段代码定义了一个工具类 `ConfigUtil`，用于加载和管理配置属性。以下是代码的主要功能和结构分析： 
 * 1. **继承与功能**： 
 * - `ConfigUtil` 继承自 `PropertyPlaceholderConfigurer`，这是一个 Spring 框架中的类，用于解析配置文件中的占位符。 
 * - 它重写了 `processProperties` 方法，用于在 Spring 容器初始化时加载所有属性并存储到静态变量 `ctxPropertiesMap` 中。 
 * 2. **核心功能**： 
 * - **`getContextProperty(String name)`**：根据指定的键获取对应的属性值。 
 * - **`getSubPropertiesMap(String prefix)`**：查找所有以指定前缀开头的属性，并返回这些属性的子集映射。 
 * 3. **实现细节**： 
 * - 使用了 Guava 的 `Maps.newHashMap()` 来创建子属性映射。 
 * - 利用 `StringUtils.startsWith` 方法来匹配属性前缀。 
 * - 静态变量 `ctxPropertiesMap` 用于存储所有的属性键值对。 
 * 4. **优点**： 
 * - 提供了灵活的属性获取方式，支持按前缀筛选。 
 * - 将所有属性集中管理，便于全局访问。 
 * 5. **潜在问题**： 
 * - 静态变量可能导致线程安全问题，特别是在多线程环境中。 
 * - 如果属性较多，可能占用较多内存。 
 * 总结来说，这个工具类通过扩展 Spring 的 `PropertyPlaceholderConfigurer`，实现了自定义的属性管理功能，适合需要频繁访问配置属性的应用场景。 
**/
public class ConfigUtil extends PropertyPlaceholderConfigurer {

    private static Map<String, String> ctxPropertiesMap;

    /**
     * 获取指定属性的属性值
     *
     * @param name
     * @return
     */
    public static String getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }

    /**
     * 查找key以指定前缀（prefix）开头的所有属性
     *
     * @param prefix key的前缀
     * @return 返回符合条件的所有属性，map的key为属性名、value为属性值
     */
    public static Map<String, String> getSubPropertiesMap(String prefix) {
        Map<String, String> subMap = Maps.newHashMap();

        for (Map.Entry<String, String> entry : ctxPropertiesMap.entrySet()) {
            if (StringUtils.startsWith(entry.getKey(), prefix)) {
                subMap.put(entry.getKey(), entry.getValue());
            }
        }

        return subMap;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
                                     Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap<>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

}


