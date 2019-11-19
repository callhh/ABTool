package com.tattle.tantou.adapter;

import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.callhh.module_common.util.common.MyLogUtils;
import com.callhh.module_common.util.common.MyTextUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tattle.tantou.R;
import com.tattle.tantou.bean.mycenter.MessageListBean;

import java.util.List;

/**
 * 消息列表Adapter
 */
public class MessageListAdapter extends BaseQuickAdapter<MessageListBean.DataBean.RecordBean, BaseViewHolder> {

    public MessageListAdapter(@Nullable List<MessageListBean.DataBean.RecordBean> data) {
        super(R.layout.list_item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean.DataBean.RecordBean itemModel) {
        initUI(helper, itemModel);
    }

    private void initUI(BaseViewHolder helper, MessageListBean.DataBean.RecordBean model) {
        try {
            LinearLayout llItemLayout = helper.getView(R.id.llItemLayout);
            TextView tvTitle = helper.getView(R.id.tvTitle);
            TextView tvCreateTime = helper.getView(R.id.tvCreateTime);
            TextView tvContent = helper.getView(R.id.tvContent);
            TextView tvRightButton = helper.getView(R.id.tvRightButton);
            //更新数据
            if (null != model) {
                MyTextUtil.setText(tvTitle, model.getTitle());
                MyTextUtil.setText(tvCreateTime, model.getCreateTime());
                MyTextUtil.setText(tvContent, model.getContent());
                String readFlag = model.getReadFlag();
                if (readFlag.equals("Y")) {
                    llItemLayout.setBackgroundResource(R.drawable.shape_corners_4_solid_gray_stroke_green_e4ebe5);
                } else if (readFlag.equals("N")) {
                    llItemLayout.setBackgroundResource(R.drawable.shape_corners_4_solid_white_stroke_green_e4ebe5);
                }
                //点击可查看详情
           }
        } catch (Exception e) {
            MyLogUtils.logI(e.toString());
        }
    }
}
