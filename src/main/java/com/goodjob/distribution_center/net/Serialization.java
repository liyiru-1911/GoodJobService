package com.goodjob.distribution_center.net;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * 序列化方法
 */
@Component
public class Serialization {


    public JSONObject objectToJson(Object object) {
        return (JSONObject) JSONObject.toJSON(object);
    }
//
//    public <T> T JsonToObject(JSONObject jsonObject) {
//        return JSONObject.toJavaObject(jsonObject, T);
//    }
}
