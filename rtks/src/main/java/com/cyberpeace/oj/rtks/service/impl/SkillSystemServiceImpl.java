package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.model.PageReqBase;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.rtks.dao.entity.SkillSystem;
import com.cyberpeace.oj.rtks.dao.mapper.SkillSystemMapper;
import com.cyberpeace.oj.rtks.service.SkillSystemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SkillSystemServiceImpl extends ServiceImpl<SkillSystemMapper, SkillSystem>
        implements SkillSystemService {
    @Autowired
    private SkillSystemMapper skillSystemMapper;

    @Override
    public ListRsp<SkillSystem> page(PageReqBase pageReqBase) {
        Page page = pageReqBase.toPageParam();
        IPage<SkillSystem> result = skillSystemMapper.selectPage(page,
                new QueryWrapper<SkillSystem>().
                        like(StringUtils.isNotEmpty(pageReqBase.getPattern()), "content", pageReqBase.getPattern())
        );
        return PageUtil.getListResult(result);
    }
}




