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
 * @date 2024/11/07
 */
@Data
@TableName("cr_eval_probe_data")
public class EvalProbeData extends BaseTable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 场景id
     */
    private String sceneId;

    /**
     * 装置id
     */
    private String deviceId;

    /**
     * 探针id
     */
    private String probeId;

    /**
     * 收集时间
     */
    private Date collectionTime;

    /**
     * 装置ip地址
     */
    private String deviceIp;

    /**
     * 内容
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
