package com.goodjob.distribution_center.worker;

/**
 * worker处理所需全部参数类
 *
 * 定义了worker中处理器所在位置
 * 处理器入参
 * 执行结果返回地址
 * 执行结果保存的数据库Id
 */
public class Handler {

    /**
     * 处理器方法签名
     */

    // 类的全限定名 eg:com.goodjob.distribution_center.worker.Handler
    private String className;

    // 方法名
    private String methodName;

    // 方法参数类型的全限定名 eg:java.lang.String
    private String[] paramTypes;

    // 方法参数
    private Object[] params;

    /**
     * 执行结果返回
     */

    // url
    private String taskUrl;

    // DB id
    private int taskId;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(String[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
