package com.callhh.nn.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.callhh.nn.R;
import com.callhh.nn.bean.classification.CommodityClassificationEntity;
import com.callhh.nn.listener.OnRecyclerViewItemClick;

import java.util.List;

/**
 * 适配器，根据不同的数据类型，展示不同的UI效果
 * Created on 2021-02-19 by callhh
 * Created by callhh on 2021-02-19
 */
public class PrimaryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CommodityClassificationEntity> mClassificationList;
    private OnRecyclerViewItemClick mOnRecyclerViewItemClick;
    private int mClickedPosition;//选中的行下标
    private final int ITEM_VIEW_TYPE_PRIMARY = 1;

    public PrimaryListAdapter(Context context) {
        this.mContext = context;
    }

    public PrimaryListAdapter(Context context, List<CommodityClassificationEntity> classificationList) {
        mContext = context;
        mClassificationList = classificationList;
    }

    public void setClassificationList(List<CommodityClassificationEntity> classificationList) {
        mClassificationList = classificationList;
    }

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        mOnRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    public void setClickedPosition(int clickedPosition) {
        mClickedPosition = clickedPosition;
    }

    @Override
    public int getItemViewType(int position) {
        if (mClassificationList != null && mClassificationList.size() > 0) {
            CommodityClassificationEntity classificationEntity = mClassificationList.get(position);
            if (classificationEntity.getcId() == 0) {
                return ITEM_VIEW_TYPE_PRIMARY;
            }
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new PrimaryViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.list_item_primary_classification, parent, false));
            case 0:
            default:
                return new UnknowViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.list_item_primary_classification, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        CommodityClassificationEntity classificationEntity = mClassificationList.get(position);
        switch (itemViewType) {
            case 1: {
                PrimaryViewHolder commodityHolder = (PrimaryViewHolder) holder;
                String name = classificationEntity.getName();
                commodityHolder.tvName.setText(name);
                if (mClickedPosition == position) {
                    commodityHolder.bgItem.setBackground(mContext.getResources().getDrawable(R.drawable.shape_checked));
                    commodityHolder.tvName.setTextSize(16);
                    commodityHolder.tvName.setTextColor(mContext.getResources().getColor(R.color.black));
                    commodityHolder.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                } else {
                    commodityHolder.bgItem.setBackground(mContext.getResources().getDrawable(R.drawable.shape_normal));
                    commodityHolder.tvName.setTextSize(14);
                    commodityHolder.tvName.setTextColor(mContext.getResources().getColor(R.color.black_999999));
                    commodityHolder.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//常规
                }
            }
            break;
            case 2: {
                PrimaryViewHolder commodityHolder = (PrimaryViewHolder) holder;

            }
            break;
            case 3: {
                CategoryNevViewHolder categoryNevHolder = (CategoryNevViewHolder) holder;
//                List<ColorItem> items = DemoData.loadDemoColorItems(mContext);
//                Collections.sort(items, new Comparator<ColorItem>() {
//                    @Override
//                    public int compare(ColorItem lhs, ColorItem rhs) {
//                        return lhs.name.compareTo(rhs.name);
//                    }
//                });
//
//                DemoColorPagerAdapter adapter = new DemoColorPagerAdapter();
//                adapter.addAll(items);
//                categoryNevHolder.vp.setAdapter(adapter);
//                categoryNevHolder.tabLayout.setUpWithViewPager(categoryNevHolder.vp);
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
        return 0;
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

    class PrimaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FrameLayout bgItem;
        TextView tvName;

        PrimaryViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(false);
            bgItem = (FrameLayout) itemView.findViewById(R.id.flBgItem);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            bgItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnRecyclerViewItemClick != null) {
                mOnRecyclerViewItemClick.onItemClick(v, getAdapterPosition());
            }
        }
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvDesc;

        CategoryViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(false);
//            ivIcon = (ImageView) itemView.findViewById(R.id.imageview_head_icon);
//            tvName = (TextView) itemView.findViewById(R.id.textview_chat_name);
//            tvDesc = (TextView) itemView.findViewById(R.id.textview_last_msg_content);
        }
    }

    class CategoryNevViewHolder extends RecyclerView.ViewHolder {
        ViewPager vp;
        ImageView ivIcon;
        TextView tvName;
        TextView tvDesc;

        CategoryNevViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(true);
//            vp = (ViewPager) itemView.findViewById(R.id.view_pager);
//            tabLayout = (RecyclerTabLayout) itemView.findViewById(R.id.recycler_tab_layout);

//            ivIcon = (ImageView) itemView.findViewById(R.id.imageview_head_icon);
//            tvName = (TextView) itemView.findViewById(R.id.textview_chat_name);
//            tvDesc = (TextView) itemView.findViewById(R.id.textview_last_msg_content);
        }
    }

    class CategoryVpViewHolder extends RecyclerView.ViewHolder {
        ViewPager vp;
        TextView tvName;
        TextView tvDesc;

        CategoryVpViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(false);
//            ivIcon = (ImageView) itemView.findViewById(R.id.imageview_head_icon);
//            tvName = (TextView) itemView.findViewById(R.id.textview_chat_name);
//            tvDesc = (TextView) itemView.findViewById(R.id.textview_last_msg_content);
        }
    }

}
