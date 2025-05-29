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
/**
 * ### 分析与总结 
 * #### 代码功能概述 
 * 该代码是一个 `AttachmentServiceImpl` 类，实现了 `AttachmentService` 接口，主要用于处理文件附件相关的业务逻辑。它提供了多种方法来管理文件的上传、查询、复制、删除等操作，并支持批量处理。 
 * --- 
 * #### 主要功能模块 
 * 1. **文件查询** 
 * - `selectByIds`: 根据附件ID列表查询附件信息，并返回对应的 `AttachmentBo` 对象。 
 * - `batchGetByIds`: 批量获取附件信息。 
 * 2. **文件创建** 
 * - `create`: 创建一个新的附件记录，并保存到数据库中。 
 * - `uploadFile`: 处理文件上传逻辑，包括重命名文件和保存到指定路径。 
 * 3. **文件复制** 
 * - `copyFile`: 将文件从源路径复制到目标路径，并可以选择性地插入数据库记录。 
 * 4. **批量插入** 
 * - `batchInsert`: 批量插入附件记录到数据库。 
 * 5. **特殊操作** 
 * - `loadImage`: 用于加载图片并插入数据库。 
 * - `loadZiguanImages`: 批量加载 Ziguan 图片并插入数据库。 
 * 6. **文件存在检查** 
 * - `isExist`: 检查附件是否存在。 
 * 7. **辅助方法** 
 * - `reFileName`: 生成新的文件名（通常用于重命名）。 
 * - `uploadFile`: 内部方法，封装了文件上传的核心逻辑。 
 * --- 
 * #### 技术特点 
 * - **依赖注入**: 使用了 Spring 的 `@Autowired` 注解注入了 `AttachmentMapper` 和 `UtilService` 等依赖。 
 * - **工具类**: 使用了 `BeanUtils`、`StreamApiUtils`、`IDGeneratorUtil` 等工具类简化代码逻辑。 
 * - **异常处理**: 在文件操作失败时抛出自定义异常 `BusinessException`，并记录日志。 
 * - **枚举支持**: 使用了 `EnumUtils` 和 `AttachmentPath` 枚举类来增强代码可读性和灵活性。 
 * --- 
 * #### 优点 
 * 1. **模块化设计**: 各个功能模块职责清晰，便于维护和扩展。 
 * 2. **高复用性**: 提供了多种通用方法，如文件上传、复制、批量插入等。 
 * 3. **安全性**: 通过异常处理机制确保程序的健壮性。 
 * --- 
 * #### 缺点 
 * 1. **日志记录不足**: 部分方法的日志记录较为简单，可能需要更详细的上下文信息。 
 * 2. **代码重复**: 如 `uploadFile` 方法有多个重载版本，可能存在冗余代码。 
 * 3. **未处理边界情况**: 某些方法对输入参数的校验不够严格，可能导致潜在问题。 
 * --- 
 * #### 总结 
 * 该代码实现了一个功能全面的附件服务，涵盖了文件管理的主要场景。通过合理的模块划分和工具类的使用，代码具有较高的可读性和可维护性。但部分地方仍需优化，例如减少重复代码、增加日志详细程度以及完善参数校验等。 
**/
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

