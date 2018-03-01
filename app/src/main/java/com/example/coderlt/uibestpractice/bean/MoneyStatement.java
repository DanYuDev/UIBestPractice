package com.example.coderlt.uibestpractice.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by coderlt on 2018/3/1.
 */

public class MoneyStatement {
    private String name;
    private Date date;
    private float amount;
    private int outInType;
    private List<String> nameType = new ArrayList<>();
    // 每个name对应一个图表 icon icon的名字设定为 ic_name.jpg

    static class OutInType{
        static int OUT = 0;
        static int IN = 1;
    }
}
