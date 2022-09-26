package com.cyberpeace.cr.evaluation.service.impl;

import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvalLog {
    private static final Logger LOG = LoggerFactory.getLogger("eval");

    static void info(EvalRecord evalRecord, String msg) {
        LOG.info(String.format("ID %s: %s", evalRecord.getResourceId(), msg));
    }

    static void info(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format("variable %s [%s] %s", evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        info(evalRecord, newMsg);
    }

    static void info(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format("index %s [%s] %s", evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        info(evalRecord, newMsg);
    }

    static void error(EvalRecord evalRecord, String msg) {
        LOG.error(String.format("ID %s: %s", evalRecord.getResourceId(), msg));
    }

    static void error(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format("variable %s [%s] %s", evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        error(evalRecord, newMsg);
    }

    static void error(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format("index %s [%s] %s", evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        error(evalRecord, newMsg);
    }

    static void debug(EvalRecord evalRecord, String msg) {
        LOG.debug(String.format("ID %s: %s", evalRecord.getResourceId(), msg));
    }

    static void debug(EvalRecord evalRecord, EvalVariableRef evalVariableRef, String msg) {
        String newMsg = String.format("variable %s [%s] %s", evalVariableRef.getResourceId(), evalVariableRef.getName(), msg);
        debug(evalRecord, newMsg);
    }

    static void debug(EvalRecord evalRecord, EvalIndexRef evalIndexRef, String msg) {
        String newMsg = String.format("index %s [%s] %s", evalIndexRef.getResourceId(), evalIndexRef.getName(), msg);
        debug(evalRecord, newMsg);
    }
}
