package com.cyberpeace.space.controller;

import com.cyberpeace.space.dao.entity.SpaceAlgorithmIndividual;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.SpaceAlgorithmIndividualService;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmIndividualBo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 空间算法个人Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "知识库-算法库-个人能力评估算法")
@RestController
@RequestMapping("/space/algorithm/algorithm_individual")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmIndividualController extends SpaceControllerBase<SpaceAlgorithmIndividualService, SpaceAlgorithmIndividual, SpaceAlgorithmIndividualBo, SpacePageReqBase> {

    /**
     * 检查值
     *
     * @param entity 实体
     * @throws
     * @see SpaceAlgorithmIndividual
     */
    @Override
    public void checkValue(SpaceAlgorithmIndividual entity){
        this.checkPercentageValue(entity.getRolePercent() + entity.getExpertisePercent());
    }
}
    