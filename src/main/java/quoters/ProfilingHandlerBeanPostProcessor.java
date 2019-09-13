package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller,new ObjectName("profiling","name","controller"));
    }

    private ProfilingController controller = new ProfilingController();
  Map<String, Class> map = new HashMap<String, Class>();

  public Object postProcessBeforeInitialization(Object bean, String s) throws BeansException {
    Class<?> beanClass = bean.getClass();
    if (beanClass.isAnnotationPresent(Profiling.class)) {
      map.put(s, beanClass);
    }

    return bean;
  }

  public Object postProcessAfterInitialization(final Object bean, String s) throws BeansException {
    Class beanClass = map.get(s);
    if (beanClass != null) {
      return Proxy.newProxyInstance(
          beanClass.getClassLoader(),
          beanClass.getInterfaces(),
          new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
              if (controller.isEnabled()) {
                System.out.println("Prolilez !");
                long before = System.nanoTime();
                Object retVal = method.invoke(bean, args);
                long after = System.nanoTime();
                System.out.println("REZULTAT !!!" + (after - before));
                System.out.println("Gata am profilat !");
                return retVal;
              } else {
                return method.invoke(bean, args);
              }
            }
          });
    }
    return bean;
  }
}
