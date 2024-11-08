package com.cyberpeace.cr.evaluation.service.impl;

import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 评估日志
 *
 * @author SN
 * @date 2024/11/08
 */
public class EvalLog {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger("eval");

    /**
     * 信息
     *
     * @param evalRecord EVAL 记录
     * @param msg        味精
     */
    static void info(EvalRecord evalRecord, String msg) {
        LOG.info(String.format("ID %s: %s", evalRecord.getResourceId(), msg));
    }

    /**
     * 信息
     *
     * @param evalRecord      EVAL 记录
     * @param evalVariableRef eval 变量 ref
     * @param msg             味精
     */
    static void info(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format("variable %s [%s] %s", evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        info(evalRecord, newMsg);
    }

    /**
     * 信息
     *
     * @param evalRecord   EVAL 记录
     * @param evalIndexRef eval 索引 ref
     * @param msg          味精
     */
    static void info(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format("index %s [%s] %s", evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        info(evalRecord, newMsg);
    }

    /**
     * 错误
     *
     * @param evalRecord EVAL 记录
     * @param msg        味精
     */
    static void error(EvalRecord evalRecord, String msg) {
        LOG.error(String.format("ID %s: %s", evalRecord.getResourceId(), msg));
    }

    /**
     * 错误
     *
     * @param evalRecord      EVAL 记录
     * @param evalVariableRef eval 变量 ref
     * @param msg             味精
     */
    static void error(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format("variable %s [%s] %s", evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        error(evalRecord, newMsg);
    }

    /**
     * 错误
     *
     * @param evalRecord   EVAL 记录
     * @param evalIndexRef eval 索引 ref
     * @param msg          味精
     */
    static void error(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format("index %s [%s] %s", evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        error(evalRecord, newMsg);
    }

    /**
     * 调试
     *
     * @param evalRecord EVAL 记录
     * @param msg        味精
     */
    static void debug(EvalRecord evalRecord, String msg) {
        LOG.debug(String.format("ID %s: %s", evalRecord.getResourceId(), msg));
    }

    /**
     * 调试
     *
     * @param evalRecord      EVAL 记录
     * @param evalVariableRef eval 变量 ref
     * @param msg             味精
     */
    static void debug(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format("variable %s [%s] %s", evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        debug(evalRecord, newMsg);
    }

    /**
     * 调试
     *
     * @param evalRecord   EVAL 记录
     * @param evalIndexRef eval 索引 ref
     * @param msg          味精
     */
    static void debug(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format("index %s [%s] %s", evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        debug(evalRecord, newMsg);
    }
}
