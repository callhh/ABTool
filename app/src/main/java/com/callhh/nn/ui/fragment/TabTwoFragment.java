package com.callhh.nn.ui.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.callhh.abtool.util.common.GsonUtil;
import com.callhh.abtool.util.common.MyLogUtils;
import com.callhh.nn.R;
import com.callhh.nn.adapter.PrimaryListAdapter;
import com.callhh.nn.adapter.SubAdapter;
import com.callhh.nn.base.BaseFragment;
import com.callhh.nn.bean.classification.CommodityClassificationEntity;
import com.callhh.nn.bean.classification.GoodsAllDataListBean;
import com.callhh.nn.util.FileResUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页Tab2 second fragment
 * 【分类】
 */
public class TabTwoFragment extends BaseFragment {

    private RecyclerView mRvPrimary, mRvSecondary;
    private List<CommodityClassificationEntity> mClassificationEntityList = new ArrayList<>();
    private SubAdapter mSubAdapter;//右边商品分类列表适配器

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_tab_two;
    }

    @Override
    protected void initView() {
        MyLogUtils.logI("start TabTwoFragment- 首页tab2 ");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRvPrimary = mView.findViewById(R.id.rvPrimary);
        mRvSecondary = mView.findViewById(R.id.rvSecondary);
        initLeftList();
        initRightList();
    }

    /**
     * 初始化左边 分类菜单列表
     */
    private void initLeftList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.shape_divider));
        mRvPrimary.addItemDecoration(dividerItemDecoration);
        mRvPrimary.setLayoutManager(linearLayoutManager);
        //初始化数据 绑定列表适配器
        mClassificationEntityList = parsePrimaryJson();
        PrimaryListAdapter primaryListAdapter = new PrimaryListAdapter(getActivity(), mClassificationEntityList);
        mRvPrimary.setAdapter(primaryListAdapter);
        //行点击监听事件
        primaryListAdapter.setOnRecyclerViewItemClick((childView, position) -> {
            CommodityClassificationEntity classificationEntity = mClassificationEntityList.get(position);
            MyLogUtils.logI("onItemClick() 点击的分类名称：" + classificationEntity.getName());
            MyLogUtils.logI("onItemClick() 左边菜单列表 点击了第 " + position + " 个");
            primaryListAdapter.setClickedPosition(position);
            primaryListAdapter.notifyDataSetChanged();
            int rvPrimaryHeight = mRvPrimary.getHeight();
            MyLogUtils.logI("method:onItemClick#RecyclerView_height= " + rvPrimaryHeight);
            MyLogUtils.logI("method:onItemClick#childView_height= " + childView.getHeight());
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            //左右列表 实时滚动到中心位置
            scrollToMiddleVertical(childView, position, firstVisibleItemPosition);
            //右边列表 实时更新数据； position下标作为取数据集合元素 测试专用！！！
            if (position > 0) position = 1;
            setSubAdapter(position, classificationEntity);
        });
    }

    /**
     * 初始化右边 分类商品数据列表
     */
    private void initRightList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvSecondary.setLayoutManager(linearLayoutManager);
        mSubAdapter = new SubAdapter(getActivity());
        mRvSecondary.setAdapter(mSubAdapter);
        //确保左边的分类菜单有数据，才能更新右边的商品分类数据
        if (mClassificationEntityList != null && mClassificationEntityList.size() > 0) {
            CommodityClassificationEntity classificationEntity = mClassificationEntityList.get(0);
            setSubAdapter(0, classificationEntity);
        }
    }

    /**
     * 设置更新数据 绑定适配器
     *
     * @param classificationEntity 数据实体
     */
    private void setSubAdapter(int position, CommodityClassificationEntity classificationEntity) {
        List<CommodityClassificationEntity> subclassificationList = classificationEntity.getSubclassificationList();
        if (subclassificationList == null || subclassificationList.size() == 0) {
            subclassificationList = parseSubJson(position);//重新解析
            if (subclassificationList != null)
                classificationEntity.setSubclassificationList(subclassificationList);
        }
        mSubAdapter.replaceDataList(subclassificationList);
    }

    /**
     * 滚动到列表的中心位置
     *
     * @param childView                子视图
     * @param clickedPosition          点击行的位置
     * @param firstVisibleItemPosition 第一行可见位置
     */
    private void scrollToMiddleVertical(View childView, int clickedPosition, int firstVisibleItemPosition) {
        MyLogUtils.logI("method:onItemClick#clickedPosition= " + clickedPosition + ", firstVisibleItemPosition= " + firstVisibleItemPosition);
        int vHeight = childView.getHeight();
        Rect rect = new Rect();
        mRvPrimary.getGlobalVisibleRect(rect);
        int reHeight = rect.bottom - rect.top - vHeight;
        MyLogUtils.logI("method:onItemClick#reHeight= " + (rect.bottom - rect.top));
        int top = mRvPrimary.getChildAt(clickedPosition - firstVisibleItemPosition).getTop();
        MyLogUtils.logI("method:onItemClick#top= " + top);
        int half = reHeight / 2;
        MyLogUtils.logI("method:onItemClick#smoothScrollBy_dy= " + (top - half));
        mRvPrimary.smoothScrollBy(0, top - half);

    }

    /**
     * 解析出json文件中的所有最新数据：左边的分类菜单数据
     *
     * @return 集合数据
     */
    public List<CommodityClassificationEntity> parsePrimaryJson() {
        List<CommodityClassificationEntity> classificationList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            String jsonStr = FileResUtils.getJsonStr(getActivity(), "classification_primary.json");
            classificationList = gson.fromJson(jsonStr, new TypeToken<List<CommodityClassificationEntity>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyLogUtils.d("分类菜单数据：parsePrimaryJson() classificationList=" + classificationList);
        return classificationList;
    }

    /**
     * 解析出json文件中的所有最新数据：右边的分类商品数据
     *
     * @return  集合数据
     */
    public List<CommodityClassificationEntity> parseSubJson(int position) {
        List<CommodityClassificationEntity> dataList = new ArrayList<>();
        try {
            String jsonStr = FileResUtils.getJsonStr(getActivity(), "classification_sub_01.json");
//            dataList = gson.fromJson(jsonStr, new TypeToken<List<CommodityClassificationEntity>>() {
//            }.getType());
            GoodsAllDataListBean entity = GsonUtil.parseJSON(jsonStr, GoodsAllDataListBean.class);
            if (entity != null) {
                switch (position) {
                    case 0:
                        dataList = entity.getTuijian_fenlei();
                        break;
                    case 1:
                        dataList = entity.getJingdong_chaoshi();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyLogUtils.d("商品数据：parseSubJson() classificationList=" + dataList);
        return dataList;
    }

}
