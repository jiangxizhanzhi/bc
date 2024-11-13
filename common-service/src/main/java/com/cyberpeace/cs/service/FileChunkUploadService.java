package com.cyberpeace.cs.service;

import com.cyberpeace.cs.service.context.upload.FileChunkReq;
import com.cyberpeace.cs.service.context.upload.FileMergeReq;
import com.cyberpeace.cs.service.context.upload.MergeFileRsp;

/**
 * 文件块上传Service
 *
 * @author SN
 * @date 2024/11/11
 */
public interface FileChunkUploadService {

    /**
     * 检查文件
     *
     * @param md5 md5
     * @return {@code Integer }
     */
    Integer checkFile(String md5);

    /**
     * 上传区块
     *
     * @param req 请求体
     * @return {@code Integer }
     */
    Integer uploadChunk(FileChunkReq req);

    /**
     * 合并文件
     *
     * @param req 请求体
     * @return {@code MergeFileRsp }
     */
    MergeFileRsp mergeFile(FileMergeReq req);
}
