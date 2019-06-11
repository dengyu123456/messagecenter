package com.zhkj.nettyserver.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
/*import com.google.gson.Gson;*/

/**
 * 解析消息
 * 将前台发过来的消息解析成Mage
 * 后台发送消息到前台转成json字符串
 */

public class Mage {

    private static ObjectMapper gson = new ObjectMapper();
    /*private static Gson gson = new Gson();*/

    /**
     * 那个聊天室
     */
    private String table;
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 所发送的消息
     */
    private String message;

    /**
     * 将json字符串转成Mage
     * @param message
     * @return
     * @throws Exception
     */
    public static Mage strJson2Mage(String message) throws Exception{
        return Strings.isNullOrEmpty(message) ? null : gson.readValue(message, Mage.class);
    }

    /**
     * 将Mage转成json字符串
     * @return
     * @throws Exception
     */
    public String toJson() throws Exception{
        return gson.writeValueAsString(this);
    }

    public Mage setTableId(String table) {
        this.setTable(table);
        return this;
    }

    public static ObjectMapper getGson() {
        return gson;
    }

    public static void setGson(ObjectMapper gson) {
        Mage.gson = gson;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
