package com.nikshepav.jwtauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpConnectionConfiguration {

    @Value("${generic.client.max.total.connections}")
    private int maxTotalConnections;
    @Value("${generic.client.max.connections.per.route}")
    private int maxConnectionsPerRoute;
    @Value("${generic.client.idle.connections.timeout.ms}")
    private int idleConnectionsTimeout;
    @Value("${generic.client.connection.timeout.ms}")
    private int connectionTimeout;
    @Value("${generic.client.socket.timeout.ms}")
    private int socketTimeout;

    int getSocketTimeout() {
        return socketTimeout;
    }

    int getConnectionTimeout() {
        return connectionTimeout;
    }

    int getMaxTotalConnections() {
        return maxTotalConnections;
    }

    int getMaxConnectionsPerRoute() {
        return maxConnectionsPerRoute;
    }

    int getIdleConnectionsTimeout() {
        return idleConnectionsTimeout;
    }
}
