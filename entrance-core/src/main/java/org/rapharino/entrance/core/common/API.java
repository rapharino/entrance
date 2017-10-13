
package org.rapharino.entrance.core.common;

/**
 * Created By Rapharino on 12/10/2017 3:40 PM
 */
public class API {

    // 仅为 别名
    protected String name;

    protected String group;

    protected String version;

    protected Class value;

    protected String methodName;

    protected Class<?>[] parameterTypes;

    public String getName() {
        return name;
    }

    public API setName(String name) {
        this.name = name;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public API setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public API setVersion(String version) {
        this.version = version;
        return this;
    }

    public Class getValue() {
        return value;
    }

    public API setValue(Class value) {
        this.value = value;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public API setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public API setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    public API(Class interfaceClazz) {
        name = "";
        group = "";
        version = "v1";
        value = interfaceClazz;
    }
    public API() {
       this(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        API api = (API) o;

        if (group != null ? !group.equals(api.group) : api.group != null) return false;
        if (version != null ? !version.equals(api.version) : api.version != null) return false;
        if (value != null ? !value.equals(api.value) : api.value != null) return false;
        return methodName != null ? methodName.equals(api.methodName) : api.methodName == null;
    }

    @Override
    public int hashCode() {
        int result = group != null ? group.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (methodName != null ? methodName.hashCode() : 0);
        return result;
    }

    public API getAPI() {
        return this;
    }
}
