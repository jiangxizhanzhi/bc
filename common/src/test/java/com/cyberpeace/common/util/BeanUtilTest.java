package com.cyberpeace.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.*;

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
