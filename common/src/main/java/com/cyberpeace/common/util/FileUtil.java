package com.cyberpeace.common.util;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
}
