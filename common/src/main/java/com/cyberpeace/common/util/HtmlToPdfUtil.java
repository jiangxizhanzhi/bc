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

/**
 * 这段代码是一个用于将HTML转换为PDF以及生成HTML模板实例的工具类。以下是代码的主要功能和结构分析： 
 * 1. **类与日志**： 
 * - 类名为`HtmlToPdfUtil`，位于`com.cyberpeace.common.util`包下。 
 * - 使用了`slf4j`库进行日志记录，日志对象`log`用于记录错误信息。 
 * 2. **静态方法`convert`**： 
 * - 功能：将指定的HTML文件路径转换为PDF文件路径。 
 * - 参数： 
 * - `htmlFilePath`: 输入的HTML文件路径。 
 * - `pdfFilePath`: 输出的PDF文件路径。 
 * - 流程： 
 * - 检查并创建目标PDF文件所在的目录。 
 * - 构造命令行指令，使用`wkhtmltopdf`工具进行转换。 
 * - 执行命令，并设置最长执行时间为5分钟。 
 * - 如果转换失败，记录错误日志并返回`false`。 
 * 3. **静态方法`getHtmlTemplateInstance`**： 
 * - 功能：根据模板生成HTML实例文件。 
 * - 重载方法： 
 * - 第一个方法接收对象`obj`和模板枚举类型`HtmlTemplateEnum`，内部调用第二个方法。 
 * - 第二个方法接收`Map<String, Object>`类型的`data`、模板枚举类型`HtmlTemplateEnum`和目标HTML文件路径`templateInstancePath`。 
 * - 流程： 
 * - 使用`freemarker`库加载模板并处理数据。 
 * - 将生成的HTML内容写入目标文件。 
 * - 如果目标文件已存在，抛出异常。 
 * - 处理异常并记录日志。 
 * 4. **私有方法`ensureDir`**： 
 * - 功能：确保目标文件的父目录存在。 
 * - 参数：`file`为目标文件。 
 * - 流程：检查父目录是否存在，如果不存在则创建。 
 * 5. **工具依赖**： 
 * - 使用了`wkhtmltopdf`工具进行HTML到PDF的转换。 
 * - 使用`freemarker`库处理HTML模板。 
 * 6. **异常处理**： 
 * - 在`convert`和`getHtmlTemplateInstance`方法中捕获并记录异常，确保程序不会因异常崩溃。 
 * 总结来说，该类提供了两个主要功能：HTML到PDF的转换和基于模板生成HTML实例。代码结构清晰，功能模块化，便于维护和扩展。 
**/
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
		command.append(" --dpi 300");
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

