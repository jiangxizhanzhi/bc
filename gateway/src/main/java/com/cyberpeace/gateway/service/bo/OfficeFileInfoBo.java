package com.cyberpeace.gateway.service.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("office文件信息")
public class OfficeFileInfoBo implements Serializable {

	private String BaseFileName;

	private String OwnerId;

	private String Size;

	private String SHA256;

	private String Version;

}
