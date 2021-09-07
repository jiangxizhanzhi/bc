package com.cyberpeace.common.util;

import java.io.*;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	public static File saveFile(MultipartFile file, String basePath) throws IOException {
		String originalFilename = file.getOriginalFilename();
		assert originalFilename != null;
		String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		String uuid = String.valueOf(UUID.randomUUID());
		String fileName = uuid + fileSuffix;
		File fileExist = new File(basePath);
		if (!fileExist.exists()) {
			fileExist.mkdirs();
		}
		File newFile = new File(basePath, fileName);
		file.transferTo(newFile);
		return newFile;
	}

	public static String textFromFile(String filePath) {
		File file = new File(filePath);

		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File " + filePath + " not found");
		}

		StringBuilder stringBuilder = new StringBuilder();

		String text;
		while (true) {
			try {
				text = input.readLine();
			} catch (IOException e) {
				throw new RuntimeException("IOException while reading " + filePath);
			}

			if (text == null) {
				break;
			} ;

			stringBuilder.append(text).append("/n");
		}

		return stringBuilder.toString();
	}

	public static void textToFile(String path, String text) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			out.write(text);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("Fail to write file " + path);
		}
	}
}
