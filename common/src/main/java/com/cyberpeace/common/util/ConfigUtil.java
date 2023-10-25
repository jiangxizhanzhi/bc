package com.cyberpeace.common.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtil extends PropertySourcesPlaceholderConfigurer {

    private static Map<String, String> ctxPropertiesMap = new HashMap<>();

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
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        super.postProcessBeanFactory(beanFactory);
        PropertySources propertySources = super.getAppliedPropertySources();
        for (PropertySource<?> source : propertySources) {
            if (source.getSource() instanceof Properties) {
                Properties props = (Properties) source.getSource();
                for (Object key : props.keySet()) {
                    String keyStr = key.toString();
                    String value = props.getProperty(keyStr);
                    ctxPropertiesMap.put(keyStr, value);
                }
            }
        }
    }
}

