package com.callhh.nn.bean.classification;

import java.util.List;

/**
 * 分类 - 右边商品数据列表
 * Created by callhh on 2021/2/23
 */
public class GoodsAllDataListBean {

    private List<CommodityClassificationEntity> tuijian_fenlei;
    private List<CommodityClassificationEntity> jingdong_chaoshi;

    public List<CommodityClassificationEntity> getTuijian_fenlei() {
        return tuijian_fenlei;
    }

    public void setTuijian_fenlei(List<CommodityClassificationEntity> tuijian_fenlei) {
        this.tuijian_fenlei = tuijian_fenlei;
    }

    public List<CommodityClassificationEntity> getJingdong_chaoshi() {
        return jingdong_chaoshi;
    }

    public void setJingdong_chaoshi(List<CommodityClassificationEntity> jingdong_chaoshi) {
        this.jingdong_chaoshi = jingdong_chaoshi;
    }

}
