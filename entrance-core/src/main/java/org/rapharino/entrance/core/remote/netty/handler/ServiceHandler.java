
package org.rapharino.entrance.core.remote.netty.handler;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.rapharino.entrance.core.common.API;
import org.rapharino.entrance.core.common.Request;
import org.rapharino.entrance.core.common.Response;
import org.rapharino.entrance.core.remote.impl.AbstractProvider;
import org.rapharino.entrance.core.utils.APIUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

/**
 * Created By Rapharino on 12/10/2017 4:51 PM
 */
public class ServiceHandler extends SimpleChannelInboundHandler<Request> {

    private final AbstractProvider provider;

    private volatile Map<API, Object> services = new ConcurrentHashMap<>(8);

    public ServiceHandler(AbstractProvider provider) {
        super();
        this.provider = provider;
        for (Object service : provider.getDeclareService()) {
            org.rapharino.entrance.core.annotation.API api = service.getClass()
                    .getAnnotation(org.rapharino.entrance.core.annotation.API.class);
            for (Method method : service.getClass().getDeclaredMethods()) {
                API apibean = APIUtil.method(APIUtil.toBean(api), method);
                services.put(apibean, service);
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        Response response = new Response(request.getRequestId());
        try {
            Object result = handle(request);
            response.setResult(result);
        } catch (Throwable t) {
            response.setError(t);
        }

        ctx.writeAndFlush(response);
    }

    private Object handle(Request request) throws Throwable {

        Object service = services.get(request.getAPI());
        Class<?> serviceClass = service.getClass();

        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();



        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(service, parameters);
    }
}
