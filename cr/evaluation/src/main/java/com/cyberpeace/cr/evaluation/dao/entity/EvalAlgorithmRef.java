package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

import java.util.List;

/**
 * eval 算法 ref
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_eval_algorithm_ref")
public class EvalAlgorithmRef extends BaseTable {
    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 算法语言
     */
    private Integer algorithmLanguage;

    /**
     * 代号
     */
    private String code;

    /**
     * 矩阵数据
     */
    private String matrixData;

    public enum Params implements GenericEnum {
        NUMBER(0, "Number"),
        STRING(1, "String"),
        BOOL(2, "Bool"),
        IP(3, "IP");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Params(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 模式
     */
    @ApiModelProperty("算法模型")
    private Integer mode;

    public enum Mode implements GenericEnum {

        SCORING_ALGORITHM(0, "其他算法"), FORMAT_ALGORITHM(1, "层次分析法");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        private Mode(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }


}
