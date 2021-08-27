package com.cyberpeace.rtks.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface BaseListService<T> extends BaseService<T> {

	IPage<T> listEntity(Page<T> page, String keyword);

}