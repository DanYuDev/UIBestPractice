package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableRow;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coderlt on 2018/3/20.
 */

public class EditSpinnerText extends AppCompatEditText {
    private Context mContext;
    private PopupWindow popupWindow;
    private List<String> spinnerList;
    private ListView listView;
    private ArrayAdapter adapter;
    private Drawable rightDrawable;

    public EditSpinnerText(Context context){
        super(context,null);
    }

    public EditSpinnerText(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext = context;
        initPopupWindow();
    }

    public void setSpinnerList(List<String> spinnerList){
        this.spinnerList.addAll(spinnerList);
        adapter.notifyDataSetChanged();
    }

    private void initPopupWindow(){
        spinnerList = new ArrayList<>();
        listView=new ListView(mContext);
        adapter = new ArrayAdapter(mContext,R.layout.edit_spinner_item,spinnerList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditSpinnerText.this.setText(spinnerList.get(position));
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        popupWindow = new PopupWindow(
                listView,w-40, ViewGroup.LayoutParams.WRAP_CONTENT,false);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        setOnClickListener(new ViewGroup.OnClickListener(){
            @Override
            public void onClick(View v){
                Utils.showToast("PopupWindow:"+spinnerList);
                popupWindow.showAsDropDown(EditSpinnerText.this,10,0);
            }
        });
    }

}
