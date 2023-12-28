package com.cyberpeace.scene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.scene.dao.entity.NodeEntity;
import com.cyberpeace.scene.dao.mapper.NodeMapper;
import com.cyberpeace.scene.service.NodeService;
import org.springframework.stereotype.Service;

/**
 * @author :   SN
 * @date :   2023/12/28 11:21
 * @desc :
 **/
@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, NodeEntity> implements NodeService {
}
