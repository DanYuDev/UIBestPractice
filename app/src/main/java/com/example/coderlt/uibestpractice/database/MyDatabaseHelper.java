package com.example.coderlt.uibestpractice.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coderlt.uibestpractice.utils.Utils;

/**
 * Created by coderlt on 2018/3/25.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_BILL = "create table Bill("
            +"id integer primary key autoincrement,"
            +"bill_icon integer,"  // bill_icon 就是对应的类型图标
            +"bill_name text,"
            +"bill_type integer,"  // 消费 or 收入
            +"bill_noteUrl text,"
            +"bill_noteText text,"
            +"bill_money real,"
            +"bill_date text)";
    /**
     * type 消费类型的 id
     * noteUrl 备注图片
     */
    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext = context;
    }

    /**
     * 调用 dbHelper.getWritableDatabase 之后，如果未创建数据库，则调用 onCreate 来创建数据库，并建表
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_BILL);
        Utils.showToast("Create succeeded");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("drop table if exists Bill");
        onCreate(db);
    }
}
