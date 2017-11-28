package com.example.myapplication.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cwidgetutils.AutoGallery;
import com.example.cwidgetutils.FlowIndicator;
import com.example.cwidgetutils.PullToRefreshListView;
import com.example.cwidgetutils.helper.BaseAdapterHelper;
import com.example.cwidgetutils.helper.QuickAdapter;
import com.example.myapplication.R;
import com.example.myapplication.common.Config;
import com.example.myapplication.common.DefineView;
import com.example.myapplication.entity.AdHeadBean;
import com.example.myapplication.entity.CategoriesBean;
import com.example.myapplication.entity.HomeNewsBean;
import com.example.myapplication.manager.HomeNewsDataManager;
import com.example.myapplication.manager.HeadDataManager;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.ui.fragment.base.BaseFragment;
import com.example.myapplication.util.OkHttpManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.Request;

/**
 * "全部"新闻
 */

public class HomeFragment extends BaseFragment implements DefineView {
    private View mView;

    private static final String KEY = "EXTRA";
    private CategoriesBean categoriesBean;
    //首页新闻列表信息
    private List<HomeNewsBean> homeNewsBeans;
    //首页广告信息
    private List<AdHeadBean> adHeadBeans;
    private QuickAdapter<HomeNewsBean> quickAdapter;
    private PullToRefreshListView home_list;
    //分类标签颜色及名字
    public static String[] masks;
    public static int[] mask_colors;

    private FrameLayout home_fragment;
    private LinearLayout loading, error, empty;
    private View headView;
    private LayoutInflater mInflate;

    //初始化图片UIL图片加载组件
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;


    //轮播图
    private int gallerySelectedPosition = 0; //Gallery索引，表示当前在第几页
    private int circleSelectedPosition = 0; //指示器圆圈数量

    //指示器
    private AutoGallery headline_image_gallery;
    private FlowIndicator headline_circle_indicator;

    public static HomeFragment newsInstance(CategoriesBean extra) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, extra);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            categoriesBean = (CategoriesBean) bundle.getSerializable(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.home_fragment_layout, container, false);
            mInflate = LayoutInflater.from(getActivity());
            headView = mInflate.inflate(R.layout.gallery_indicator_layout,null);
            initView();
            initValidata();
            initListener();
        }
        return mView;
    }

    @Override
    public void initView() {
        home_list = mView.findViewById(R.id.home_list);
        home_list.addHeaderView(headView);
        home_fragment = mView.findViewById(R.id.home_fragment);
        loading = mView.findViewById(R.id.loading);
        error = mView.findViewById(R.id.error);
        empty = mView.findViewById(R.id.empty);

        //获取 FlowIndicator和AutoGallery控件
        headline_image_gallery = headView.findViewById(R.id.headline_image_gallery);
        headline_circle_indicator = headView.findViewById(R.id.headline_circle_indicator);
    }

    @Override
    public void initValidata() {
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.defaultbg)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        masks = new String[]{"业界", "人工智能", "智能驾驶",
                "AI+", "Fintech", "未来医疗", "网络安全", "AR/VR",
                "机器人", "开发者", "智能硬件", "物联网"};
        mask_colors = new int[]{R.color.mask_tags_1, R.color.mask_tags_2, R.color.mask_tags_3, R.color.mask_tags_4,
                R.color.mask_tags_5, R.color.mask_tags_6, R.color.mask_tags_7, R.color.mask_tags_8,
                R.color.mask_tags_9, R.color.mask_tags_10, R.color.mask_tags_11, R.color.mask_tags_12};

        home_list.setVisibility(View.GONE);
        home_fragment.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);

        //异步加载首页数据
        OkHttpManager.getAsync(categoriesBean.getHref(), new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailue(Request request, IOException e) {
                Toast.makeText(getContext(), "首页新闻加载失败...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void requestSucess(String result) {
                Document document = Jsoup.parse(result, Config.CRAWLER_URL);
                adHeadBeans = new HeadDataManager().getHeadBeans(document);
                homeNewsBeans = new HomeNewsDataManager().getHomeNews(document);
                if (adHeadBeans != null && homeNewsBeans != null){
                    home_list.setVisibility(View.VISIBLE);
                    home_fragment.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                    bindData();
                }else {
                    home_list.setVisibility(View.GONE);
                    home_fragment.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void initListener() {

    }


    /**
     * 绑定首页数据，更改首页文章类型颜色、加载不同情况页面做出不同情况的显示
     */
    @Override
    public void bindData() {
        int topSize = adHeadBeans.size();
        //设置轮播图指示器
        headline_circle_indicator.setCount(topSize); // 设置数量
        headline_circle_indicator.setSeletion(circleSelectedPosition);//设置位置为第一项目

        //设置画廊Gallery
        headline_image_gallery.setLength(topSize); // 设置长度
        gallerySelectedPosition = topSize *50 + circleSelectedPosition;
        headline_image_gallery.setSelection(gallerySelectedPosition); //设置数量，乘以倍数实现循环滑动
        headline_image_gallery.setDelayMillis(4000); //设置延迟为4s
        headline_image_gallery.start();
        headline_image_gallery.setAdapter(new GalleryAdapter());
        headline_image_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                circleSelectedPosition = i;
                gallerySelectedPosition = circleSelectedPosition % adHeadBeans.size();
                headline_circle_indicator.setSeletion(gallerySelectedPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        quickAdapter = new QuickAdapter<HomeNewsBean>(getActivity(), R.layout.item_home_news_layout, homeNewsBeans) {
                @Override
                protected void convert(BaseAdapterHelper helper, HomeNewsBean item) {
                    String mask = item.getMask();
                    helper.setText(R.id.item_news_tv_name, item.getAuthorName())
                            .setText(R.id.item_news_tv_time, item.getDatetime())
                            .setText(R.id.item_news_tv_type, mask)
                            .setText(R.id.item_news_tv_title, item.getTitle());
                    mImageLoader.displayImage(item.getImgurl(), (ImageView) helper.getView(R.id.item_news_tv_img),options);
                    mImageLoader.displayImage(item.getAuthoImg(), (ImageView) helper.getView(R.id.item_news_img_icon),options);
                    int index = 0;
                    for (int i = 0; i < mask.length(); i++) {
                        if (masks[i].equals(mask)) {
                            index = i;
                            break;
                        }
                    }

                    TextView tv_type = helper.getView(R.id.item_news_tv_type);
                    tv_type.setTextColor(getActivity().getResources().getColor(mask_colors[index]));
                    helper.getView(R.id.item_news_tv_arrow).setBackgroundColor(getActivity().getResources().getColor(mask_colors[index]));
                }
            };
            home_list.setAdapter(quickAdapter);
    }

    /**
     * AutoGallery的自定义Adapter
     */
    class GalleryAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int i) {
            return adHeadBeans.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Holder holder = null;
            if (view == null){
                view = mInflate.inflate(R.layout.item_gallery_layout,null);
                holder = new Holder();
                holder.item_head_galley_img = view.findViewById(R.id.item_head_galley_img);
                view.setTag(holder);
            }else {
                holder = (Holder) view.getTag();
            }
            //显示数据
//             Picasso.with(mView.getContext()).load(adHeadBeans.get(i%adHeadBeans.size()).getImgurl())
//                     .transform(getTransformation())
//                     .into( holder.item_head_galley_img );
            view.setMinimumWidth(MainActivity.WIDTH);
            mImageLoader.displayImage(adHeadBeans.get(i%adHeadBeans.size()).getImgurl(),holder.item_head_galley_img,options);
            return view;
        }
    }

    private static class Holder{
        ImageView item_head_galley_img;
    }

    //设置轮播图图片大小
    private Transformation getTransformation(){
        Transformation transformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = MainActivity.WIDTH;
                if (source.getWidth() == 0){
                    return source;
                }
                //如果图片小于设置的宽度，返回原图
                if (source.getWidth() > targetWidth){
                    return source;
                }else {
                    //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                    double aspectRatio = (double)source.getHeight() /  (double)source.getWidth();
                    int targetHeight = (int) (targetWidth * aspectRatio);
                    if (targetHeight != 0 && targetWidth != 0){
                        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                        if (result != source) {
                            // Same bitmap is returned if sizes are the same
                            source.recycle();
                        }
                        return result;
                    }else {
                        return source;
                    }
                }
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };
        return transformation;
    }
}
