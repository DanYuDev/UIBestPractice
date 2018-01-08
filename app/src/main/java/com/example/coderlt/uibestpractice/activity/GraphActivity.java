package com.example.coderlt.uibestpractice.activity;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.LineGraph;

public class GraphActivity extends AppCompatActivity {
    private PointF[] dataSet=new PointF[20];
    private LineGraph lineGraph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        for(int i=0;i<10;i++){
            dataSet[i]=new PointF();
            dataSet[i].x=(float) (Math.random()*100);
            dataSet[i].y=(float)(Math.random()*80);
        }

        lineGraph=findViewById(R.id.line_graph);
        lineGraph.setDataSet(dataSet,10);

    }
}
