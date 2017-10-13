
package org.rapharino.entrance.core.common;

import java.util.Arrays;

/**
 * Created By Rapharino on 11/10/2017 11:41 AM
 * 泛化请求
 */
public class Request extends API{

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 参数
     */
    private Object[] parameters;


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Request{");
        sb.append("requestId='").append(requestId).append('\'');
        sb.append(", methodName='").append(methodName).append('\'');
        sb.append(", parameterTypes=").append(Arrays.toString(parameterTypes));
        sb.append(", parameters=").append(Arrays.toString(parameters));
        sb.append(", name='").append(name).append('\'');
        sb.append(", group='").append(group).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }

    public Request(API api) {
        super(api.value);
        this.setGroup(api.group)
                .setValue(api.value)
                .setName(api.name)
                .setVersion(api.version)
                .setMethodName(api.methodName)
                .setParameterTypes(api.parameterTypes);

    }
}
