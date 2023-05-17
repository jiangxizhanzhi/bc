package com.cyberpeace.cs.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.service.context.AttachmentBo;

/**
 * 附件服务
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public interface AttachmentService extends IService<Attachment> {

	/**
	 * 根据附件编号查询附件信息 以,分割
	 * @param attachmentIds
	 * @param isWeb
	 * @return
	 */
	List<AttachmentBo> selectByIds(String attachmentIds, Boolean isWeb);

	/**
	 * 批量查询
	 * @param attachmentIds
	 * @return
	 */
	List<Attachment> batchGetByIds(List<String> attachmentIds);

	/**
	 * 创建
	 *
	 * @param filename 文件名
	 * @param filePath 文件路径
	 * @return {@link AttachmentBo}
	 */
	AttachmentBo create(String filename, String filePath);

	/**
	 * 根据类型上传到对应的文件夹
	 * @param type
	 * @param insert
	 * @param file
	 * @param renameFile
	 * @return
	 */
	AttachmentBo uploadFile(Integer type, Boolean insert, MultipartFile file, Boolean renameFile);

	/**
	 * 文件上传到目的文件夹 文件中文文件乱码 目前没时间解决 暂时上传的全部文件重命名
	 * @param destDir 目的文件夹
	 * @param insert 是否插入数据库
	 * @param file 上传的源文件
	 * @return
	 */
	AttachmentBo uploadDest(String destDir, boolean insert, MultipartFile file);

	/**
	 * 从临时目录将文件拷贝到实际存储目录
	 * @param originPath 临时目录
	 * @param type 文件存储地址类型，参考AttachmentPath
	 * @param insert true表示记录写入数据库
	 * @param renameFile true表示会对文件重命名
	 * @return
	 */
	AttachmentBo copyFile(String originPath, Integer type, Boolean insert, Boolean renameFile);

	/**
	 * 批量插入
	 * 文件目录插入数据库
	 *
	 * @param attachmentPaths 附件路径
	 * @return {@link List}<{@link String}>
	 */
	List<String> batchInsert(List<String> attachmentPaths);

	/**
	 * 加载图片
	 * 导入场景模板时，将图片存入统一文件夹，插入或更新数据库条目
	 *
	 * @param imageId  形象标识
	 * @param fileName 文件名称
	 * @param imageDir dir形象
	 * @return {@link Boolean}
	 */
	Boolean loadImage(String imageId, String fileName, String imageDir);

	/**
	 * 资管平台导入时，图片附件插入数据库
	 *
	 * @param images 图片
	 */
	void loadZiguanImages(Map<String, String> images);

	/**
	 * 判断文件是否存在
	 *
	 * @param attachmentId 附件标识
	 * @return {@link Boolean}
	 */
	Boolean isExist(String attachmentId);

	/**
	 * 通过附件名称（非原始名称）删除附件
	 *
	 * @param attachmentName 附件名称
	 * @return {@code Boolean}
	 */
	Boolean deleteAttachment(String attachmentName);

}
