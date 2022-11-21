package com.cyberpeace.assess.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.assess.dao.entity.AssessCase;
import com.cyberpeace.assess.service.AssessCaseService;
import com.cyberpeace.assess.dao.mapper.AssessCaseMapper;
import org.springframework.stereotype.Service;

/**
* @author dell
* @description 针对表【assess_case】的数据库操作Service实现
* @createDate 2022-11-21 13:52:10
*/
@Service
public class AssessCaseServiceImpl extends ServiceImpl<AssessCaseMapper, AssessCase>
implements AssessCaseService{

}
