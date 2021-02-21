package com.empire.flume.client.log4j2;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

import org.apache.flume.event.SimpleEvent;
import org.apache.logging.log4j.Level;

import com.alibaba.fastjson.JSON;

/**
 * @author aaron.xu
 * @date 2021/2/21
 */
public class LogEventWrapper {
    private String appName;
    private String sourceIp;
    private String method;
    private int lineNumber;
    private String loggerName;
    private long timeMillis;
    private Level level;
    private String threadName;
    private String message;
    private Object[] parameters;
    private Throwable thrown;
    private Throwable t = null;

    protected void buildFlumeEvent(SimpleEvent simpleEvent, String appName, String sourceIp)
        throws EventBuildException {
        try {
            this.appName = appName;
            this.sourceIp = sourceIp;
            byte[] msg = this.initMessage();
            this.message = new String(msg);
            this.buildHeaders(simpleEvent.getHeaders());
            simpleEvent.setBody(JSON.toJSONString(this).getBytes());
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new EventBuildException(unsupportedEncodingException);
        }
    }

    protected void buildHeaders(Map<String, String> headers) {
        if (this.method != null) {
            headers.put("method",
                AbstractContext.getStringBuilder().append(this.method).append(':').append(this.lineNumber).toString());
        }
        headers.put("appName", this.appName);
        headers.put("sourceIp", this.sourceIp);
        headers.put("logger", this.loggerName);
        headers.put("timestamp", String.valueOf(this.timeMillis));
        headers.put("level", this.level.toString());
        headers.put("thread", this.threadName);
    }

    protected byte[] initMessage() throws UnsupportedEncodingException {
        if (this.thrown == null) {
            return this.message.getBytes("UTF8");
        } else {
            StringBuilder body = AbstractContext.getStringBuilder();
            body.append(this.message);

            for (this.t = this.thrown; this.t != null; this.t = this.t.getCause()) {
                body.append(System.lineSeparator());
                if (this.t != this.thrown) {
                    body.append("Caused by: ");
                }

                body.append(this.t.getClass().getName()).append(':').append(' ').append(this.t.getMessage());
                StackTraceElement[] stes = this.t.getStackTrace();
                if (stes != null && stes.length != 0) {
                    StackTraceElement[] arr$ = stes;
                    int len$ = stes.length;

                    for (int i$ = 0; i$ < len$; ++i$) {
                        StackTraceElement ste = arr$[i$];
                        body.append(System.lineSeparator()).append('-').append(' ');
                        body.append(ste.getClassName()).append(".").append(ste.getMethodName());
                        if (ste.isNativeMethod()) {
                            body.append("(Native Method)");
                        } else {
                            String fn = ste.getFileName();
                            int ln = ste.getLineNumber();
                            if (fn != null && ln > 0) {
                                body.append(':').append(ln);
                            } else if (fn == null) {
                                body.append("(Unknown Source)");
                            }
                        }
                    }
                }
            }
            this.t = null;
            return body.toString().getBytes("UTF8");
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Throwable getThrown() {
        return thrown;
    }

    public void setThrown(Throwable thrown) {
        this.thrown = thrown;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    @Override
    public String toString() {
        return "LogEventWrapper{" + "appName='" + appName + '\'' + ", sourceIp='" + sourceIp + '\'' + ", method='"
            + method + '\'' + ", lineNumber=" + lineNumber + ", loggerName='" + loggerName + '\'' + ", timeMillis="
            + timeMillis + ", level=" + level + ", threadName='" + threadName + '\'' + ", message='" + message + '\''
            + ", parameters=" + Arrays.toString(parameters) + ", thrown=" + thrown + ", t=" + t + '}';
    }
}
