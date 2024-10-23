package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.space.model.SpaceBaseTable;
import lombok.Data;

@Data
@TableName("space_finger_print")
public class SpaceFingerPrint extends SpaceBaseTable {

	private Integer type;

	private String content;

}
