package com.cyberpeace.cs.service.context.upload;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileChunkReq {

	private String md5;

	private MultipartFile file;

	private String fileName;

	private Long fileSize;

	private Integer total;

	private Integer index;
}
