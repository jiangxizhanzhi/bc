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
