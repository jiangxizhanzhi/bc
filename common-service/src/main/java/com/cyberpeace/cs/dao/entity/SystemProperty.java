package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("system_property")
public class SystemProperty implements Serializable {

	private String name;

	private String desc;

	private String content;
}
