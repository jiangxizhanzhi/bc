package com.cyberpeace.evaluate.service.bo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简化的测试实例模型
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestInstanceSimplfyBO implements Serializable {

	private String instanceId;

	private String name;

	private String desc;
}
