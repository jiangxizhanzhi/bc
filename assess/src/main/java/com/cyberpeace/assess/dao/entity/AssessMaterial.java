package com.cyberpeace.assess.dao.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.Getter;

/**
 * 
 * @TableName assess_material
 */
@Data
@TableName("assess_material")
public class AssessMaterial extends BaseTable {

    /**
     * 
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     *
     */
    private String caseId;

    /**
     * 评估材料名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型
     */
    private Integer materialType;

    /**
     * 材料附件
     */
    private String attachment;

    /**
     * 材料附件
     */
    private String attachmentName;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private String createUserId;


    public enum MaterialType implements GenericEnum {

        EXCEL(3, "表格"), PIC(2, "图片"), TEXT(0, "文本"), VIDEO(1, "视频");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        MaterialType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static MaterialType getEnumValue(Integer value) {
            return MAPS.get(value);
        }

        private static final Map<Integer, MaterialType> MAPS =
                Maps.uniqueIndex(Arrays.asList(MaterialType.values()), MaterialType::getCode);
    }

}