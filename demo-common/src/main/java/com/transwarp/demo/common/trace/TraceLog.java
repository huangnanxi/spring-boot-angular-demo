package com.transwarp.demo.common.trace;

import org.slf4j.Logger;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * Created by huangnx on 2018/8/26.
 */
public class TraceLog {
    private Logger             logger;

    private String             method;

    private long               threshold     = 0L;

    private String             invokeNo;

    private String             exType        = "N";

    private String             errorCode     = "success";

    private String             beyondThd     = "N";

    private long               beginTime;

    private long               endTime;

    private StringBuilder      message       = new StringBuilder();

    public static final String MDC_INVOKE_NO = "invokeNo";

    public TraceLog(Logger logger, String method, long threshold) {
        this.logger = logger;
        this.method = method;
        this.threshold = threshold;
        this.invokeNo = MDC.get("invokeNo");
        if (this.invokeNo == null) {
            MDC.put("invokeNo", UUID.randomUUID().toString().replace("-", ""));
        }

    }

    public void begin() {
        this.beginTime = System.currentTimeMillis();
    }

    public void end() {
        if (this.logger.isWarnEnabled()) {
            this.endTime = System.currentTimeMillis();
            long runTime = this.endTime - this.beginTime;
            if (this.threshold > 0L && runTime > this.threshold) {
                this.beyondThd = "Y";
            }

            this.message.append("ME:").append(this.method).append("|RT:").append(runTime).append("|BT:")
                    .append(this.beyondThd).append("|ET:").append(this.exType).append("|EC:").append(this.errorCode);
        }

    }

    public void reset(String method, long threshold) {
        this.method = method;
        this.threshold = threshold;
        this.exType = "N";
        this.beyondThd = "N";
    }

    public void setExType(String exType) {
        this.exType = exType;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void log() {
        try {
            if (this.logger.isWarnEnabled()) {
                this.logger.warn(this.message.toString());
            }
        } finally {
            if (this.invokeNo == null) {
                MDC.remove("invokeNo");
            }

        }

    }

    @Override
    public String toString() {
        return this.message.toString();
    }
}
