package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ImgUtil {

	//webp格式的图片转成jpg格式的图片
	public static void webpToJpg(String webpPath, String jpgPath) throws IOException {
		if(!jpgPath.endsWith("jpg")){
			throw new BusinessException(ErrorCode.ILLEGAL_PICTURE_SUFFIX);
		}

		BufferedImage webpImage = ImageIO.read(new File(webpPath));
		// 创建一个新的JPG图片文件
		File jpgFile = new File(jpgPath);
		// 将WebP图片写入到JPG文件中
		ImageIO.write(webpImage, "jpg", jpgFile);
	}

	//获取图片的真实格式
	public static String imgType(File file) throws IOException {
		InputStream inputStream = new FileInputStream(file);
		// 读取文件前几位
		byte[] fileHeader = new byte[4];
		int read = inputStream.read(fileHeader, 0, fileHeader.length);
		inputStream.close();

		// 转为十六进制字符串
		String header = ImgUtil.bytes2Hex(fileHeader);

		if (header.contains("FFD8FF")) {
			return "jpg";
		} else if (header.contains("89504E47")) {
			return "png";
		} else if (header.contains("47494638")) {
			return "gif";
		} else if (header.contains("424D")) {
			return "bmp";
		} else if (header.contains("52494646")) {
			return "webp";
		} else if (header.contains("49492A00")) {
			return "tif";
		} else {
			return "unknown";
		}

	}

	public static String bytes2Hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(b & 0xff);
			sb.append(hex.length() == 2 ? hex : ("0"+hex));
		}
		return sb.toString();
	}

}
