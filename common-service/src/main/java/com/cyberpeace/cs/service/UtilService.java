/**
 * 这段代码定义了一个名为 `UtilService` 的接口，位于 `com.cyberpeace.cs.service` 包下。该接口包含多个方法，用于处理与文件相关的 URL 和内容操作。以下是对其功能的简要分析： 
 * 1. **`handlerFileUrl(String fileUrl)`** 
 * - 功能：后端处理文件 URL 地址。 
 * - 输入：一个文件的 URL 字符串。 
 * - 输出：处理后的 URL 字符串。 
 * 2. **`officeUrl(String fileUrl, String fileId)`** 
 * - 功能：生成可查看 Office 文件（如 PPT、Word）的 URL 地址。 
 * - 输入：文件的 URL 和文件 ID。 
 * - 输出：处理后的 Office 文件 URL。 
 * 3. **`replaceFileUrl(String fileUrl)`** 
 * - 功能：前端传递文件路径时，去除文件路径中的前缀。 
 * - 输入：文件的 URL。 
 * - 输出：去除了前缀的文件路径字符串。 
 * 4. **`getRealPath(String fileUrl)`** 
 * - 功能：获取文件的真实路径。 
 * - 输入：文件的 URL。 
 * - 输出：真实路径字符串。 
 * 5. **`handlerMarkdownFile(String content, String destFilePath)`** 
 * - 功能：处理 Markdown 文件内容，将文件拷贝到目标地址，并替换路径为 Nginx 可转发的路径。 
 * - 输入：Markdown 内容和目标文件夹路径。 
 * - 输出：处理后的 Markdown 内容字符串。 
 * 6. **`replaceMarkdownFile(String content)`** 
 * - 功能：仅处理 Markdown 内容，可能涉及路径的替换或调整。 
 * - 输入：Markdown 内容。 
 * - 输出：处理后的 Markdown 内容字符串。 
 * ### 总结： 
 * 该接口主要服务于文件路径处理和 Markdown 文件的特殊需求，包括 URL 转换、文件路径提取、Markdown 内容处理等。这些方法覆盖了从文件路径处理到内容解析的多种场景，适用于需要对文件路径和内容进行统一管理的应用场景。 
**/
package com.cyberpeace.cs.service;

public interface UtilService {

	/**
	 * 后端查询处理url地址
	 * @param fileUrl
	 * @return
	 */
	String handlerFileUrl(String fileUrl);

	/**
	 * 获取可查看的office文件url地址 比如ppt word
	 * @param fileUrl 传名称获取路径均可
	 * @param fileId   
	 * @return
	 */
	String officeUrl(String fileUrl, String fileId);

	/**
	 * 前端参数传递文件路径处理 去除文件前缀
	 * @param fileUrl
	 * @return
	 */
	String replaceFileUrl(String fileUrl);

	/**
	 * 获取真正的文件路径
	 * @param fileUrl
	 * @return
	 */
	String getRealPath(String fileUrl);

	/**
	 * 处理markdown内容 将里面的文件拷贝到目的地址 同时替换路径为nginx可转发
	 * @param content markdown内容 内容中地址为绝对地址 请显示用MdUtil先读取一下
	 * @param destFilePath 目的文件夹地址 nginx可转发文件路径
	 * @return
	 */
	String handlerMarkdownFile(String content, String destFilePath);

	/**
	 * 仅处理markdown内容
	 * @param content
	 * @return
	 */
	String replaceMarkdownFile(String content);

}
