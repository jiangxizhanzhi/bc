package com.cyberpeace.cs.service;

import com.cyberpeace.cs.service.context.upload.FileChunkReq;
import com.cyberpeace.cs.service.context.upload.FileMergeReq;
import com.cyberpeace.cs.service.context.upload.MergeFileRsp;

public interface FileChunkUploadService {

    Integer checkFile(String md5);

    Integer uploadChunk(FileChunkReq req);

    MergeFileRsp mergeFile(FileMergeReq req);
}
