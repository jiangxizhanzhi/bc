package com.cyberpeace.situation.dao.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author xuxz
 * @Date 11/11/2022 上午11:41
 */
@Data
@TableName("situation_dashboard_components_setting")
public class DashboardComponents {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty("视角类型")
    private String type;

    @ApiModelProperty("数据源id")
    private String datasourceId;

    @ApiModelProperty("数据源配置")
    private String datasourceSetting;

    @ApiModelProperty("组件id")
    private String componentsId;

    @ApiModelProperty("组件配置")
    private String componentSetting;

    @ApiModelProperty("组件配置对象")
    @TableField(exist = false)
    private JSONObject componentSettingObject;

    @ApiModelProperty("图层顺序")
    private Integer componentIndex;

    public enum ComponentType{
        GIS(0,"GIS资源"),
        TWO_DIMENSIONAL_COMPONENT(1,"二维组件"),
        COMMON_CONTROLS(2,"通用控件"),
        MEDIA_COMPONENT(3,"媒体组件");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        ComponentType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }

    public static HashMap<Integer, String> getComponentType(){
        HashMap<Integer, String> map = new HashMap();
        map.put(ComponentType.GIS.code,ComponentType.GIS.desc);
        map.put(ComponentType.TWO_DIMENSIONAL_COMPONENT.code,ComponentType.TWO_DIMENSIONAL_COMPONENT.desc);
        map.put(ComponentType.COMMON_CONTROLS.code,ComponentType.COMMON_CONTROLS.desc);
        map.put(ComponentType.MEDIA_COMPONENT.code,ComponentType.MEDIA_COMPONENT.desc);
        return map;
    }
}
