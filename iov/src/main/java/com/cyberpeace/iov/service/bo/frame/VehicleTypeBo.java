package com.cyberpeace.iov.service.bo.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.iov.dao.entity.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VehicleTypeBo {

	private Integer resourceId;

	private String name;

	public VehicleType bo2Entity(){
		VehicleType entity = BeanUtils.copy(this, VehicleType.class);
		return entity;
	}
}
