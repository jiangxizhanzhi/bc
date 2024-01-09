package com.cyberpeace.config;

import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 禁用fastjson循环引用
 * @author SN
 * @date 2024/01/09
 */
@Configuration
public class FastJsonConfig {

	static {
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
	}
}
