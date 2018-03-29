package com.example.coderlt.uibestpractice.View;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.bean.ExpandMenuItem;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * app:itemWidth
 * app:itemTextSize
 * 如何控制反向增加
 * Created by coderlt on 2018/3/28.
 */

public class ExpandMenu extends RelativeLayout {
    private static final String TAG = "ExpandMenu";
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(2000);
    private Context mContext;
    private boolean isExpanded;
    private List<ExpandMenuItem> menus;
    private OnItemClickListener listener;
    private ImageView defaultIv;

    //图标的宽高，不包括字体，字体自适应
    private int itemWidth;
    private int itemMargin;
    private int marginBottom;
    private int marginRight;

    // postion 代表子菜单的位置
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public ExpandMenu(Context context){
        super(context,null);
    }

    public ExpandMenu(Context context, AttributeSet attrs){
        super(context,attrs);
        initStatus(context,attrs);
    }

    private void initStatus(Context context,AttributeSet attrs){
        isExpanded = false;
        menus = new ArrayList<>();
        mContext =context;

        if(attrs!=null){
            TypedArray typedArray = null;
            typedArray = mContext.obtainStyledAttributes(attrs,R.styleable.ExpandMenu);
            itemWidth = typedArray.getInteger(R.styleable.ExpandMenu_itemWidth,0);
            itemMargin = typedArray.getInteger(R.styleable.ExpandMenu_itemMargin,20);
            marginBottom = typedArray.getInteger(R.styleable.ExpandMenu_marginBottom,0);
            marginRight = typedArray.getInteger(R.styleable.ExpandMenu_marginRight,0);
            if(typedArray!=null)
                typedArray.recycle();
        }
    }

    public void setMenus(final List menus,int defaultIconId){
        this.menus = menus;
        ViewGroup.LayoutParams mLayoutParams = getLayoutParams();
        defaultIv = new ImageView(mContext);
        defaultIv.setImageResource(defaultIconId);
        final LayoutParams defaultIvLp = new LayoutParams(itemWidth,itemWidth);
        defaultIvLp.setMargins(0,0,marginRight,marginBottom);
        addView(defaultIv,defaultIvLp);

        setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 未扩展
                if (!isExpanded){
                    ExpandMenu.this.removeAllViews();

                    LinearLayout previousItem = null;
                    for(int i=0;i<menus.size();i++) {
                        ExpandMenuItem expandMenuItem = (ExpandMenuItem) menus.get(i);

                        LinearLayout itemLayout = new LinearLayout(mContext);

                        //-------------- 添加 itemLayout -----------------------------
                        TextView itemTv = new TextView(mContext);
                        itemTv.setText(expandMenuItem.getTitle());
                        itemTv.setHeight(itemWidth);
                        itemTv.setGravity(Gravity.CENTER);

                        ImageView itemIv = new ImageView(mContext);
                        LinearLayout.LayoutParams ivLp = new LinearLayout.LayoutParams(itemWidth,itemWidth);
                        itemIv.setImageResource(expandMenuItem.getIconId());

                        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
                        itemLayout.addView(itemTv);
                        itemLayout.addView(itemIv,ivLp);

                        LayoutParams itemLp = new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        itemLp.setMargins(0,0,marginRight,itemMargin);
                        itemLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                        //itemLp.setMargins(0,0,20,itemMargin);
                        if(previousItem!=null){
                            itemLp.addRule(RelativeLayout.ABOVE,previousItem.getId());
                            Log.d(TAG,"previous id is :"+previousItem.getId());
                        }else{
                            // TODO 这一句非常关键，我一开始就是因为少加了这一句，导致一直只能看到一个菜单
                            // 因为布局默认是将控件放在左上角的
                            itemLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

                            itemLp.setMargins(0,0,marginRight,marginBottom);
                        }

                        ExpandMenu.this.addView(itemLayout,itemLp);
                        itemLayout.setId(generateViewId());
                        previousItem = itemLayout;
                        Log.d(TAG,"Add a item which has a id :"+itemLayout.getId());

                        final int tempPosition = i;

                        itemLayout.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v){
                                listener.onItemClick(tempPosition);
                                ExpandMenu.this.removeAllViews();
                                ExpandMenu.this.addView(defaultIv,defaultIvLp);
                                isExpanded = false;
                                setMenuLayoutParams();
                            }
                        });

                        ValueAnimator animatorX = ObjectAnimator.ofFloat(itemLayout,"scaleX",0,1);
                        animatorX.setDuration(300);

                        ValueAnimator animatorY = ObjectAnimator.ofFloat(itemLayout,"scaleY",0,1);
                        animatorY.setDuration(300);

                        animatorX.start();
                        animatorY.start();
                    }
                    isExpanded = true;
                    setMenuLayoutParams();
                }
                // 扩展
                else{
                    ExpandMenu.this.removeAllViews();
                    ExpandMenu.this.addView(defaultIv,defaultIvLp);
                    isExpanded = false;
                    setMenuLayoutParams();
                }
            }
        });
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    /**
     * 很明显，这里利用 getLayoutParams 来获取菜单的原始布局参数
     * 如果是new出来的话，会丢失部分参数
     * 这种做法适合只改变部分参数，其余参数不变的情形
     */
    public void setMenuLayoutParams(){
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if(isExpanded){
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            setBackgroundResource(R.color.half_transparent);
        }else{
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            setBackgroundResource(android.R.color.transparent);
        }
        requestLayout();
    }

    /**
     * Generate a value suitable for use in {@link #setId(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

}
