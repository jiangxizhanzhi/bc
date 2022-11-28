package com.cyberpeace.assess.dao.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("讲义类型")
    private Integer handoutType;

    /**
     * 材料内容
     */
    private String attachmentContent;



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

    public enum HandoutType implements GenericEnum {

        /** 讲义类型*/
        NEITHER(0, "都不是"),
        MD(1, "markdown"),
        PPT(2, "ppt文件"),
        PDF(4, "pdf"),
        DOC(3, "word文档"),
        XLS(5, "xls文件");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        HandoutType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static Boolean isOfficeFile(Integer handoutType) {
            List<Integer> officeTypeList = Lists.newArrayList(HandoutType.DOC.getCode(),
                    HandoutType.PPT.getCode(), HandoutType.XLS.getCode());
            return officeTypeList.contains(handoutType);
        }

    }

}