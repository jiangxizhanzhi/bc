package com.cyberpeace.evaluate.controller.res;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class FileUploadRsp implements Serializable {

	/**
	 * 文件存储id
	 */
	private String appendixId;

	/**
	 * 文件实际存储路径
	 */
	private String appendixUrl;
}
