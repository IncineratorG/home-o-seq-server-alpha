package com.touristskaya.homeoseq.common.service;

public interface Service {
    ServiceActions getActions();
    void startService();
    void stopService();
}
