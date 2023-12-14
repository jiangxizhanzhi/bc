package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.DictInfo;

import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/7 17:49
 * @desc :
 **/
public interface DictInfoService extends IService<DictInfo> {

    List<DictInfo> getDictInfo(String key);
}
