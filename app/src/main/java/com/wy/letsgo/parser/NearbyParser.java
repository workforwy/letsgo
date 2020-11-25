package com.wy.letsgo.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.wy.letsgo.entity.UserEntity;

public class NearbyParser {
    public static ArrayList<UserEntity> parser(String jsonString) {
        ArrayList<UserEntity> list = new ArrayList<UserEntity>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String status = jsonObject.getString("status");
            if ("0".equals(status)) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectUser = jsonArray.getJSONObject(i);
                    String username = "", nickname = "", iconUrl = "";
                    username = jsonObjectUser.getString("username");
                    iconUrl = jsonObjectUser.getString("iconUrl");
                    UserEntity userEntity = new UserEntity();
                    userEntity.setUsername(username);
                    userEntity.setName(nickname);
                    userEntity.setIconUrl(iconUrl);
                    list.add(userEntity);
                }
            }
        } catch (Exception e) {
        }
        return list;
    }
}
