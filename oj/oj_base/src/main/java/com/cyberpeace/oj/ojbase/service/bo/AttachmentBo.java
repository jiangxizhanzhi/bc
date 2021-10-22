package com.cyberpeace.oj.ojbase.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttachmentBo implements Serializable {

	private String resourceId;

	private String fileName;

	private String filePath;

}
