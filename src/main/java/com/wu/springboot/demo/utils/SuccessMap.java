package com.wu.springboot.demo.utils;

import java.util.HashMap;
import java.util.Map;

public class SuccessMap extends HashMap {

    public static Map<String, String> success(String msg){
        Map<String, String> successMap = new HashMap<>();
        successMap.put("success", "1");
        successMap.put("msg", msg);
        return successMap;
    }

    public static Map<String, String> failure(String msg){
        Map<String, String> successMap = new HashMap<>();
        successMap.put("success", "0");
        successMap.put("msg", msg);
        return successMap;
    }

}
