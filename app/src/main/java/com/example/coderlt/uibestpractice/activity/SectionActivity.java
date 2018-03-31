package com.example.coderlt.uibestpractice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.adapter.SectionAdapter;
import com.example.coderlt.uibestpractice.bean.Employee;
import com.example.coderlt.uibestpractice.bean.Section;
import com.example.coderlt.uibestpractice.interfaces.MyOnClickListener;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.util.List;

import okhttp3.internal.Util;

public class SectionActivity extends AppCompatActivity {
    private static final String TAG = "SectionActivity" ;
    private Section section;
    private List<Employee> employeeList;
    private ListView sectionCycler;
    private SectionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        section =(Section) getIntent().getSerializableExtra("section");
        Utils.showToast("section name : "+section.getSectionName());
        employeeList = section.getEmployees();

        initView();
    }

    private void initView(){
        sectionCycler = findViewById(R.id.employee_list_view);
        adapter = new SectionAdapter(this,R.layout.employee_item,employeeList);
        sectionCycler.setAdapter(adapter);
    }
}
