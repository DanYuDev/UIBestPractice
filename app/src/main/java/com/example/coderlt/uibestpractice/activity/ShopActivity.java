package com.example.coderlt.uibestpractice.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.adapter.OrganizationAdapter;
import com.example.coderlt.uibestpractice.bean.Organization;
import com.example.coderlt.uibestpractice.bean.Section;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.JsonUtils;
import com.example.coderlt.uibestpractice.utils.Utils;
import com.kcode.autoscrollviewpager.view.AutoScrollViewPager;
import com.kcode.autoscrollviewpager.view.BaseViewPagerAdapter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "ShopActivity";
    private TextView titleTv;
    private View  titleLayout;
    private OkHttpClient okHttpClient;
    private final MediaType MEDIA_TYPE_JSON = MediaType.parse("json/application;charset=utf-8");
    private Map para;
    private String paraJson;
    private AutoScrollViewPager mViewPager;
    private Organization organization;
    private ListView organizationCycler;
    private OrganizationAdapter adapter;
    private List<Section> sections;

    private String[] paths =
            {
                    "http://n4-q.mafengwo.net/s5/M00/DC/7E/wKgB3FGLd6yAOv6fAAFa2fIseJI42.jpeg?imageMogr2%2Fthumbnail%2F%21690x370r%2Fgravity%2FCenter%2Fcrop%2F%21690x370%2Fquality%2F100",
                    "http://www.ganzixinwen.com/uploads/file/2016/0918/20160918045431211.gif",
                    "https://bbs-fd.zol-img.com.cn/t_s1200x5000/g4/M09/06/0E/Cg-4WlIuZhOIZ-23ABLQ4R85hhgAALaywKA1AYAEtD5099.jpg",
                    "http://hbztw.h-cdn.co/assets/17/07/980x490/landscape-1487135493-gettyimages-140012002-1.jpg",
                    "http://i2.sinaimg.cn/lx/news/p/2009/1203/U2075P8T1D942775F917DT20091203125913.jpg",
                    "https://d3oj8nq9p0q26f.cloudfront.net/blog/assets/images/posts/coding-f33f4f67d9aed71fdfb01b58094c13a6.jpeg",
                    "https://i1.hdslb.com/bfs/archive/cecd151f5ff107f388f450170f34ef00c5428604.jpg",
                    "http://www.sinaimg.cn/dy/slidenews/4_img/2016_15/704_1892857_831486.jpg"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initOrganization();
        initViews();
        requestShopData("123");
    }

    private void initOrganization(){
        organization = new Organization();
        JsonUtils.DealSectionsInfo(getResources().getString(R.string.organization_json),organization);
        Log.d(TAG,organization.toString());
        organizationCycler = findViewById(R.id.organization_listview);
        sections = organization.getSectionList();
        adapter = new OrganizationAdapter(this,R.layout.section_item,sections);
        adapter.setOnItemClickedListener(new OrganizationAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(ShopActivity.this,SectionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("section",sections.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        organizationCycler.setAdapter(adapter);
    }

    private void initViews(){
        titleTv = findViewById(R.id.title_tv);
        titleLayout = findViewById(R.id.title_layout);
        mViewPager = (AutoScrollViewPager) findViewById(R.id.shop_banner);

        titleLayout.setBackgroundColor(Color.parseColor("#88888888"));
        BaseViewPagerAdapter<String> adapter = new BaseViewPagerAdapter<String>(this,listener) {
            @Override
            public void loadImage(ImageView view, int position, String url) {
                Glide.with(ShopActivity.this).load(url).into(view);
            }

            @Override
            public void setSubTitle(TextView textView, int position, String s) {
                //textView.setText(s);
            }
        };
        mViewPager.setAdapter(adapter);

        adapter.add(Arrays.asList(paths));
    }

    private BaseViewPagerAdapter.OnAutoViewPagerItemClickListener listener =
            new BaseViewPagerAdapter.OnAutoViewPagerItemClickListener<String>() {
        @Override
        public void onItemClick(int position, String url) {
            Utils.showToast(url);
        }
    };

    @Override
    public void onClick(View v){
    }

    /**
     * 根据店铺ID请求店铺信息
     */
    private void requestShopData(String shopId){
        para = new HashMap<String,String>();
        para.put("Name","Yudan");
        para.put("Age","27");
        para.put("degree","collegea");
        paraJson = JSONObject.toJSONString(para);
        Log.d(TAG,"Json String : "+paraJson);
        okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(Constant.SHOP_URL)
                .post(RequestBody.create(MEDIA_TYPE_JSON,paraJson.toString()))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Utils.showToast("Cannot connect the serve.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                //titleTv.setText(responseText);
                Log.d(TAG,responseText);
            }
        });
    }
}
