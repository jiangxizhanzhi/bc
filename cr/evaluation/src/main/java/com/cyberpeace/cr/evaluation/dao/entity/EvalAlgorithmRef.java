package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * eval 算法 ref
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_eval_algorithm_ref")
public class EvalAlgorithmRef extends BaseTable {
    /**
     * 资源id
     *
     * @see String
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    private String name;

    /**
     * 描述
     *
     * @see String
     */
    private String description;

    /**
     * 类型
     *
     * @see Integer
     */
    private Integer type;

    /**
     * 算法语言
     *
     * @see Integer
     */
    private Integer algorithmLanguage;

    /**
     * 代号
     *
     * @see String
     */
    private String code;

    /**
     * 矩阵数据
     *
     * @see String
     */
    private String matrixData;

    /**
     * 参数
     *
     * @author SN
     * @date 2024/11/07
     */
    public enum Params implements GenericEnum {
        /**
         * 数
         */
        NUMBER(0, "Number"),
        /**
         * 字符串
         */
        STRING(1, "String"),
        /**
         * 布尔
         */
        BOOL(2, "Bool"),
        /**
         * ip地址
         */
        IP(3, "IP");

        /**
         * 代号
         */
        @Getter
        private final Integer code;

        /**
         * 描述
         */
        @Getter
        private final String desc;

        Params(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 模式
     *
     * @see Integer
     */
    @ApiModelProperty("算法模型")
    private Integer mode;

    /**
     * 模式
     *
     * @author SN
     * @date 2024/11/07
     */
    public enum Mode implements GenericEnum {

        /**
         * 评分算法
         */
        SCORING_ALGORITHM(0, "其他算法"),
        /**
         * 格式算法
         */
        FORMAT_ALGORITHM(1, "层次分析法");

        /**
         * 代号
         */
        @Getter
        private Integer code;

        /**
         * 描述
         */
        @Getter
        private String desc;

        private Mode(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }


}
