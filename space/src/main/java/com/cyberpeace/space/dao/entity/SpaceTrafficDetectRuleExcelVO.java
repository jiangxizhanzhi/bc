package com.cyberpeace.space.dao.entity;

import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class SpaceTrafficDetectRuleExcelVO {


    @ApiModelProperty(value = "规则内容")
    private String ruleContent;

    @ApiModelProperty(value = "规则编号")
    private String ruleId;

    @ApiModelProperty(value = "规则信息")
    private String ruleInfo;

    @ApiModelProperty(value = "威胁等级")
    private String threatLevel;

    @ApiModelProperty(value = "工具")
    private String tool;

    @ApiModelProperty(value = "防御阶段， type=57")
    private String stage;

    @ApiModelProperty(value = "攻击类型， type=58")
    private String attackType;

    private Integer attackTypeCode;

    private Integer stageCode;


    public static SpaceTrafficDetectRuleExcelVO convertModelToExcelVO(SpaceTrafficDetectRule model, CrConstantsService crConstantsService) {
        SpaceTrafficDetectRuleExcelVO spaceTrafficDetectRuleExcelVO = new SpaceTrafficDetectRuleExcelVO();
        BeanUtils.copyProperties(model, spaceTrafficDetectRuleExcelVO);
        spaceTrafficDetectRuleExcelVO.setAttackTypeCode(model.getAttackType());
        spaceTrafficDetectRuleExcelVO.setStageCode(model.getStage());

        List<CrConstantsConfig> attackConstants = crConstantsService.getByType(58);
        Map<Integer, String> attackTypeMap =
                attackConstants.stream().collect(Collectors.toMap(CrConstantsConfig::getCode, CrConstantsConfig::selectI18nName));

        if (Objects.nonNull(spaceTrafficDetectRuleExcelVO.getAttackTypeCode())) {
            spaceTrafficDetectRuleExcelVO.setAttackType(attackTypeMap.get(spaceTrafficDetectRuleExcelVO.getAttackTypeCode()));
        }

        List<CrConstantsConfig> stageConstants = crConstantsService.getByType(57);
        Map<Integer, String> stageTypeMap =
                stageConstants.stream().collect(Collectors.toMap(CrConstantsConfig::getCode, CrConstantsConfig::selectI18nName));
        if (Objects.nonNull(spaceTrafficDetectRuleExcelVO.getStageCode())) {
            spaceTrafficDetectRuleExcelVO.setStage(stageTypeMap.get(spaceTrafficDetectRuleExcelVO.getStageCode()));
        }

        return spaceTrafficDetectRuleExcelVO;
    }

}
