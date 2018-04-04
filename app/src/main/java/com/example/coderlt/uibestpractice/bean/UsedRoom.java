package com.example.coderlt.uibestpractice.bean;

/**
 * Created by coderlt on 2018/4/3.
 */

import com.example.coderlt.uibestpractice.adapter.RoomCyclerAdapter;

/**
 * 已投入使用的房间信息
 * 有人使用 || 脏房
 */
public class UsedRoom {
    /**
     * 房间基本信息
     */
    private Room room;

    /**
     * 已有人数
     */
    private int persons;

    /**
     * 卫生状况
     */
    private boolean isClean;

    /***
     * 项目信息
     */
    private TherapyProject project;

    private RoomCyclerAdapter.ITEM_TYPE item_type;

    public RoomCyclerAdapter.ITEM_TYPE getItem_type() {
        return item_type;
    }

    public void setItem_type(RoomCyclerAdapter.ITEM_TYPE item_type) {
        this.item_type = item_type;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }

    public TherapyProject getProject() {
        return project;
    }

    public void setProject(TherapyProject project) {
        this.project = project;
    }
}
