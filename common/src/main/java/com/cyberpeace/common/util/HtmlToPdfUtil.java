package com.cyberpeace.common.util;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlToPdfUtil {

	private static final Logger log = LoggerFactory.getLogger(HtmlToPdfUtil.class);

	private HtmlToPdfUtil() {}

	/**
	 * html转pdf的工具是提前在镜像中装好的
	 */
	private static final String TOOL = "wkhtmltopdf";

	/**
	 *
	 * @param htmlFilePath 待转换的html文件路径
	 * @param pdfFilePath 转换后的pdf文件路径
	 * @return 转换成功返回true
	 */
	public static boolean convert(String htmlFilePath, String pdfFilePath) {
		File file = new File(pdfFilePath);
		ensureDir(file);

		StringBuilder command = new StringBuilder(TOOL);
		command.append(" ");
		command.append(htmlFilePath);
		command.append(" ");
		command.append(pdfFilePath);

		try {
			Process proc = Runtime.getRuntime().exec(command.toString());

			// 最多执行5分钟，不然就算bug
			proc.waitFor(5, TimeUnit.MINUTES);
			return true;
		} catch (Exception e) {
			log.error("[{}] to [{}] convert pdf error", htmlFilePath, pdfFilePath, e);
			return false;
		}
	}

	private static void ensureDir(File file) {
		File dir = file.getParentFile();

		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}
	}
}
