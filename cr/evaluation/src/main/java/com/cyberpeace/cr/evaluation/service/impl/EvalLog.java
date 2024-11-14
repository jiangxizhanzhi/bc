package com.cyberpeace.cr.evaluation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;

/**
 * 测评日志
 *
 * @author SN
 * @date 2024/11/14
 */
public class EvalLog {
    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger("eval");

    /**
     * id日志格式
     */
    private static final String ID_LOG_FORMAT = "ID {}: {}";

    /**
     * 变量日志格式
     */
    private static final String VARIABLE_LOG_FORMAT = "variable %s [%s] %s";

    /**
     * 编号日志格式
     */
    private static final String INDEX_LOG_FORMAT = "index %s [%s] %s";

    /**
     * 信息
     *
     * @param evalRecord 测评记录
     * @param msg        消息
     */
    static void info(EvalRecord evalRecord, String msg) {
		LOG.info(ID_LOG_FORMAT, evalRecord.getResourceId(), msg);
    }

    /**
     * 信息
     *
     * @param evalRecord      测评记录
     * @param evalVariableRef 测评变量裁判
     * @param msg             消息
     */
    static void info(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format(VARIABLE_LOG_FORMAT, evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        info(evalRecord, newMsg);
    }

    /**
     * 信息
     *
     * @param evalRecord   测评记录
     * @param evalIndexRef 测评编号裁判
     * @param msg          消息
     */
    static void info(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format(INDEX_LOG_FORMAT, evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        info(evalRecord, newMsg);
    }

    /**
     * 错误
     *
     * @param evalRecord 测评记录
     * @param msg        消息
     */
    static void error(EvalRecord evalRecord, String msg) {
        LOG.error(ID_LOG_FORMAT, evalRecord.getResourceId(), msg);
    }

    /**
     * 错误
     *
     * @param evalRecord      测评记录
     * @param evalVariableRef 测评变量裁判
     * @param msg             消息
     */
    static void error(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format(VARIABLE_LOG_FORMAT, evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        error(evalRecord, newMsg);
    }

    /**
     * 错误
     *
     * @param evalRecord   测评记录
     * @param evalIndexRef 测评编号裁判
     * @param msg          消息
     */
    static void error(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format(INDEX_LOG_FORMAT, evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        error(evalRecord, newMsg);
    }

    /**
     * 调试
     *
     * @param evalRecord 测评记录
     * @param msg        消息
     */
    static void debug(EvalRecord evalRecord, String msg) {
        LOG.debug(ID_LOG_FORMAT, evalRecord.getResourceId(), msg);
    }

    /**
     * 调试
     *
     * @param evalRecord      测评记录
     * @param evalVariableRef 测评变量裁判
     * @param msg             消息
     */
    static void debug(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format(VARIABLE_LOG_FORMAT, evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        debug(evalRecord, newMsg);
    }

    /**
     * 调试
     *
     * @param evalRecord   测评记录
     * @param evalIndexRef 测评编号裁判
     * @param msg          消息
     */
    static void debug(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format(INDEX_LOG_FORMAT, evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        debug(evalRecord, newMsg);
    }
}
