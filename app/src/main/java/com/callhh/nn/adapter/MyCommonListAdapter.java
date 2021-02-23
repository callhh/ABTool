package com.callhh.nn.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.callhh.abtool.util.common.MyLogUtils;
import com.callhh.abtool.util.common.MyTextUtil;
import com.callhh.nn.R;
import com.callhh.nn.bean.CommonListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的ListView Adapter Demo
 */
public class MyCommonListAdapter extends BaseAdapter {

    private Context context;
    private List<CommonListBean.DataBean.RecordBean> mListBeans;


    public MyCommonListAdapter(Context context, List<CommonListBean.DataBean.RecordBean> listPage) {
        this.context = context;
        this.mListBeans = listPage;
    }

    /**
     * 更新数据
     */
    public void setListPage(List<CommonListBean.DataBean.RecordBean> listPage) {
        this.mListBeans = listPage;
        notifyDataSetChanged();
    }

    /**
     * 增加数据
     */
    public void addListPage(List<CommonListBean.DataBean.RecordBean> listPage) {
        this.mListBeans.addAll(listPage);
        notifyDataSetChanged();
    }

    public List<CommonListBean.DataBean.RecordBean> getListPage() {
        return mListBeans;
    }

    @Override
    public int getCount() {
        return mListBeans == null ? 0 : mListBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(context, R.layout.list_item_common_list, null);
                viewHolder.tvTimes = convertView.findViewById(R.id.tvTimes);
                viewHolder.tvContents = convertView.findViewById(R.id.tvContents);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            CommonListBean.DataBean.RecordBean recordBean = mListBeans.get(position);
            if (null != recordBean) {
                MyTextUtil.setText(viewHolder.tvContents, recordBean.getCreateContent());

            }
        } catch (Exception e) {
            MyLogUtils.logI(e.toString());
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvContents;
        TextView tvTimes;
    }

}
