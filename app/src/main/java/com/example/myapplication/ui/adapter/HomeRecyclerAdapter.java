package com.example.myapplication.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.HomeNewsBean;
import com.example.myapplication.ui.fragment.HomeFragment;
import com.example.myapplication.ui.widget.RoundAngleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 分类新闻列表适配器
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<HomeNewsBean> homeNewsBeans; //新闻列表数据
    private ImageLoader mImageLoader; //图片加载组件
    private final DisplayImageOptions options; //ImageLoader配置变量
    private Resources resources;

    public void setHomeNewsBeans(List<HomeNewsBean> homeNewsBeans) {
        this.homeNewsBeans = homeNewsBeans;
    }

    public HomeRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        //初始化图片加载组件
        //图片保存在内存中
        // 保存在内存卡中
        // 加载过程图片
         options = new DisplayImageOptions.Builder()
                .cacheInMemory(true) //图片保存在内存中
                .cacheOnDisk(true) //保存在内存卡中
                .showImageOnLoading(R.drawable.defaultbg_h) //加载过程图片
                .build();
        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_home_news_layout,parent,false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeNewsBean homeNewsBean = homeNewsBeans.get(position);
        if (holder instanceof ItemViewHolder){
            String mask = homeNewsBean.getMask();
            mImageLoader.displayImage(homeNewsBeans.get(position).getImgurl(),((ItemViewHolder) holder).item_news_tv_img);
            mImageLoader.displayImage(homeNewsBeans.get(position).getAuthoImg(),((ItemViewHolder) holder).item_news_img_icon);
            ((ItemViewHolder) holder).item_news_tv_title.setText(homeNewsBeans.get(position).getTitle());
            ((ItemViewHolder) holder).item_news_tv_name.setText(homeNewsBeans.get(position).getAuthorName());
            ((ItemViewHolder) holder).item_news_tv_time.setText(homeNewsBeans.get(position).getDatetime());
            ((ItemViewHolder) holder).item_news_tv_type.setText(homeNewsBeans.get(position).getMask());

            int index = 0;
            for (int i = 0; i < mask.length(); i++) {
                if (HomeFragment.masks[i].equals(mask)) {
                    index = i;
                    break;
                }
            }
            ((ItemViewHolder) holder).item_news_tv_type.setTextColor(mContext.getResources().getColor(HomeFragment.mask_colors[index]));
            ((ItemViewHolder) holder).item_news_tv_arrow.setBackgroundColor(mContext.getResources().getColor(HomeFragment.mask_colors[index]));
        }
    }

    @Override
    public int getItemCount() {
        return homeNewsBeans == null ? 0 : homeNewsBeans.size();
    }

    //自定义ViewHolder
    private class  ItemViewHolder extends RecyclerView.ViewHolder{
        private RoundAngleImageView item_news_img_icon;
        private TextView item_news_tv_name;
        private TextView item_news_tv_time;
        private TextView item_news_tv_type;
        private TextView item_news_tv_title;
        private ImageView item_news_tv_img;
        private TextView item_news_tv_arrow;
        public ItemViewHolder(View itemView) {
            super(itemView);
            item_news_img_icon = itemView.findViewById(R.id.item_news_img_icon);
            item_news_tv_name = itemView.findViewById(R.id.item_news_tv_name);
            item_news_tv_time = itemView.findViewById(R.id.item_news_tv_time);
            item_news_tv_type = itemView.findViewById(R.id.item_news_tv_type);
            item_news_tv_title = itemView.findViewById(R.id.item_news_tv_title);
            item_news_tv_img = itemView.findViewById(R.id.item_news_tv_img);
            item_news_tv_arrow = itemView.findViewById(R.id.item_news_tv_arrow);
        }
    }
}
