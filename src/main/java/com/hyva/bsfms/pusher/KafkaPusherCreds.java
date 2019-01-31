package com.hyva.bsfms.pusher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaPusherCreds {


    @Value("${spring.kafka.bootstrap-servers}")
    private String KAFKA_SERVER_URL;

    @Value("${spring.kafka.consumer.group-id}")
    private String KAFKA_GROUPID;

    @Value("${jsa.kafka.topic}")
    private String KAFKA_TOPIC;

    @Value("${pusher__domainame}")
    private static String PUSHER_SERVER_URL;

    @Value("${php_domainame}")
    private static String PHP_SERVER_URL;

    public String getKAFKA_SERVER_URL() {
        return KAFKA_SERVER_URL;
    }

    public void setKAFKA_SERVER_URL(String KAFKA_SERVER_URL) {
        this.KAFKA_SERVER_URL = KAFKA_SERVER_URL;
    }

    public String getKAFKA_GROUPID() {
        return KAFKA_GROUPID;
    }

    public void setKAFKA_GROUPID(String KAFKA_GROUPID) {
        this.KAFKA_GROUPID = KAFKA_GROUPID;
    }

    public String getKAFKA_TOPIC() {
        return KAFKA_TOPIC;
    }

    public void setKAFKA_TOPIC(String KAFKA_TOPIC) {
        this.KAFKA_TOPIC = KAFKA_TOPIC;
    }


    public static String getPusherServerUrl() {
        return PUSHER_SERVER_URL;
    }

    public static void setPusherServerUrl(String pusherServerUrl) {
        PUSHER_SERVER_URL = pusherServerUrl;
    }

    public static String getPhpServerUrl() {
        return PHP_SERVER_URL;
    }

    public static void setPhpServerUrl(String phpServerUrl) {
        PHP_SERVER_URL = phpServerUrl;
    }
}
