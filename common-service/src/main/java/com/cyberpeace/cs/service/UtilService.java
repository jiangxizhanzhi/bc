package com.cyberpeace.cs.service;

/**
 * 工具服务
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public interface UtilService {

	/**
	 * 处理程序文件url
	 * 后端查询处理url地址
	 *
	 * @param fileUrl 文件url
	 * @return {@link String}
	 */
	String handlerFileUrl(String fileUrl);

	/**
	 * 办公地址
	 * 获取可查看的office文件url地址 比如ppt word
	 *
	 * @param fileUrl 传名称获取路径均可
	 * @param fileId  文件标识
	 * @return {@link String}
	 */
	String officeUrl(String fileUrl, String fileId);

	/**
	 * 替换文件url
	 * 前端参数传递文件路径处理 去除文件前缀
	 *
	 * @param fileUrl 文件url
	 * @return {@link String}
	 */
	String replaceFileUrl(String fileUrl);

	/**
	 * 获得真正路径
	 * 获取真正的文件路径
	 *
	 * @param fileUrl 文件url
	 * @return {@link String}
	 */
	String getRealPath(String fileUrl);

	/**
	 * 处理markdown内容 将里面的文件拷贝到目的地址 同时替换路径为nginx可转发
	 *
	 * @param content      markdown内容 内容中地址为绝对地址 请显示用MdUtil先读取一下
	 * @param destFilePath 目的文件夹地址 nginx可转发文件路径
	 * @return {@link String}
	 */
	String handlerMarkdownFile(String content, String destFilePath);

	/**
	 * 仅处理markdown内容
	 *
	 * @param content 内容
	 * @return {@link String}
	 */
	String replaceMarkdownFile(String content);

}
