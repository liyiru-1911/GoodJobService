package com.goodjob.distribution_center.domain.pojo;

/**
 * 任务
 * 被分到该service上的任务实体类
 * 不在service数据库中体现，只在内存中缓存
 */
public class Job {

    // 任务UUID
    private String uuid;

    // 任务名
    private String name;

    // 任务描述
    private String description;

    // 责任人
    private String owner;

    // 所属平台UUID
    private String platformUuid;

    // 所属平台名称
    private String platformName;

    // 路由策略代号
    private String routingPolicyCode;

    // 路由策略名
    private String routingPolicyName;

    // 是否启用
    private String status;

    // 执行计划Cron
    private String cron;

    // 处理器 - 类的全限定名
    private String className;

    // 处理器 - 方法名
    private String methodName;

    // 处理器 - 方法参数类型的全限定名
    private String[] paramTypes;

    // 处理器 - 方法参数
    private Object[] params;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPlatformUuid() {
        return platformUuid;
    }

    public void setPlatformUuid(String platformUuid) {
        this.platformUuid = platformUuid;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getRoutingPolicyCode() {
        return routingPolicyCode;
    }

    public void setRoutingPolicyCode(String routingPolicyCode) {
        this.routingPolicyCode = routingPolicyCode;
    }

    public String getRoutingPolicyName() {
        return routingPolicyName;
    }

    public void setRoutingPolicyName(String routingPolicyName) {
        this.routingPolicyName = routingPolicyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

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
}
