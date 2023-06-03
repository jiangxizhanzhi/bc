package com.cyberpeace.common.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cyberpeace.common.constant.HtmlTemplateEnum;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

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
		command.append(" --enable-local-file-access");
		command.append(" --javascript-delay 2000");
		command.append(" --margin-right  5");
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

	/**
	 * 生成html模板实例
	 * @param obj
	 * @param htmlTemplateEnum
	 * @param templateInstancePath
	 */
	public static void getHtmlTemplateInstance(Object obj, HtmlTemplateEnum htmlTemplateEnum,
			String templateInstancePath) {
		Map<String, Object> obj2map = ObjUtil.obj2map(obj);
		getHtmlTemplateInstance(obj2map, htmlTemplateEnum, templateInstancePath);
	}

	/**
	 * 生成html模板实例
	 * @description 获取模板
	 */
	public static void getHtmlTemplateInstance(Map<String, Object> data,
			HtmlTemplateEnum htmlTemplateEnum, String templateInstancePath) {

		StringWriter writer = null;

		try {
			// FreeMarker配置
			Configuration config = new Configuration(Configuration.VERSION_2_3_28);
			config.setDefaultEncoding("UTF-8");

			// 注意这里是模板所在文件夹，不是文件
			config.setDirectoryForTemplateLoading(new File(htmlTemplateEnum.getTemplatePath()));
			config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			config.setLogTemplateExceptions(false);

			// 注意这里是模板所在文件夹，不是文件
			Template template = config.getTemplate(htmlTemplateEnum.getTemplateName());

			// 模板和数据的匹配
			writer = new StringWriter();
			template.process(data, writer);
			writer.flush();
			String html = writer.toString();

			File destFile = new File(templateInstancePath);
			if (!destFile.getParentFile().exists()) {
				boolean res = destFile.getParentFile().mkdirs();
			}

			if (destFile.exists()) {
				log.error("templateInstancePath exists! templateInstancePath=[{}]",
						templateInstancePath);
				throw new FileAlreadyExistsException(templateInstancePath);
			}

			FileUtil.writeToDestFile(html, templateInstancePath);

		} catch (Exception e) {
			log.error("Get html template instance error template_path=[{}], dest_path=[{}]",
					htmlTemplateEnum.getTemplatePath() + File.separatorChar
							+ htmlTemplateEnum.getTemplateName(),
					e);
			throw new RuntimeException(e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {}
		}

	}

	private static void ensureDir(File file) {
		File dir = file.getParentFile();

		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}
	}
}
