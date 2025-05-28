package com.cyberpeace.space.controller;

import com.cyberpeace.space.dao.entity.SpaceAlgorithmEquipment;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.SpaceAlgorithmEquipmentService;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmEquipmentBo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 空间算法设备Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "知识库-算法库-装备效能评估算法")
@RestController
@RequestMapping("/space/algorithm/algorithm_equipment")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmEquipmentController extends SpaceControllerBase<SpaceAlgorithmEquipmentService, SpaceAlgorithmEquipment, SpaceAlgorithmEquipmentBo, SpacePageReqBase> {
}
    