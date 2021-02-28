package com.goodjob.distribution_center.domain.pojo;

/**
 * 平台信息
 */
public class Platform {

    // 平台UUID
    private String uuid;

    // 平台名称
    private String name;

    // 平台下属机器
    private String[] servers;

    // TODO 下属机器状态描述 eg：cpu、内存、性能、存储。。。后续开发特定路由可能用到
    private String[] serverDescriptions;

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

    public String[] getServers() {
        return servers;
    }

    public void setServers(String[] servers) {
        this.servers = servers;
    }

    public String[] getServerDescriptions() {
        return serverDescriptions;
    }

    public void setServerDescriptions(String[] serverDescriptions) {
        this.serverDescriptions = serverDescriptions;
    }
}
