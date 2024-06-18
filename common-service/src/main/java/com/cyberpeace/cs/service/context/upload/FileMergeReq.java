package com.cyberpeace.cs.service.context.upload;

import lombok.Data;

@Data
public class FileMergeReq {

	private Integer total;

	private String fileName;

	private String md5;

	private Long fileSize;

	private String fileType;
}
