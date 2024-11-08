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
