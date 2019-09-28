package com.wei.mvcframework.servlet;

import com.wei.mvcframework.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @author wei
 * @date 2019/7/3-22:04
 */
public class WeiDispatchServlet extends HttpServlet    {

    private Properties properties=new Properties();

    private List<String> classNames=new ArrayList<>();

    private Map<String,Object> ioc=new HashMap<>();

    private Map<String,Method> handlerMapping=new HashMap<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //任务调度和派遣
        try {
            doDispatch(req,resp);
        }catch (Exception e){
            e.printStackTrace();
            resp.getWriter().write("500"+Arrays.toString(e.getStackTrace()));
        }

    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        Iterator<Map.Entry<String, Method>> iterator = handlerMapping.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry=iterator.next();
            entry.getKey();
            entry.getValue();
        }

        //1. 加载配置文件 spring 以do开头的都是干活的小弟
        doLoadConfig(config.getInitParameter("contextConfigLocation"));//读取web配置
        //2. 扫描所有的相关的类
        doScanner(properties.getProperty("scanPackage"));//获取配置的包
        //3 实例化所有的相关地方class  并且放入IOC容器
        doInstacne();
        //4. 依赖注入 把需要赋值的字段自动赋值
        doAutowired();
        //spring结束 开始springmvc
        //5. 初始化HandlerMapping 把controller中的url和Method 一对一关联
        initHandlerMapping();

        System.out.println("wei Spring mvc success");
    }

    /**
     * 初始化url
     */
    private void initHandlerMapping() {
        if (ioc.isEmpty()){return;}
        for (Map.Entry<String,Object> entry:ioc.entrySet()) {
            Class<?> clazz=entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(WeiController.class)){continue;}
            String baseUrl="";
            if (clazz.isAnnotationPresent(WeiRequestMapping.class)){
                WeiRequestMapping requestMapping = clazz.getAnnotation(WeiRequestMapping.class);
                baseUrl=requestMapping.value();
            }
            Method[] methods=clazz.getMethods();
            for (Method method:methods) {
                if (!method.isAnnotationPresent(WeiRequestMapping.class)){return;}
                WeiRequestMapping requestMapping=method.getAnnotation(WeiRequestMapping.class);
                String url=requestMapping.value();
                baseUrl=(baseUrl+"/"+url).replaceAll("/+","/");
                handlerMapping.put(baseUrl,method);
                System.out.println("mapped:"+baseUrl+","+method);
            }
        }
    }

    /**
     * 依赖注入
     */
    private void doAutowired() {
        if (ioc.isEmpty()){return;}
        for (Map.Entry<String,Object> entry:ioc.entrySet()) {
                //不管你是不是private 只要你加了注解
                Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            for (Field field:declaredFields) {
                if (!field.isAnnotationPresent(WeiAutowired.class)){return;}
                WeiAutowired autowired=field.getAnnotation(WeiAutowired.class);
                String beanName=autowired.value();
                if ("".equals(beanName)){
                    beanName=field.getType().getName();
                }
                //不管你是不是愿意 只要你加了注解
                try {
                    field.setAccessible(true);
                    field.set(entry.getValue(),ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }
            }

        }
    }

    /**
     * 创建实例
     */
    private void doInstacne() {
        if (classNames.isEmpty()){return;}
        try {
            for (String className : classNames) {
                Class<?> clazz=Class.forName(className);
                //只有加了注解的类才能实例化
                if (clazz.isAnnotationPresent(WeiController.class)){
                    Object instance=clazz.newInstance();
                    //IOC容器中的Bean 都有一个唯一的ID beanName
                    //beanName默认采用的类名首字母小写
                    //如果采用自定义beanName 优先采用自定义的值
                    //用接口的全名作为key 用实现类的实例作为值
                    String beanName=lowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName,instance);
                }else if(clazz.isAnnotationPresent(WeiService.class)){
                   WeiService weiService = clazz.getAnnotation(WeiService.class);
                   String beanName=weiService.value();
                   if ("".equals(beanName.trim())){
                       beanName=lowerFirstCase(clazz.getSimpleName());
                   }
                   Object instance=clazz.newInstance();
                   ioc.put(beanName,instance);
                   //4 投机取巧 用接口的全名作为key 用实现类的实例作为值
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> inte:interfaces) {
                        ioc.put(inte.getName(),instance);
                    }
                }else{

                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 类名开头小写
     * @param simpleName
     * @return
     */
    private String lowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0]+=32;
        return  String.valueOf(chars);
    }

    /**
     * 扫包
     * @param property
     */
    private void doScanner(String property) {
        URL url= this.getClass().getClassLoader().getResource("/"+property.replaceAll("\\.","/"));
        File classesDir=new File(url.getFile());
        for (File file:classesDir.listFiles()) {
            if (file.isDirectory()){
                doScanner(property+"."+file.getName());
            }else{
                String className = (property+"."+file.getName().replace(".class",""));
                classNames.add(className);
            }
        }
    }

    /**
     * 加载配置文件
     * @param initParameter
     */
    private void doLoadConfig(String initParameter) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(initParameter);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        String url = req.getRequestURI();//绝对路径
        String contextPath=req.getContextPath();//相对路径
        url=url.replace(contextPath,"").replaceAll("/+","/");
        if (!this.handlerMapping.containsKey(url)){
            resp.getWriter().write("404 Not Found!!!");
            return;
        }
        Method method=this.handlerMapping.get(url);
        Map<String,String[]> params=req.getParameterMap();
        System.out.println(method);
        Class<?>[] parameterTypes = method.getParameterTypes();//获取参数类型
        for (Class<?> cla:parameterTypes) {
            System.out.println(cla.getName()+"==========");
            if(!cla.isAnnotationPresent(WeiRequestParam.class)){continue;};
            WeiRequestParam weiRequestParam=cla.getAnnotation(WeiRequestParam.class);
            String paramName=weiRequestParam.value();
            String[] param =params.get(paramName);
            System.out.println(Arrays.toString(param));
        }
        String beanName=lowerFirstCase( method.getDeclaringClass().getSimpleName());
        System.out.println(beanName);


        Annotation[][] parameterAnnotations = method.getParameterAnnotations(); //获取参数的注解 可能有多个
        if (parameterAnnotations == null || parameterAnnotations.length == 0) { return;}//注解不能为null
        int i = 0;
        String name="";
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof WeiRequestParam) {
                    WeiRequestParam param = (WeiRequestParam) annotation;
                    name= param.value();//取出注解值 去request请求参数中获取
                }
            }
        }
        try {
            method.invoke(ioc.get(beanName),req,resp,params.get(name)[0]);//请求参数可能有多个 默认取第一个
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
