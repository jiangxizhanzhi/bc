package com.cyberpeace.situation.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 显示
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class Display {

	@Data
	public class Label {

	/**
	 * 颜色
	 */
		@ApiModelProperty("颜色")
		private String fill;

	/**
	 * 大小（px）
	 */
		@ApiModelProperty("大小（px）")
		private Integer fontSize;

		public Label(String fill, Integer fontSize) {
			this.fill = fill;
			this.fontSize = fontSize;
		}

		public Label() {}

		public void setFill(String fill) {
			this.fill = rgbToHex(fill);
		}

		public void setFontSize(String fontSize) {
			this.fontSize = getSize(fontSize);
		}
	}

	@Data
	public class Body {

	/**
	 * 边框宽度
	 */
		@ApiModelProperty("边框宽度")
		private Integer strokeWidth;

	/**
	 * 边框颜色
	 */
		@ApiModelProperty("边框颜色")
		private String stroke;

	/**
	 * 填充颜色
	 */
		@ApiModelProperty("填充颜色")
		private String fill;

		public Body(Integer strokeWidth, String stroke, String fill) {
			this.strokeWidth = strokeWidth;
			this.stroke = stroke;
			this.fill = fill;
		}

		public Body() {}

		public void setStrokeWidth(String strokeWidth) {
			this.strokeWidth = getSize(strokeWidth);
		}

		public void setStroke(String stroke) {
			this.stroke = rgbToHex(stroke);
		}

		public void setFill(String fill) {
			this.fill = rgbToHex(fill);
		}
	}

	/**
	 * 区域文本属性
	 *
	 * @see Label
	 */
	@ApiModelProperty("区域文本属性")
	private Label label;

	/**
	 * 区域填充图片
	 *
	 * @see String
	 */
	@ApiModelProperty("区域填充图片")
	private String fillPic;

	/**
	 * 区域区块配置
	 *
	 * @see Body
	 */
	@ApiModelProperty("区域区块配置")
	private Body body;

	/**
	 * 节点文本属性
	 *
	 * @see Label
	 */
	@ApiModelProperty("节点文本属性")
	private Label text;

	public Display(Label label, String fillPic, Body body) {
		this.label = label;
		this.fillPic = fillPic;
		this.body = body;
	}

	public Display() {}

	private Integer getSize(String size) {
		if (null == size){
			return null;
		}
		// 使用正则表达式提取数字部分
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(size);
		if (matcher.find()) {
			String extractedNumber = matcher.group();
			// 将提取的数字字符串转换为int
			return Integer.parseInt(extractedNumber);
		} else {
			// 未找到匹配的数字
			return null;
		}
	}

	private String rgbToHex(String rgbaColor) {
		// 使用正则表达式提取红、绿、蓝和透明度值
		String pattern = "rgba\\((\\d+),\\s*(\\d+),\\s*(\\d+),\\s*([\\d.]+)\\)";
		java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
		java.util.regex.Matcher m = r.matcher(rgbaColor);

		if (m.find()) {
			int red = Integer.parseInt(m.group(1));
			int green = Integer.parseInt(m.group(2));
			int blue = Integer.parseInt(m.group(3));
			double alpha = Double.parseDouble(m.group(4));

			// 将RGB值转换为16进制
			String hexColor = String.format("#%02X%02X%02X", red, green, blue);

			// 将透明度值转换为16进制，并追加到RGB颜色代码
			int alphaHex = (int) Math.round(alpha * 255);
			String alphaHexStr = Integer.toHexString(alphaHex);
			if (alphaHexStr.length() == 1) {
				alphaHexStr = "0" + alphaHexStr; // 确保两位数的十六进制表示
			}

			hexColor = hexColor + alphaHexStr.toUpperCase();

			return hexColor;
		} else {
			// 未找到匹配的颜色
			return null;
		}
	}

}
