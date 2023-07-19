package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
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
		String header;
		try (InputStream inputStream = new FileInputStream(file)) {
			// 读取文件前几位
			byte[] fileHeader = new byte[4];
			inputStream.read(fileHeader, 0, fileHeader.length);

			// 转为十六进制字符串
			header = ImgUtil.bytes2Hex(fileHeader);
		}
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

		// 文字平滑处理，不是最优方案，只是减小了尖锐度，会增加模糊度！！如果依然不能验收应该需要修改jre配置
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

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

			// 先画阴影
			for(int i = 1; i <= 2; i ++) {
				Color shadowRgba = new Color(0, 0, 0, 0x33);
				g2d.setColor(shadowRgba);
				g2d.drawString(line, lineX, y + i);
			}

			g2d.setColor(Color.WHITE);
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

	private ImgUtil() {}

}
