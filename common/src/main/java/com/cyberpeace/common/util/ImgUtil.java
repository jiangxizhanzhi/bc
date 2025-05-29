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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * img util
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码是一个工具类 `ImgUtil`，主要用于处理图像相关的操作。以下是代码的主要功能和结构分析： 
 * ### 主要功能： 
 * 1. **WebP 转 JPG**: 
 * - 方法 `webpToJpg` 将 WebP 格式的图片转换为 JPG 格式的图片。 
 * - 检查输出路径是否以 `.jpg` 结尾，否则抛出非法图片后缀异常。 
 * - 使用 `ImageIO` 和 `WebPReadParam` 解码 WebP 图片并将其写入到指定的 JPG 文件中。 
 * 2. **字节数组转十六进制字符串**: 
 * - 方法 `bytes2Hex` 将字节数组转换为十六进制字符串表示。 
 * 3. **在图片上添加文字**: 
 * - 方法 `addTextToImage` 在原始图片上绘制文字，并保存为目标图片。 
 * - 支持最多三行文字，自动换行以适应图片宽度。 
 * - 文字绘制时带有阴影效果，增强可读性。 
 * 4. **辅助方法**: 
 * - `drawString`：实际绘制文字的方法，支持多行文本和阴影效果。 
 * - `insertNewlines`：根据给定的行长度插入换行符，确保文字不超过最大行数。 
 * ### 关键点总结： 
 * - **依赖库**: 使用了 `javax.imageio`、`java.awt` 和 `lombok` 等库。 
 * - **异常处理**: 对非法文件路径和格式进行了检查，并抛出自定义异常 `BusinessException`。 
 * - **字体与颜色**: 文字绘制支持自定义字体和颜色（默认白色），并且通过设置渲染提示优化文字显示效果。 
 * - **性能与限制**: 文字最多支持三行，超出限制将不绘制多余部分；图片格式固定为 PNG 输出。 
 * ### 适用场景： 
 * 该工具类适用于需要进行 WebP 图片转换、字节数组转十六进制以及在图片上添加文字等操作的场景。 
**/
@Slf4j
public class ImgUtil {

	/**
	 * WebP 转 JPG
	 *
	 * @param webpPath WebP 路径
	 * @param jpgPath  JPG 路径
	 * @throws IOException io异常
	 *///webp格式的图片转成jpg格式的图片
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

	/**
	 * bytes2 十六进制
	 *
	 * @param bytes 字节
	 * @return {@code String }
	 */
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
	 *
	 * @param originalPicture 原图片路径
	 * @param font            文字格式
	 * @param text            文字内容
	 * @param destPicture     目标图片路径
	 * @return
	 * @throws IOException io异常
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

	/**
	 * 绘制字符串
	 *
	 * @param g2d  G2D
	 * @param text 发短信
	 * @param y    y
	 */
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

	/**
	 * 插入换行符
	 *
	 * @param fm         调频
	 * @param text       发短信
	 * @param lineLength 线路长度
	 * @return {@code List<String> }
	 */
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

