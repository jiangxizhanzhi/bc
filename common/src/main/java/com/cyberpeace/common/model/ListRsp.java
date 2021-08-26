package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 分页返回数据模型
 */
@Data
public class ListRsp<T> implements Serializable {

	private List<T> list = Lists.newArrayList();

	private long total;
}
