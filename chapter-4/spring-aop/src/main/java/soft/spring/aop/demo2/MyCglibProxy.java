package soft.spring.aop.demo2;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author : zhayh
 * @date : 2020-2-11 10:48
 * @description :
 */
public class MyCglibProxy implements MethodInterceptor {
    private ProductDao productDao;
    public MyCglibProxy(ProductDao productDao) {
        this.productDao = productDao;
    }
    public Object createProxy() {
        // 1. 创建核心类
        Enhancer enhancer = new Enhancer();
        // 2. 设置父类
        enhancer.setSuperclass(productDao.getClass());
        // 3. 设置回调
        enhancer.setCallback(this);
        // 4. 生成代理
        return enhancer.create();
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args,
                            MethodProxy methodProxy) throws Throwable {
        if ("save".equals(method.getName())) {
            System.out.println("权限校验......");
            return methodProxy.invokeSuper(proxy, args);
        }
        return methodProxy.invokeSuper(proxy, args);
    }
}
