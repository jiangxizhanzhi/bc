package com.cyberpeace.cs.service;

public interface UtilService {

	/**
	 * 后端查询处理url地址
	 * @param fileUrl
	 * @return
	 */
	String handlerFileUrl(String fileUrl);

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
