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

}
