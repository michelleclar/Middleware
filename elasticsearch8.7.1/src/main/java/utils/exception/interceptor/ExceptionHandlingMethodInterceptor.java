package utils.exception.interceptor;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import utils.exception.inter.CatchException;
import utils.exception.inter.CatchExceptions;

import java.lang.reflect.Method;

/**
 * @program: Middleware
 * @description: 全局异常处理逻辑
 * @author: Mr.Carl
 **/

public class ExceptionHandlingMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        CatchException catchException = method.getAnnotation(CatchException.class);
        CatchExceptions catchExceptions = method.getAnnotation(CatchExceptions.class);
        if (catchException != null) {
            System.out.println("方法异常");
        } else if (catchExceptions != null) {
            System.out.println("类异常");
        }
        try {
            return methodProxy.invokeSuper(o, args);
        } catch (Exception e) {
            // 处理异常
        } finally {
            return null;
        }
    }


}
