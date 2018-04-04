package com.example.coderlt.uibestpractice.bean;

/**
 * Created by coderlt on 2018/4/3.
 */

/**
 * 房间基本信息
 */
public class Room {
    /**
     * 房间名称  8310
     */
    private String name;
    /**
     * 容量，可容纳人数
     */
    private int    volume;
    /**
     * 类型： 推拿房，按摩房，spa房
     */
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Room(String name, int volume, String type) {
        this.name = name;
        this.volume = volume;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", volume=" + volume +
                ", type='" + type + '\'' +
                '}';
    }
}
