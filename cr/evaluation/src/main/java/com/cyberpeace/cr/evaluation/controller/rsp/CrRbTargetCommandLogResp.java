package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandLog;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 靶标命令执行结果日志返回
 */
@Data
public class CrRbTargetCommandLogResp extends CrRbTargetCommandLog implements Serializable {
    /**
     * 执行的模板names
     */
    private Set<String> commandTemplateNames;
}

