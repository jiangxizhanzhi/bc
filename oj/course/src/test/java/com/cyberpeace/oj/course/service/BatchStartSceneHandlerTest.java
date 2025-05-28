/**
 * 这段代码是一个Junit测试类 `BatchStartSceneHandlerTest`，它包含一个主要的测试方法 `test00` 和一个辅助方法 `filex1`。下面是详细的分析： 
 * ### 类与注解 
 * - **类名**: `BatchStartSceneHandlerTest` 
 * - **注解**: `@ExtendWith(SpringExtension.class)` 用于启用Spring的测试支持。 
 * ### 测试方法 `test00` 
 * - **功能**: 遍历指定目录下的所有Java文件，并生成一系列模拟的测试方法。 
 * - **步骤**: 
 * 1. 获取当前目录 (`D:\ojj\oj\course\src\main\java\com\cyberpeace\oj\course\service\bo`)。 
 * 2. 创建一个结果列表 `result`，并添加一些基础内容（如 `@Test` 和方法签名）。 
 * 3. 遍历当前目录下的所有文件： 
 * - 如果是Java文件，则生成一个打印对象字段的方法调用。 
 * - 如果是目录，则递归调用 `filex1` 方法。 
 * 4. 最后将结果打印出来。 
 * ### 辅助方法 `filex1` 
 * - **功能**: 递归遍历指定目录下的所有文件，并生成模拟的测试方法。 
 * - **步骤**: 
 * 1. 传入当前目录和方法名。 
 * 2. 获取当前目录下的所有文件。 
 * 3. 创建一个结果列表 `result`，并添加一些基础内容（如 `@Test` 和方法签名）。 
 * 4. 遍历当前目录下的所有文件： 
 * - 如果是Java文件，则生成一个打印对象字段的方法调用。 
 * - 如果是目录，则递归调用 `filex1` 方法。 
 * 5. 最后将结果打印出来。 
 * ### 问题与改进点 
 * 1. **硬编码路径**: 当前目录路径 (`D:\ojj\oj\course\src\main\java\com\cyberpeace\oj\course\service\bo`) 是硬编码的，建议使用配置文件或环境变量来管理路径。 
 * 2. **方法签名重复**: `test00` 和 `filex1` 方法中的逻辑基本相同，可以考虑合并成一个方法以减少重复代码。 
 * 3. **输出方式**: 结果直接通过 `System.out.println` 打印，建议使用日志框架（如SLF4J）来记录日志。 
 * 4. **缺少依赖**: 代码中使用了 `createInstance` 和 `printObjectFields` 方法，但这些方法未在代码中定义或导入，需要确保这些方法的存在和正确性。 
 * ### 总结 
 * 这段代码的主要目的是生成一系列模拟的测试方法，但它存在一些可改进的地方。通过重构代码、使用配置文件管理和引入日志框架，可以使代码更加健壮和易于维护。 
**/
package com.cyberpeace.oj.course.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class BatchStartSceneHandlerTest {

	@Test
	public void test00() {
		// 获取当前目录
		File currentDirectory = new File("D:\\ojj\\oj\\course\\src\\main\\java\\com\\cyberpeace\\oj\\course\\service\\bo");
		String methodName = "testa1";

		// 列出当前目录下的所有文件和目录
		File[] filesList = currentDirectory.listFiles();

		List<String> result = new ArrayList<>();
		result.add("@Test");
		result.add("public void ${method}(){".replace("${method}", methodName));
		int i = 100;
		if (filesList != null) {
			for (File file : filesList) {
				if (file.isFile() && file.getName().contains(".java")) { // 检查是否为文件
					result.add("printObjectFields(createInstance({name}.class));".replace("{name}",
							file.getName().replace(".java", "")));
				} else {
					filex1(file, "test" + (i++));
				}
			}
		}
		result.add("}");
		System.out.println();
		if (result.size() > 3) {
			for (String s : result) {
				System.out.println(s);
			}
		}
	}

	public void filex1(File currentDirectory, String methodName) {
		File[] filesList = currentDirectory.listFiles();
		List<String> result = new ArrayList<>();
		result.add("@Test");
		result.add("public void ${method}(){".replace("${method}", methodName));
		if (filesList != null) {
			for (File file : filesList) {
				if (file.isFile() && file.getName().contains(".java")) { // 检查是否为文件
					result.add("printObjectFields(createInstance({name}.class));".replace("{name}",
							file.getName().replace(".java", "")));
				} else {
					filex1(file, methodName + file.getName());
				}
			}
		}
		result.add("}");
		System.out.println();
		if (result.size() > 3) {
			for (String s : result) {
				System.out.println(s);
			}
		}
	}
}
