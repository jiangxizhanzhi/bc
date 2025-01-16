package com.cyberpeace.iov.index.service.bo.student;

import java.util.Date;

import com.cyberpeace.iov.constant.IovConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultFileBo {

	@ApiModelProperty(value = "文件名")
	private String name;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	private long timeStamp;

	@ApiModelProperty(value = "类型， 1:CAN,2:Wifi,3:蓝牙")
	private Integer type;

	@ApiModelProperty(value = "文件下载路径")
	private String path;

	public void fulfill() {
		this.createTime = new Date(this.timeStamp);
		if (this.name.contains(".")) {
			String suffix = this.name.substring(this.name.lastIndexOf("."));
			switch (suffix) {
				case ".pcap":
					this.type = IovConstants.PacketType.BLUETOOTH;
					break;
				case ".json":
					this.type = IovConstants.PacketType.WIFI;
					break;
				case ".blf":
					this.type = IovConstants.PacketType.CAN;
					break;
			}
		}
	}

}
