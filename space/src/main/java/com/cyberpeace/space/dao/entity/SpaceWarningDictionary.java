package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.space.model.SpaceBaseTable;
import lombok.Data;

@Data
@TableName("space_warning_dictionary")
public class SpaceWarningDictionary extends SpaceBaseTable {

	private String rule;

}
