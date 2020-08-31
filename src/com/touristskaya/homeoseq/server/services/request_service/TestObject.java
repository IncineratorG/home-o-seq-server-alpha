package com.touristskaya.homeoseq.server.services.request_service;

import java.util.List;

public class TestObject {
    public int a;
    public String s;
    public List<String> list;

    TestObject(int a, String s, List<String> list) {
        this.a = a;
        this.s = s;
        this.list = list;
    }
}
