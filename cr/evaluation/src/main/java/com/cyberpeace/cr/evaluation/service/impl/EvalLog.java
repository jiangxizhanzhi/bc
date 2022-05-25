package com.cyberpeace.cr.evaluation.service.impl;

import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EvalLog {
    private static final Logger LOG = LoggerFactory.getLogger("eval");

    static void info(EvalRecord evalRecord, String msg) {
        LOG.info(String.format("ID %s: %s", evalRecord.getResourceId(), msg));
    }

    static void error(EvalRecord evalRecord, String msg) {
        LOG.error(String.format("ID %s: %s", evalRecord.getResourceId(), msg));
    }

}
