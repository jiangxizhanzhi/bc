package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页返回数据模型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListRsp<T> implements Serializable {

	private List<T> list = Lists.newArrayList();

	private long total;

	private long lastPage;

	private long currentPage;
}
