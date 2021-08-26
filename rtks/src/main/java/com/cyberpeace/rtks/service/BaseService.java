package com.cyberpeace.rtks.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseService<T> extends IService<T> {

	T getOr404(String resourceId);

	IPage<T> listEntity(Page<T> page, String keyword);

}