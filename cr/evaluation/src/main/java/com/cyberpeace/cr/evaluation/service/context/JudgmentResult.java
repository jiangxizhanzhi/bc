package com.cyberpeace.cr.evaluation.service.context;

public enum JudgmentResult {
    UNJUDGED(0),

    SUCCESS(1),

    FAILURE(2);

    private final int code;

    JudgmentResult(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
