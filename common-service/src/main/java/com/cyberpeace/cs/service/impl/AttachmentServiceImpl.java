package com.cyberpeace.cs.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.dao.mapper.AttachmentMapper;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import lombok.extern.slf4j.Slf4j;

/**
 * 附件Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
@Slf4j
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

	/**
	 * 附件Mapper
	 */
	@Autowired
	private AttachmentMapper attachmentMapper;

	/**
	 * utilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 查询由id列表
	 *
	 * @param attachmentIds 附件id列表
	 * @param isWeb         是 Web
	 * @return {@code List<AttachmentBo> }
	 */
	@Override
	public List<AttachmentBo> selectByIds(String attachmentIds, Boolean isWeb) {
		if (StringUtils.isNotBlank(attachmentIds)) {
			String[] attachmentArr = StringUtils.split(attachmentIds, CommonConstant.CODE_COMMA);

			List<Attachment> attachments = attachmentMapper
					.selectBatchIds(Lists.newArrayList(attachmentArr), isWeb);
			List<AttachmentBo> attachmentBos = StreamApiUtils.map(attachments, attachment -> {
				AttachmentBo attachmentBo = BeanUtils.copy(attachment, AttachmentBo.class);
				attachmentBo.setFilePath(utilService.handlerFileUrl(attachment.getFilePath()));
				return attachmentBo;
			});
			return attachmentBos;
		}
		return Lists.newArrayList();
	}

	/**
	 * 批量获取由id列表
	 *
	 * @param attachmentIds 附件id列表
	 * @return {@code List<Attachment> }
	 */
	@Override
	public List<Attachment> batchGetByIds(List<String> attachmentIds) {
		if (attachmentIds == null || attachmentIds.size() == 0) {
			return Lists.newArrayList();
		}
		// 不根据isPublic做筛选
		return attachmentMapper.selectBatchIds(attachmentIds, false);
	}

	/**
	 * 创造
	 *
	 * @param filename 文件名
	 * @param filePath 文件路径
	 * @return {@code AttachmentBo }
	 */
	@Override
	public AttachmentBo create(String filename, String filePath) {
		Attachment attachment = Attachment.builder().resourceId(IDGeneratorUtil.getID())
				.fileName(filename).filePath(filePath).isPublic(CommonConstant.TRUE).build();
		int insert = attachmentMapper.insert(attachment);
		if (insert > 0) {
			AttachmentBo attachmentBo = BeanUtils.copy(attachment, AttachmentBo.class);
			attachmentBo.setFilePath(utilService.handlerFileUrl(attachment.getFilePath()));
			return attachmentBo;
		}
		return null;
	}

	/**
	 * 复制文件
	 *
	 * @param originPath 源路径
	 * @param type       类型
	 * @param insertDb   插入 DB
	 * @param renameFile 重命名文件
	 * @return {@code AttachmentBo }
	 */
	@Override
	public AttachmentBo copyFile(String originPath, Integer type, Boolean insertDb,
			Boolean renameFile) {
		File origin = new File(originPath);
		if (!origin.exists()) {
			throw new BusinessException(ErrorCode.FILE_NOT_EXIST, originPath);
		}
		if (origin.isDirectory()) {
			throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
		}

		String destPath = EnumUtils.getEnumDescByCode(type, AttachmentPath.class);

		String fileName = origin.getName();
		if (renameFile) {
			String[] ss = StringUtils.split(fileName, ".");
			if (ss.length > 1) {
				// 如果文件没有后缀，不重命名，方便后期问题追踪。没有后缀对文件，一般是bug
				String postfix = ss[ss.length - 1];
				fileName = IDGeneratorUtil.getID() + "." + postfix;
			}
		}
		String destPathFileName = destPath + fileName;
		String storeInDbName = EnumUtils.getEnumByCode(type, AttachmentPath.class).getOriginDesc()
				+ fileName;
		File dest = new File(destPathFileName);

		try {
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			Files.copy(origin, dest);
		} catch (IOException e) {
			// 复制文件失败
			log.error("copy file error: " + origin.getAbsolutePath() + " ,to:"
					+ dest.getAbsolutePath());
			throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);
		}

		if (null != insertDb && insertDb) {
			return create(origin.getName(), storeInDbName);
		}

		// 返回给前端对展示名称
		String displayName = ConfigUtil.getContextProperty("cr.local.file.prefix") + storeInDbName;
		return AttachmentBo.builder().filePath(displayName).fileName(origin.getName()).build();
	}

	/**
	 * 上传文件
	 *
	 * @param type       类型
	 * @param insert     插入
	 * @param file       文件
	 * @param renameFile 重命名文件
	 * @return {@code AttachmentBo }
	 */
	@Override
	public AttachmentBo uploadFile(Integer type, Boolean insert, MultipartFile file,
			Boolean renameFile) {
		return uploadFile(file, type, insert, renameFile);
	}

	/**
	 * 上传 DEST
	 *
	 * @param destDir dest dir
	 * @param insert  插入
	 * @param file    文件
	 * @return {@code AttachmentBo }
	 */
	@Override
	public AttachmentBo uploadDest(String destDir, boolean insert, MultipartFile file) {

		FileUtil.mkdirs(destDir);
		String reName = reFileName(file.getOriginalFilename());
		//获取文件名称
		String fileSuffix = FileUtil.getFileSuffix(reName);
		String lowerCaseSuffix = fileSuffix.toLowerCase();
		//转换文件后缀名
		reName = reName.replace(fileSuffix,lowerCaseSuffix);
		String destFileName = destDir + reName;

		try {
			file.transferTo(new File(destFileName));
		} catch (Exception e) {
			log.error("file upload failed! dest_file_name=[{}], origin_file_name=[{}]",
					destFileName, file.getOriginalFilename(), e);
			throw new BusinessException(ErrorCode.FILE_ERROR_FAIL, file.getOriginalFilename());
		}

		String viewUrl = utilService.replaceFileUrl(destFileName);
		Attachment attachment = Attachment.builder().fileName(file.getOriginalFilename())
				.filePath(viewUrl).isPublic(true).build();
		// 插入数据库
		if (insert) {
			attachment.setResourceId(IDGeneratorUtil.getID());
			attachmentMapper.insert(attachment);
			return BeanUtils.copy(attachment, AttachmentBo.class);
		}

		return BeanUtils.copy(attachment, AttachmentBo.class);
	}

	/**
	 * 修改文件名称
	 *
	 * @param fileName
	 * @return
	 */
	public String reFileName(String fileName) {
		String afterFix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return IDGeneratorUtil.getID() + CommonConstant.PERIOD + afterFix;
	}

	/**
	 * 批量插入
	 *
	 * @param attachmentPaths 连接路径
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> batchInsert(List<String> attachmentPaths) {

		List<Attachment> attachments = StreamApiUtils.map(attachmentPaths,
				path -> Attachment.builder().resourceId(IDGeneratorUtil.getID())
						.fileName(FileUtil.getFileName(path, true)).filePath(path)
						.isPublic(CommonConstant.TRUE).build());

		saveBatch(attachments);

		return StreamApiUtils.map(attachments, Attachment::getResourceId);
	}

	/**
	 * 加载图像
	 *
	 * @param imageId  图像id
	 * @param fileName 文件名称
	 * @param imageDir 图像目录
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean loadImage(String imageId, String fileName, String imageDir) {
		// 图片附件已存在，不做操作
		if (attachmentMapper.selectById(imageId) != null) {
			return true;
		}
		// 复制图片
		String originalPath = imageDir + File.separator + fileName;
		// 仅复制图片
		copyFile(originalPath, AttachmentPath.UPLOAD_IMAGE.getCode(), CommonConstant.FALSE,
				CommonConstant.FALSE);
		// 插入数据库
		create(fileName, AttachmentPath.UPLOAD_IMAGE.getDesc() + fileName, true);
		return true;
	}

	/**
	 * 加载 ZiGuan 图片
	 *
	 * @param images 图像
	 */
	@Override
	public void loadZiguanImages(Map<String, String> images) {
		if (images.isEmpty()) {
			return;
		}
		// 插入数据库
		List<Attachment> attachments = Lists.newArrayList();
		for (Map.Entry<String, String> entry : images.entrySet()) {
			attachments.add(Attachment.builder().resourceId(entry.getKey())
					.fileName(entry.getValue())
					.filePath(AttachmentPath.UPLOAD_IMAGE.getOriginDesc() + entry.getValue())
					.isPublic(CommonConstant.TRUE).build());
		}
		attachmentMapper.batchInsert(attachments);
	}

	/**
	 * 上传文件
	 *
	 * @param file       文件
	 * @param type       类型
	 * @param insertDb   插入 DB
	 * @param renameFile 重命名文件
	 * @return {@code AttachmentBo }
	 */
	private AttachmentBo uploadFile(MultipartFile file, Integer type, Boolean insertDb,
			Boolean renameFile) {
		String destPath = EnumUtils.getEnumDescByCode(type, AttachmentPath.class);

		String fileName = file.getOriginalFilename();
		if (renameFile) {
			String[] ss = StringUtils.split(fileName, ".");
			if (ss.length > 1) {
				// 如果文件没有后缀，不重命名，方便后期问题追踪。没有后缀对文件，一般是bug
				String postfix = ss[ss.length - 1];
				fileName = IDGeneratorUtil.getID() + "." + postfix;
			}
		}
		String destPathFileName = destPath + fileName;
		String storeInDbName = EnumUtils.getEnumByCode(type, AttachmentPath.class).getOriginDesc()
				+ fileName;
		File dest = new File(destPathFileName);

		try {
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			file.transferTo(dest);
		} catch (IOException e) {
			// 复制文件失败
			log.error("copy file error: " + fileName + " ,to:" + dest.getAbsolutePath(), e);
			throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);
		}

		if (null != insertDb && insertDb) {
			return create(fileName, storeInDbName, renameFile);
		}

		// 返回给前端对展示名称
		return AttachmentBo.builder().filePath(storeInDbName).fileName(file.getOriginalFilename())
				.build();
	}

	/**
	 * 如果重命名上传的文件，将文件名和id设置为同一个uuid
	 *
	 * @param filename 文件名
	 * @param filePath 文件路径
	 * @param idIsName 文件名和id是否一致
	 * @return
	 */
	private AttachmentBo create(String filename, String filePath, Boolean idIsName) {
		if (idIsName == null || !idIsName) {
			return create(filename, filePath);
		}
		String resourceId = StringUtils.split(filename, '.')[0];
		Attachment attachment = Attachment.builder().resourceId(resourceId).fileName(filename)
				.filePath(filePath).isPublic(CommonConstant.TRUE).build();
		int insert = attachmentMapper.insert(attachment);
		if (insert > 0) {
			AttachmentBo attachmentBo = BeanUtils.copy(attachment, AttachmentBo.class);
			attachmentBo.setFilePath(utilService.handlerFileUrl(attachment.getFilePath()));
			return attachmentBo;
		}
		return null;
	}

	/**
	 * 存在
	 *
	 * @param attachmentId 附件id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean isExist(String attachmentId) {
		Attachment attachment = attachmentMapper.selectById(attachmentId);
		return !(attachment == null
				|| !new File(ConfigUtil.getContextProperty("cr.local.upload.path") + "/"
						+ attachment.getFilePath()).exists());
	}
}
