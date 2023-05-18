package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.luciad.imageio.webp.WebPReadParam;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ImgUtil {

	//webp格式的图片转成jpg格式的图片
	public static void webpToJpg(String webpPath, String jpgPath) throws IOException {
		if(!jpgPath.endsWith("jpg")){
			log.error("Illegal Picture Suffix! jpgPath = [{}]", jpgPath);
			throw new BusinessException(ErrorCode.ILLEGAL_PICTURE_SUFFIX);
		}

		// Obtain a WebP ImageReader instance
		ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();;
		// Configure decoding parameters
		WebPReadParam readParam = new WebPReadParam();
		readParam.setBypassFiltering(true);
		// Configure the input on the ImageReader
		reader.setInput(new FileImageInputStream(new File(webpPath)));
		// Decode the image
		BufferedImage image = reader.read(0, readParam);
		// the `jpg` can use `png`
		ImageIO.write(image, "jpg", new File(jpgPath));
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

	/**
	 * 将图片展示到图片上，最多展示三行
	 * @param originalPicture 原图片路径
	 * @param font 文字格式
	 * @param text 文字内容
	 * @param destPicture 目标图片路径
	 * @return
	 * @throws IOException
	 */
	public static String addTextToImage(String originalPicture, Font font, String text, String destPicture) throws IOException {
		// Load the image
		BufferedImage img = ImageIO.read(new File(originalPicture));

		// Create a graphics context on the buffered image
		Graphics2D g2d = img.createGraphics();

		// Set the font and color for the text
		Color color = Color.WHITE;
		g2d.setFont(font);
		g2d.setColor(color);

		// Calculate the bounding box for the text
		FontMetrics fm = g2d.getFontMetrics();
		int lineHeight = fm.getHeight();
		int y = ((img.getHeight() - lineHeight) + fm.getAscent()) / 2;

		// Draw the text on the image with automatic line wrapping
		drawString(g2d, text, y);

		// Save the modified image
		ImageIO.write(img, "png", new File(destPicture));
		return destPicture;
	}

	private static void drawString(Graphics2D g2d, String text, int y) {
		FontMetrics fm = g2d.getFontMetrics();
		int lineHeight = fm.getHeight();
		List<String> list = insertNewlines(fm, text, g2d.getDeviceConfiguration()
														.getBounds().width - 150);
		y = y - (list.size() - 2) * lineHeight / 2 - lineHeight / 8;

		for (String line : list) {
			int lineWidth = fm.stringWidth(line);
			int lineX = (g2d.getDeviceConfiguration()
							.getBounds().width - lineWidth) / 2;
			g2d.drawString(line, lineX, y);
			y += lineHeight;
		}
	}

	private static List<String> insertNewlines(FontMetrics fm, String text, int lineLength) {
		List<String> lines = new ArrayList<>();
		StringBuilder currentLine = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			currentLine.append(ch);
			if (fm.stringWidth(currentLine.toString()) >= lineLength) {
				// Add the line to the output and start a new line.
				lines.add(currentLine.toString());
				currentLine.setLength(0);
				if (lines.size() >= 3) {
					break;
				}
			}
		}
		if (currentLine.length() > 0 && lines.size() < 3) {
			lines.add(currentLine.toString());
		}
		return lines;
	}

}
