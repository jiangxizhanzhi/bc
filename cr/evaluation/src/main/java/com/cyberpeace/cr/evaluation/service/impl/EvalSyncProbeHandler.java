package com.cyberpeace.cr.evaluation.service.impl;

import com.cyberpeace.cr.evaluation.service.context.SyncProbeDataContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

@Slf4j
public class EvalSyncProbeHandler {
    @Getter
    private String recordId;

    private final ReentrantLock syncProbeLock = new ReentrantLock();

    public void run(Consumer<SyncProbeDataContext> func, SyncProbeDataContext args) {
        syncProbeLock.lock();
        this.recordId = args.getEvalRecord().getResourceId();

        func.accept(args);

        this.recordId = null;
        syncProbeLock.unlock();
    }
}
