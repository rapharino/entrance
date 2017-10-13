
package org.rapharino.entrance.core.common;

/**
 * Created By Rapharino on 11/10/2017 11:45 AM
 * 泛化回复
 */
public class Response extends API{

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * Error
     */
    private Throwable error;

    /**
     * 结果
     */
    private Object result;

    public boolean isError() {
        return error != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Response(String requsetId) {

        this.requestId = requsetId;
    }

    public Response(String requestId, final Object returnValue) {
        this.requestId = requestId;
        this.result = returnValue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Response{");
        sb.append("requestId='").append(requestId).append('\'');
        sb.append(", error=").append(error);
        sb.append(", result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}
