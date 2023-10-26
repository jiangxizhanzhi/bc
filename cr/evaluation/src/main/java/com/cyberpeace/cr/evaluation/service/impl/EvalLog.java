package com.cyberpeace.cr.evaluation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;

public class EvalLog {
    private static final Logger LOG = LoggerFactory.getLogger("eval");

    private static final String ID_LOG_FORMAT = "ID {}: {}";

    private static final String VARIABLE_LOG_FORMAT = "variable %s [%s] %s";

    private static final String INDEX_LOG_FORMAT = "index %s [%s] %s";

    static void info(EvalRecord evalRecord, String msg) {
		LOG.info(ID_LOG_FORMAT, evalRecord.getResourceId(), msg);
    }

    static void info(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format(VARIABLE_LOG_FORMAT, evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        info(evalRecord, newMsg);
    }

    static void info(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format(INDEX_LOG_FORMAT, evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        info(evalRecord, newMsg);
    }

    static void error(EvalRecord evalRecord, String msg) {
        LOG.error(ID_LOG_FORMAT, evalRecord.getResourceId(), msg);
    }

    static void error(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format(VARIABLE_LOG_FORMAT, evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        error(evalRecord, newMsg);
    }

    static void error(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format(INDEX_LOG_FORMAT, evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        error(evalRecord, newMsg);
    }

    static void debug(EvalRecord evalRecord, String msg) {
        LOG.debug(ID_LOG_FORMAT, evalRecord.getResourceId(), msg);
    }

    static void debug(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format(VARIABLE_LOG_FORMAT, evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        debug(evalRecord, newMsg);
    }

    static void debug(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format(INDEX_LOG_FORMAT, evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        debug(evalRecord, newMsg);
    }

    private EvalLog() {}
}
