package com.goodjob.distribution_center.worker;

import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liyiru-1911
 * @since 2021/2/28
 */
@Service
public class WorkerService {

    @Async("workerTaskExecutor")
    public void findHandlerAndRunThenSendResultBack(Handler handler) {
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.println("worker收到的handler：" + handler.toString());
            result = findHandlerAndRun(handler);
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", "未知异常，请联系管理员");
        }

        sendResultBack(result, handler);
    }

    private Map<String, Object> findHandlerAndRun(Handler handler) {
        Map<String, Object> result = new HashMap<>();

        Class<?>[] paramTypes = new Class[handler.getParamTypes().length];
        Object[] params = new Object[handler.getParams().length];
        try {
            for (int i = 0; i < paramTypes.length; i++) {
                paramTypes[i] = Class.forName(handler.getParamTypes()[i]);
                if (handler.getParams()[i] != null) {
                    params[i] = JSONObject.parseObject(JSONObject.toJSONBytes(handler.getParams()[i]), paramTypes[i]);
                }
            }
        } catch (ClassNotFoundException e) {
            result.put("success", false);
            result.put("msg", "参数类型错误，请检查是否为java.lang.String这类的Java类型");
            return result;
        }

        Class<?> clazz;
        try {
            clazz = Class.forName(handler.getClassName());
        } catch (ClassNotFoundException e) {
            result.put("success", false);
            result.put("msg", "找不到处理器类，请检查处理器类的全量限定名");
            return result;
        }

        Object invokeResult;
        try {
            Object bean = SpringUtils.getBean(clazz);
            assert clazz != null;
            Method method = ReflectionUtils.findMethod(clazz, handler.getMethodName(), paramTypes);
            assert method != null;
            invokeResult = ReflectionUtils.invokeMethod(method, bean, params);
        } catch (NullPointerException e) {
            result.put("success", false);
            result.put("msg", "找不到处理方法，请检查函数签名是否正确");
            return result;
        }

        assert invokeResult != null;
        if (!result.getClass().equals(invokeResult.getClass())) {
            result.put("success", false);
            result.put("msg", "处理器返回结果未符合要求");
            return result;
        } else {
            result = (Map<String, Object>) invokeResult;
        }
        return result;
    }

    private void sendResultBack(Map<String, Object> result, Handler handler) {
        // TODO 返回结果，返回地址url和数据库Id在handler中
        System.out.println("worker执行是否成功：" + String.valueOf(result.get("success")));
        System.out.println("worker执行结果信息：" + String.valueOf(result.get("msg")));
    }
}
