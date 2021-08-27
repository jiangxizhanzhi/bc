package com.cyberpeace.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;

public interface BaseService<T> extends IService<T> {

	T getOr404(String resourceId);

}