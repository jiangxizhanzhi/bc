package com.cyberpeace.cs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import com.cyberpeace.cs.service.SystemPropertyService;
import com.cyberpeace.cs.dao.mapper.SystemPropertyMapper;
import org.springframework.stereotype.Service;

/**
* @author CLB
* @description 针对表【system_property】的数据库操作Service实现
* @createDate 2023-07-13 15:13:56
*/
@Service
public class SystemPropertyServiceImpl extends ServiceImpl<SystemPropertyMapper, SystemProperty> implements SystemPropertyService {

}
