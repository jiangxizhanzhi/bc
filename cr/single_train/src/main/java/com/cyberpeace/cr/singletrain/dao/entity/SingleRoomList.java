package com.cyberpeace.cr.singletrain.dao.entity;

import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;

import lombok.Data;

@Data
public class SingleRoomList {

	private BusinessTemplate businessTemplate;

	private SingleTemplate singleTemplate;

	private SingleRoom singleRoom;

	private String missionsScheme;

}
