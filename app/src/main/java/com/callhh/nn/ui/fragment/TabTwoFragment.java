package com.callhh.nn.ui.fragment;


import com.callhh.module_common.util.common.MyLogUtils;
import com.callhh.nn.R;
import com.callhh.nn.base.BaseFragment;

/**
 * 首页 second fragment
 */
public class TabTwoFragment extends BaseFragment {


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_tab_one;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        unbinder = ButterKnife.bind(this, rootView);
//        return rootView;
//    }

    @Override
    protected void initView() {
        MyLogUtils.logI("start TabMyFragment- 首页 ");

    }

    @Override
    protected void initData() {
        loadData();
    }

    private void loadData() {
//        ApiRequestUtils.postJson(mActivity, HttpResponseStatus.URL_USER_INFO, ""
//                , true, new ResultCallBack() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            MyInfoBean dataBean = JsonUtil.parseJSON(response, MyInfoBean.class);
//                            if (null != dataBean)
//                                if (null != dataBean.getData()) {
//                                    MyInfoBean.DataBean data = dataBean.getData();
//                                    MyTextUtil.setText(mTvJobDepartment, data.getOrg());
//                                    if (StringUtils.isNotBlank(data.getHeadPicUrl())) {
//                                        GlideUtil.getInstance().loadingImage(mActivity, mIvAvatar
//                                                , data.getHeadPicUrl(), R.mipmap.ic_avatar_def, false);
//                                    }
//                                }
//                        } catch (Exception e) {
//                            DialogUtil.cancleLoadingDialog(true);
//                            LogUtils.logI(e.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        DialogUtil.cancleLoadingDialog(true);
////                        ToastUtil.toastBottomView(mActivity, e.getMessage());
//                    }
//                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
