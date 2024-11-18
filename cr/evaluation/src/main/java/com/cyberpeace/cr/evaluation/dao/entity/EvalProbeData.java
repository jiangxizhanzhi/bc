package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.util.Date;

/**
 * EVAL 探针数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_eval_probe_data")
public class EvalProbeData extends BaseTable {
    /**
     * id
     *
     * @see Integer
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 场景id
     *
     * @see String
     */
    private String sceneId;

    /**
     * 装置id
     *
     * @see String
     */
    private String deviceId;

    /**
     * 探针id
     *
     * @see String
     */
    private String probeId;

    /**
     * 收集时间
     *
     * @see Date
     */
    private Date collectionTime;

    /**
     * 装置ip地址
     *
     * @see String
     */
    private String deviceIp;

    /**
     * 内容
     *
     * @see String
     */
    private String content;

    // todo 识别采集时间需要
//    public enum Type implements GenericEnum {
//        BASE(Arrays.asList("001", "002", "004", "005"), "基础类型");
//
//        @Getter
//        private final List<String> code;
//
//        @Getter
//        private final String desc;
//
//        Type(List<String> codes, String desc) {
//            this.code = codes;
//            this.desc = desc;
//        }
//    }

}
