package com.callhh.nn.ui.fragment;


import com.callhh.abtool.util.common.MyLogUtils;
import com.callhh.nn.R;
import com.callhh.nn.base.BaseFragment;
import com.callhh.nn.bean.DemoBean;
import com.callhh.abtool.bean.ResponseBean;
import com.callhh.nn.util.http.ApiConstants;
import com.callhh.nn.util.http.ApiRequestUtils;
import com.callhh.nn.util.okgo.OkGoUtils;
import com.callhh.nn.util.okgo.callbck.ShowDialogCallback;
import com.lzy.okgo.model.Response;

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
        findView(mView, R.id.tvOnClick).setOnClickListener(view -> {
            loadData();
        });

    }

    private void loadData() {
        Map<String, String> params = ApiRequestUtils.getUIdParams(9);
        OkGoUtils.postRequest(ApiConstants.BASE_URL + ApiConstants.URL_Test, this, params
                , new ShowDialogCallback<ResponseBean<DemoBean>>(getActivity(),true) {

                    @Override
                    public void onSuccess(Response<ResponseBean<DemoBean>> response) {
                        ResponseBean<DemoBean> body = response.body();
                        DemoBean result = body.data;
                        MyLogUtils.logI(body.msg);
                    }

                    @Override
                    public void onError(Response<ResponseBean<DemoBean>> response) {
                        MyLogUtils.logI(response.message());
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
