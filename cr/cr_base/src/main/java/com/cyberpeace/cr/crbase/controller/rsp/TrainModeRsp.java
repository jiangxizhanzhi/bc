package com.cyberpeace.cr.crbase.controller.rsp;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class TrainModeRsp implements Serializable {

    @ApiModelProperty(value = "编号")
    private String resourceId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * TrainType对应的对抗练习类型 (表cr_business_template字段businessType=3时,对应表cr_team_template字段train_category)
     * businessType=3,train_category=1 -->TRAINING_TEAM_RED
     * businessType=3,train_category=2 -->TRAINING_TEAM_BLUE
     * businessType=3,train_category=3 -->TRAINING_DAILY
     */
    public enum TrainType implements GenericEnum {
        TRAINING_SINGLE(1, "单兵练习"), TRAINING_TEAM_RED(2, "红队练习"), TRAINING_TEAM_BLUE(3, "蓝队练习"),
        RED_VS_BLUE(4, "红蓝练习"), COURSE(5, "攻防实训"), TRAINING_DAILY(6, "日常练习"),COMPETITION(8, "比武竞赛");

        public static final List<Integer> TRAINING_TYPE_LIST = new ArrayList<>(
                Arrays.asList(TRAINING_SINGLE.code, TRAINING_TEAM_RED.code, TRAINING_TEAM_BLUE.code, RED_VS_BLUE.code, TRAINING_DAILY.code));

        @Getter
        private Integer code;

        @Getter
        private String desc;

        TrainType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum TrainCategoryType implements GenericEnum {
        TRAINING_TEAM_RED(1, "红队练习"), TRAINING_TEAM_BLUE(2, "蓝队练习"), TRAINING_DAILY(3, "日常练习");

        public static final List<Integer> CATEGORY_TYPE_LIST = new ArrayList<>(
                Arrays.asList(TRAINING_TEAM_RED.code, TRAINING_TEAM_BLUE.code, TRAINING_DAILY.code));

        @Getter
        private Integer code;

        @Getter
        private String desc;

        TrainCategoryType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        /**
         * 根据TrainType获取TrainCategory (表cr_business_template字段businessType=3时,对应表cr_team_template字段train_category)
         */
        public static Integer getTrainCategoryByTrainType(Integer code) {
            if (TrainType.TRAINING_TEAM_RED.code.equals(code)) {
                return TRAINING_TEAM_RED.code;
            }
            if (TrainType.TRAINING_TEAM_BLUE.code.equals(code)) {
                return TRAINING_TEAM_BLUE.code;
            }
            if (TrainType.TRAINING_DAILY.code.equals(code)) {
                return TRAINING_DAILY.code;
            }
            return null;
        }
    }

    /**
     * 根据TrainType获取BusinessType (表cr_business_template字段businessType=3时,对应表cr_team_template字段train_category)
     */
    public static Integer getBusinessTypeByTrainType(Integer code) {
        if (TrainType.TRAINING_SINGLE.code.equals(code)) {
            return BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode();
        }
        if (TrainType.TRAINING_TEAM_RED.code.equals(code)) {
            return  BusinessTemplate.BusinessType.TRAINING_TEAM.getCode();
        }
        if (TrainType.TRAINING_TEAM_BLUE.code.equals(code)) {
            return  BusinessTemplate.BusinessType.TRAINING_TEAM.getCode();
        }
        if (TrainType.RED_VS_BLUE.code.equals(code)) {
            return  BusinessTemplate.BusinessType.RED_VS_BLUE.getCode();
        }
        if (TrainType.TRAINING_DAILY.code.equals(code)) {
            return  BusinessTemplate.BusinessType.TRAINING_TEAM.getCode();
        }
        return null;
    }
}

