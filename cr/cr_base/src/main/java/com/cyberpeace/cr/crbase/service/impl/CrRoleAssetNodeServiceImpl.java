package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleAssetNode;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleAssetNodeMapper;
import com.cyberpeace.cr.crbase.service.CrRoleAssetNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CrRoleAssetNodeServiceImpl extends ServiceImpl<CrRoleAssetNodeMapper, CrRoleAssetNode> implements CrRoleAssetNodeService {
}
