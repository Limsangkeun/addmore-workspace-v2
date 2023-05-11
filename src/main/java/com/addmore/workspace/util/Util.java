package com.addmore.workspace.util;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
public class Util {
    private static Gson gson = new Gson();

    public static Map<String, Object> fromJson(String data) {
        Map<String, Object> result_data = gson.fromJson(data, Map.class);
        return result_data;
    }
}
