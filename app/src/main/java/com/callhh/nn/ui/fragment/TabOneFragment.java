package com.callhh.nn.ui.fragment;


import com.callhh.module_common.listener.ApiResultCallBack;
import com.callhh.module_common.util.common.MyLogUtils;
import com.callhh.nn.R;
import com.callhh.nn.base.BaseFragment;
import com.callhh.nn.bean.DemoBean;
import com.callhh.module_common.bean.ResponseBean;
import com.callhh.nn.util.http.ApiConstants;
import com.callhh.nn.util.http.ApiRequestUtils;
import com.callhh.nn.util.okgo.ApiRequestUtilSSSS;

import java.util.List;
import java.util.Map;

/**
 * 首页first fragment
 */
public class TabOneFragment extends BaseFragment {


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
        findView(mView,R.id.tvOnClick).setOnClickListener(view -> {
            loadData();
        });

    }

    private void loadData() {
        Map<String, String> params = ApiRequestUtils.getUIdParams(9);

//        OkgoUtils.postRequest(ApiConstants.URL_Test, this, params
//                , new JsonCallback<ResponseBean<DemoBean>>() {
//
//                    @Override
//                    public void onSuccess(Response<ResponseBean<DemoBean>> response) {
//                        ResponseBean<DemoBean> body = response.body();
//                        DemoBean result = body.data;
//                        MyLogUtils.logI(body.msg);
//                    }
//
//                    @Override
//                    public void onError(Response<ResponseBean<DemoBean>> response) {
//
//                        MyLogUtils.logI(response.message());
//
//                    }
//                });

        ApiRequestUtilSSSS.post(getContext(),ApiConstants.URL_Test,params
        ,true,new ApiResultCallBack<DemoBean>(){
                    @Override
                    public void onResponse(ResponseBean<DemoBean> response) {
                        MyLogUtils.logI( "response.msg " +response.msg);
                        MyLogUtils.logI( "response.data.size() " +response.data.getType().size());
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
