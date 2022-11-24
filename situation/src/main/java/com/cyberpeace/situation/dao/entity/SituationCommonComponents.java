package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:00
 */
@Data
@TableName("situation_common_components")
public class SituationCommonComponents extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    private Integer componentType;

    private Integer type;

    private String img;

    private String url;

    public enum CommonComponentsType{

        INPUT(0,"文本输入框"),
        SITUATION_TITLE (3,"态势标题"),
        CHART_TITLE(1,"图表标题"),
        IMG(1,"图片"),
        VIDEO(0,"视频"),
        AUDIO(1,"音频");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        CommonComponentsType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public static HashMap<Integer, String> getCommonComponentsType(){
        HashMap<Integer, String> map = new HashMap();
        map.put(CommonComponentsType.INPUT.code, CommonComponentsType.INPUT.desc);
        map.put(CommonComponentsType.SITUATION_TITLE.code, CommonComponentsType.SITUATION_TITLE.desc);
        map.put(CommonComponentsType.CHART_TITLE.code, CommonComponentsType.CHART_TITLE.desc);
        map.put(CommonComponentsType.IMG.code, CommonComponentsType.IMG.desc);
        map.put(CommonComponentsType.VIDEO.code, CommonComponentsType.VIDEO.desc);
        map.put(CommonComponentsType.AUDIO.code, CommonComponentsType.AUDIO.desc);
        return map;
    }

}
