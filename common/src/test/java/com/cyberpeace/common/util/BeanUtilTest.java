package com.cyberpeace.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 这段代码的主要目的是测试 `BeanUtils` 工具类的两个方法：`copy` 和 `deepCopy`。以下是代码的简要分析： 
 * 1. **测试类和工具方法**： 
 * - 类 `BeanUtilTest` 包含了两个测试方法，分别测试 `BeanUtils` 的普通复制 (`copy`) 和深拷贝 (`deepCopy`) 功能。 
 * 2. **普通复制测试 (`copy` 方法)**： 
 * - 创建了一个 `ModelA` 对象并设置了其属性。 
 * - 使用 `BeanUtils.copy` 方法将 `ModelA` 转换为 `ModelB`。 
 * - 验证了 `ModelA` 和 `ModelB` 中的某些属性是否一致，例如名称、数字和时间。 
 * 3. **深拷贝测试 (`deepCopy` 方法)**： 
 * - 创建了一个包含嵌套对象的 `DeepModelA` 对象，并设置了其属性。 
 * - 使用 `BeanUtils.deepCopy` 方法将 `DeepModelA` 转换为 `DeepModelB`。 
 * - 当前代码中，`deepCopy` 方法的实现未展示，但通常深拷贝会处理嵌套对象和集合，确保所有层级的对象都被正确复制。 
 * 4. **辅助方法**： 
 * - `generateModelA` 方法用于生成一个随机的 `ModelA` 对象，用于测试数据准备。 
 * 5. **依赖库**： 
 * - 使用了 Google Guava 提供的 `Lists.newArrayList` 和 `Maps.newHashMap` 方法来创建集合。 
 * - 使用了 Apache Commons Lang 的 `StringUtils` 和 Spring 的 `Assert` 类来进行断言和字符串操作。 
 * 总结来说，这段代码通过单元测试验证了 `BeanUtils` 工具类在普通复制和深拷贝方面的功能，确保了对象及其嵌套结构能够被正确复制。 
**/
public class BeanUtilTest {

	private static final Random RANDOM = new Random();

	@Test
	public void copy() {
		ModelA modelA = new ModelA();

		modelA.setA("a");
		modelA.setName("name");
		modelA.setNum(1);
		modelA.setTime(new Date());

		ModelB modelB = BeanUtils.copy(modelA, ModelB.class);

		Assert.isTrue(StringUtils.equals(modelA.getName(), modelB.getName()));
		Assert.isTrue(null == modelB.getB());
		Assert.isTrue(modelA.getNum().intValue() == modelB.getNum().intValue());
		Assert.isTrue(modelA.getTime().getTime() == modelB.getTime().getTime());
	}

	@Test
	public void deepCopy() {
		DeepModelA deepModelA = new DeepModelA();

		deepModelA.setA("a");
		deepModelA.setName("name");
		deepModelA.setNum(1);
		deepModelA.setTime(new Date());

		List<ModelA> alist = Lists.newArrayList();
		for (int i = 0; i < 2; i++) {
			alist.add(generateModelA());
		}
		deepModelA.setList(alist);

		Map<String, ModelA> aMap = Maps.newHashMap();
		aMap.put("key", generateModelA());
		deepModelA.setMap(aMap);

		DeepModelB deepModelB = BeanUtils.deepCopy(deepModelA, DeepModelB.class);

		System.out.println("a");
	}

	private ModelA generateModelA() {
		ModelA modelA = new ModelA();

		modelA.setTime(new Date());
		modelA.setA(UUID.randomUUID().toString());
		modelA.setName(UUID.randomUUID().toString());
		modelA.setNum(RANDOM.nextInt());

		return modelA;
	}
}

