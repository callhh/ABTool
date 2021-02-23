package com.callhh.nn.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.callhh.nn.R;
import com.callhh.nn.bean.classification.CommodityClassificationEntity;
import com.callhh.nn.listener.OnRecyclerViewItemClick;
import com.callhh.nn.util.imageloader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分类列表右边商品列表适配器，根据不同的数据类型，展示不同的UI效果
 * Created by callhh on 2021-02-20
 */
public class SubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CommodityClassificationEntity> mClassificationList;
    private OnRecyclerViewItemClick mOnRecyclerViewItemClick;
    private int[] iconArry = {R.drawable.aaa, R.drawable.aab, R.drawable.aac, R.drawable.aad, R.drawable.aae, R.drawable.aaf};
    private final int ITEM_VIEW_TYPE_PRIMARY = 1;

    public SubAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * 设置列表数据 数据累加更新
     *
     * @param dataList 集合数据
     */
    public void setDataList(List<CommodityClassificationEntity> dataList) {
        this.mClassificationList = dataList == null ? new ArrayList<>() : dataList;
        notifyDataSetChanged();
    }

    /**
     * 设置列表数据 替换当前列表数据
     *
     * @param dataList 集合数据
     */
    public void replaceDataList(List<CommodityClassificationEntity> dataList) {
        if (this.mClassificationList == null) this.mClassificationList = new ArrayList<>();
        if (dataList != mClassificationList) {
            mClassificationList.clear();
            mClassificationList.addAll(dataList == null ? new ArrayList<>() : dataList);
        }
        notifyDataSetChanged();
    }

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        mOnRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    @Override
    public int getItemViewType(int position) {
        if (mClassificationList != null && mClassificationList.size() > 0) {
            CommodityClassificationEntity classificationEntity = mClassificationList.get(position);
            int type = classificationEntity.getType();
            return type;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 2:
                return new BannerListHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.list_item_sub_banner_list, parent, false));
            case 3:
                return new GoodsListHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.list_item_sub_grid_normal_temp, parent, false));
            case 0:
            default:
                return new UnknowViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.list_item_primary_classification, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        if (mClassificationList.size() < 1) return;
        CommodityClassificationEntity classificationEntity = mClassificationList.get(position);
        switch (itemViewType) {
            case 2: //横幅广告数据展示
                BannerListHolder bannerListHolder = (BannerListHolder) holder;
                // 设置数据
                String[] urls = mContext.getResources().getStringArray(R.array.url);
                List list = Arrays.asList(urls);
                List<String> images = new ArrayList(list);
                //设置banner样式
//                bannerListHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                //设置图片加载器
                bannerListHolder.banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                bannerListHolder.banner.setImages(images);
                //设置banner动画效果
                bannerListHolder.banner.setBannerAnimation(Transformer.DepthPage);
                //设置标题集合（当banner样式有显示title时）
//                bannerListHolder.banner.setBannerTitles(titles);
                //设置自动轮播，默认为true
                bannerListHolder.banner.isAutoPlay(true);
                //设置轮播时间
                bannerListHolder.banner.setDelayTime(1500);
                //设置指示器位置（当banner模式中有指示器时）
                bannerListHolder.banner.setIndicatorGravity(BannerConfig.CENTER);
                //banner设置方法全部调用完毕时最后调用
                bannerListHolder.banner.start();
                break;
            case 3: //商品类目数据展示，此处使用 gridLayout 控件; TODO 亦可采用 GridView
                GoodsListHolder goodsListHolder = (GoodsListHolder) holder;
                goodsListHolder.tvTitle.setText(classificationEntity.getName());
                List<CommodityClassificationEntity> subclassificationList = classificationEntity.getSubclassificationList();
                if (goodsListHolder.gridLayout != null) goodsListHolder.gridLayout.removeAllViews();
                for (int i = 0; i < subclassificationList.size(); i++) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.gv_item, null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                        layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1.0f);
                        layoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1.0f);
                        view.setLayoutParams(layoutParams);
                    }
                    CommodityClassificationEntity classification = subclassificationList.get(i);
                    int p = i % iconArry.length;
                    ImageView ivIcon = view.findViewById(R.id.ivIcom);
                    TextView tvName = view.findViewById(R.id.tvName);
                    ivIcon.setImageDrawable(mContext.getResources().getDrawable(iconArry[p]));
                    tvName.setText(classification.getName());
                    // 添加item
                    goodsListHolder.gridLayout.addView(view);
                }
                break;
            case 0:
            default:
                UnknowViewHolder unknowHolder = (UnknowViewHolder) holder;
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mClassificationList != null && mClassificationList.size() > 0) {
            return mClassificationList.size();
        }
        return 1;
    }

    class UnknowViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvDesc;

        UnknowViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(false);
//            ivIcon = (ImageView) itemView.findViewById(R.id.imageview_head_icon);
//            tvName = (TextView) itemView.findViewById(R.id.textview_chat_name);
//            tvDesc = (TextView) itemView.findViewById(R.id.textview_last_msg_content);
        }
    }

    class GoodsListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //        GridView gridView;
        GridLayout gridLayout;
        TextView tvTitle;

        GoodsListHolder(View itemView) {
            super(itemView);
            itemView.setTag(false);
            gridLayout = (GridLayout) itemView.findViewById(R.id.grid_layout);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
//            bgItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnRecyclerViewItemClick != null) {
                mOnRecyclerViewItemClick.onItemClick(v, getAdapterPosition());
            }
        }
    }

    class BannerListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Banner banner;

        BannerListHolder(View itemView) {
            super(itemView);
            itemView.setTag(false);
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        @Override
        public void onClick(View v) {
            if (mOnRecyclerViewItemClick != null) {
                mOnRecyclerViewItemClick.onItemClick(v, getAdapterPosition());
            }
        }
    }

}
