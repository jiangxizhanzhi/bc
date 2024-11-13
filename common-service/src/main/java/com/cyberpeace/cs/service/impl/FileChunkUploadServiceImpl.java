package com.cyberpeace.cs.service.impl;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.constant.FileChunkUploadStatus;
import com.cyberpeace.cs.dao.entity.FileChunk;
import com.cyberpeace.cs.dao.mapper.FileChunkMapper;
import com.cyberpeace.cs.service.FileChunkUploadService;
import com.cyberpeace.cs.service.context.upload.FileChunkReq;
import com.cyberpeace.cs.service.context.upload.FileMergeReq;
import com.cyberpeace.cs.service.context.upload.MergeFileRsp;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件块上传Service实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Service
@Slf4j
public class FileChunkUploadServiceImpl implements FileChunkUploadService {

	/**
	 * 块大小
	 */
	private final static long CHUNK_SIZE = 50 * 1024 * 1024;

	/**
	 * ISO 后缀
	 */
	private final static String ISO_SUFFIX = ".iso";

	/**
	 * 上传目录
	 */
	private String uploadDir;

	/**
	 * 文件块Mapper
	 */
	@Autowired
	private FileChunkMapper fileChunkMapper;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		uploadDir = ConfigUtil.getContextProperty("iso.upload.path");
	}

	/**
	 * 检查文件
	 *
	 * @param md5 md5
	 * @return {@code Integer }
	 */
	@Override
	public Integer checkFile(String md5) {
		List<FileChunk> fileChunkList = getChunkListByMd5(md5);
		if (fileChunkList.isEmpty()) {
			// not uploaded
			return FileChunkUploadStatus.NOT_UPLOADED;
		}
		if (fileChunkList.get(0).getStatus().equals(FileChunkUploadStatus.SUCCESS)) {
			return FileChunkUploadStatus.SUCCESS;
		}
		return FileChunkUploadStatus.ONGOING;
	}

	/**
	 * 上传区块
	 *
	 * @param req 请求体
	 * @return {@code Integer }
	 */
	@Override
	public Integer uploadChunk(FileChunkReq req) {
		if (req.getFile() == null) {
			throw new BusinessException(ErrorCode.EMPTY_FILE);
		}
		FileChunk fileChunk = getChunkByMd5AndIndex(req.getMd5(), req.getIndex());
		if (null != fileChunk) {
			// 该分片已经上传
			return req.getIndex();
		}
		String destDir = uploadDir + req.getMd5();
		this.uploadFileByRandomAccessFile(destDir, req.getFileName(), req.getIndex(),
				req.getFile());
		FileChunk chunk = new FileChunk();
		chunk.setFilename(req.getFileName());
		chunk.setFilesize(req.getFileSize());
		chunk.setIndex(req.getIndex());
		chunk.setStatus(FileChunkUploadStatus.ONGOING);
		chunk.setTotal(req.getTotal());
		chunk.setMd5(req.getMd5());
		fileChunkMapper.insert(chunk);
		return req.getIndex();
	}

	/**
	 * 合并文件
	 *
	 * @param req 请求体
	 * @return {@code MergeFileRsp }
	 */
	@Override
	public MergeFileRsp mergeFile(FileMergeReq req) {
		List<FileChunk> fileChunkList = getChunkListByMd5(req.getMd5());
		if (fileChunkList.size() != req.getTotal()) {
			throw new BusinessException(ErrorCode.CHUNK_MISSING);
		}
		FileChunk chunk = new FileChunk();
		chunk.setFilename(req.getFileName());
		chunk.setFilesize(req.getFileSize());
		chunk.setIndex(0);
		chunk.setStatus(FileChunkUploadStatus.SUCCESS);
		chunk.setTotal(1);
		chunk.setMd5(req.getMd5());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				fileChunkMapper.deleteBatchIds(StreamApiUtils.map(fileChunkList, FileChunk::getId));
				fileChunkMapper.insert(chunk);
			}
		});

		MergeFileRsp rsp = new MergeFileRsp();
		rsp.setFileName(chunk.getFilename());
		rsp.setTmpdirUuid(chunk.getMd5());
		return rsp;
	}

	/**
	 * 按随机访问文件上传文件
	 *
	 * @param destDir  dest dir
	 * @param filename 文件名
	 * @param index    指数
	 * @param file     文件
	 */
	private void uploadFileByRandomAccessFile(String destDir, String filename, Integer index,
			MultipartFile file) {
		FileUtil.mkdirs(destDir);
		String dest = destDir + File.separator + filename;
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(dest, "rw")) {
			// index starts from 1
			long offset = CHUNK_SIZE * (index - 1);
			randomAccessFile.seek(offset);
			randomAccessFile.write(file.getBytes());
		} catch (Exception e) {
			log.error("chunk upload failed, dest:[{}], index:[{}]", dest, index, e);
			throw new BusinessException(ErrorCode.FILE_UPLOAD_FAIL);
		}
	}

	/**
	 * 获取块列表由md5
	 *
	 * @param md5 md5
	 * @return {@code List<FileChunk> }
	 */
	private List<FileChunk> getChunkListByMd5(String md5) {
		LambdaQueryWrapper<FileChunk> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(FileChunk::getMd5, md5);
		return fileChunkMapper.selectList(wrapper);
	}

	/**
	 * 获取块由md5和指数
	 *
	 * @param md5   md5
	 * @param index 指数
	 * @return {@code FileChunk }
	 */
	private FileChunk getChunkByMd5AndIndex(String md5, Integer index) {
		LambdaQueryWrapper<FileChunk> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(FileChunk::getMd5, md5).eq(FileChunk::getIndex, index);
		return fileChunkMapper.selectOne(wrapper);
	}
}
