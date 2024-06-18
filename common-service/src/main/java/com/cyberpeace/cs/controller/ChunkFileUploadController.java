package com.cyberpeace.cs.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.service.FileChunkUploadService;
import com.cyberpeace.cs.service.context.upload.FileChunkReq;
import com.cyberpeace.cs.service.context.upload.FileMergeReq;
import com.cyberpeace.cs.service.context.upload.MergeFileRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "文件分片上传")
@RestController
@RequestMapping("/chunk/upload")
@RequiresAuthentication
@Slf4j
public class ChunkFileUploadController {

	@Autowired
	private FileChunkUploadService fileChunkUploadService;

	@ApiOperation("md5文件校验")
	@GetMapping("/check")
	public BaseRsp<Integer> checkFile(@ApiParam(value = "文件识别码", name = "md5") String md5) {
		Integer status = fileChunkUploadService.checkFile(md5);
		return new BaseRsp<>(status);
	}

	@ApiOperation("分片文件上传")
	@PostMapping("")
	public BaseRsp<Integer> upload(@RequestBody FileChunkReq req) {
		Integer index = fileChunkUploadService.uploadChunk(req);
		return new BaseRsp<>(index);
	}

	@ApiOperation("分片文件的合并")
	@PostMapping("/merge")
	public BaseRsp<MergeFileRsp> merge(@RequestBody FileMergeReq req) {
		MergeFileRsp rsp = fileChunkUploadService.mergeFile(req);
		return new BaseRsp<>(rsp);
	}

}
