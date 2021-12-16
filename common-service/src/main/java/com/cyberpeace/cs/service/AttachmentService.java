package com.cyberpeace.cs.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.service.context.AttachmentBo;

public interface AttachmentService extends IService<Attachment> {

	/**
	 * 根据附件编号查询附件信息 以,分割
	 * @param attachmentIds
	 * @param isWeb
	 * @return
	 */
	List<AttachmentBo> selectByIds(String attachmentIds, Boolean isWeb);

	/**
	 * 删除修改后
	 * @return
	 */
	Integer deleteDifferenceSet(String oldAttachmentIds, String attachmentIds);

	/**
	 *
	 * @param filename
	 * @param filePath
	 * @return
	 */
	AttachmentBo create(String filename, String filePath);

	/**
	 * 文件上传并入附件库
	 * @param type
	 * @param insert
	 * @param file
	 * @return
	 */
	AttachmentBo uploadFile(Integer type, Boolean insert, MultipartFile file);

	/**
	 * 
	 * @param type 类型 用于获取存放地址
	 * @param insert 文件信息是否入库
	 * @param file 文件
	 * @param resourceId 资源编号 用于文件资源隔离 不需要隔离请使用重载方法
	 * @return
	 */
	AttachmentBo uploadFile(Integer type, Boolean insert, MultipartFile file, String resourceId);

	/**
	 *
	 * @param attachmentId
	 */
	void deleteAttachment(String attachmentId);

	/**
	 * 解析markdown文件 返回文件内容
	 * @param markdownPath
	 * @return
	 */
	String parseMarkdown(String markdownPath);

	/**
	 * 文件解压缩
	 * @param filePath zip文件路径
	 * @param distPath 解压文件夹
	 * @param fileType 文件后缀名 不含. 不传则读取解压后文件列表 包含文件夹
	 * @return
	 */
	List<String> unzipFile(String filePath, String distPath, String fileType);
}
