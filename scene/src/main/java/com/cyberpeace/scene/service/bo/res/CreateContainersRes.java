package com.cyberpeace.scene.service.bo.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.scene.service.bo.ContainerItem;
import com.cyberpeace.scene.service.bo.Net;

import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/28 9:44
 * @desc :
 **/
@Data
public class CreateContainersRes extends ContainerItem implements Serializable {

	/**
	 * 端口映射参数
	 */
	private List<Net> nets;

}
