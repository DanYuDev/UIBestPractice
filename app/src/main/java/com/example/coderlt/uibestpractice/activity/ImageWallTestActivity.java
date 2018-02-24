package com.example.coderlt.uibestpractice.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.adapter.ImageWallAdapter;
import com.example.coderlt.uibestpractice.bean.ImageInfo;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ImageWallTestActivity extends AppCompatActivity {
    private static final String TAG = "ImageWallTestActivity";
    private ImageWallAdapter adapter = null;
    private RecyclerView cycler;
    private List<ImageInfo> imageInfos = new ArrayList<>();
    private int selectedCount = 0;
    private TextView countTv;
    private int statusBarHeight =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_wall_test);

        cycler = findViewById(R.id.test_recycler);
        countTv = findViewById(R.id.selected_count_tv);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        adapter = new ImageWallAdapter(this,R.layout.image_wall_item,imageInfos);

        // 设置选择图片监听
        adapter.setOnImageSelectedListener(new ImageWallAdapter.OnImageSelectedListener() {
            @Override
            public void selected(ImageView v) {
                 countTv.setText(""+(++selectedCount));
            }

            @Override
            public void unSelected(ImageView v) {
                countTv.setText(""+(--selectedCount));
            }

            @Override
            public void preview(ImageView imageView,String imagePath){
                Utils.showToast("Preview image."+imagePath);
                int[] location = new int[2];
                imageView.getLocationOnScreen(location);
                Log.d(TAG,"origin imageView x and y : "+location[0]+" ,"+location[1]);
                // 通过 shared element transition 进去 ImageViewPager.
            }
        });
        cycler.setLayoutManager(layoutManager);
        cycler.setAdapter(adapter);
        getImageList();
    }

    private void getImageList(){
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,null,null,null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.DISPLAY_NAME));
                String path = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.DATA));
                imageInfos.add(new ImageInfo(name,path));
            }
        }
        cursor.close();
    }
}
