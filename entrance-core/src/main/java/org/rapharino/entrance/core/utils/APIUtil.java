
package org.rapharino.entrance.core.utils;

import java.lang.reflect.Method;

import org.rapharino.entrance.core.common.API;

/**
 * Created By Rapharino on 12/10/2017 11:19 AM
 */
public abstract class APIUtil {

    public static String getPrimaryKey(org.rapharino.entrance.core.annotation.API api) {
        return getPrimaryKey(toBean(api));
    }

    public static String getPrimaryKey(API api) {
        return api.getValue().getName();
    }

    public static API toBean(org.rapharino.entrance.core.annotation.API api) {
        return new API(api.value()).setName(api.name()).setGroup(api.group()).setVersion(api.version());
    }

    public static API method(API api, Method method){
        return api.setMethodName(method.getName())
                .setParameterTypes(method.getParameterTypes());

    }
}
