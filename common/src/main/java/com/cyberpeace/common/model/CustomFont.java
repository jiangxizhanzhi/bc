package com.cyberpeace.common.model;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义字体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个 `CustomFont` 类，用于加载自定义字体。以下是代码的主要功能和结构分析： 
 * 1. **类与方法**： 
 * - 类名为 `CustomFont`，位于包 `com.cyberpeace.common.model` 下。 
 * - 提供了一个公共方法 `loadFonts`，用于加载自定义字体。 
 * 2. **方法逻辑**： 
 * - 方法参数： 
 * - `fontFilename`：字体文件名（位于资源目录下）。 
 * - `style`：字体样式（如 `Font.BOLD` 或 `Font.PLAIN`）。 
 * - `size`：字体大小。 
 * - 使用 `Thread.currentThread().getContextClassLoader().getResourceAsStream(fontFilename)` 加载字体文件流。 
 * - 调用 `Font.createFont(Font.TRUETYPE_FONT, is)` 创建字体对象。 
 * - 如果加载失败（如文件格式错误或 IO 异常），抛出 `RuntimeException`。 
 * - 最后通过 `deriveFont` 方法调整字体样式和大小，并返回新的字体对象。 
 * 3. **特点**： 
 * - 支持 TrueType 格式的字体文件。 
 * - 将字体加载和样式设置分离，便于复用。 
 * - 异常处理简单直接，直接抛出运行时异常。 
 * 总结：该类封装了字体加载的逻辑，便于在项目中使用自定义字体，同时提供了灵活性以支持不同样式和大小的字体。 
**/
public class CustomFont {

	public Font loadFonts(String fontFilename, int style, int size) {
		// 字体在resource下的路径
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fontFilename);
		//返回一个指定字体类型和输入数据的font
		Font actionJson = null;
		try {
			actionJson = Font.createFont(Font.TRUETYPE_FONT,is);
		} catch (FontFormatException | IOException e) {
			throw new RuntimeException(e);
		}
		//通过复制此 Font 对象并应用新样式和大小，创建一个新 Font 对象。
		return actionJson.deriveFont(style,size);

	}
}

