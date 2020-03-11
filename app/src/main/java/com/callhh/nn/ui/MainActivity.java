package com.callhh.nn.ui;


import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.callhh.module_common.util.common.ToastUtil;
import com.callhh.nn.R;
import com.callhh.nn.base.BaseActivity;
import com.callhh.nn.ui.fragment.TabThreeFragment;
import com.callhh.nn.ui.fragment.TabTwoFragment;
import com.callhh.nn.ui.fragment.TabOneFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * App首页
 * 底部tab菜单：
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tvTabOne)
    TextView mTvTabOne;
    @BindView(R.id.tvTabTwo)
    TextView mTvTabTwo;
    @BindView(R.id.tvTabThree)
    TextView mTvTabThree;
    private List<TextView> mTvList = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private int oldIndex = 1;
    private boolean isExit;

        @Override
        protected int setLayoutId() {
            return R.layout.activity_main;
        }

    @Override
    protected void initView() {
        mTvList.add(mTvTabOne);
        mTvList.add(mTvTabTwo);
        mTvList.add(mTvTabThree);
        mTvTabOne.setSelected(true);//默认选中首页
        initFragments();
    }

    private void initFragments() {
        TabOneFragment firstFragment = new TabOneFragment();
        TabTwoFragment secondFragment = new TabTwoFragment();
        TabThreeFragment thirdFragment = new TabThreeFragment();
        mFragments.add(firstFragment);
        mFragments.add(secondFragment);
        mFragments.add(thirdFragment);
        //默认加载前两个Fragment，其中第一个展示，第二个隐藏
        getSupportFragmentManager().beginTransaction().add(R.id.content, firstFragment)
                .add(R.id.content, secondFragment).hide(secondFragment).show(firstFragment).commit();
    }

    @Override
    protected void initData() {
        //初始化相关数据
//        mTvTab1_Home.postDelayed(()->
//                new UpdateAppManager(mActivity).updateAPP()
//        , 1500);

    }

    @Override
    protected void initListener() {
        mTvTabOne.setOnClickListener(this);
        mTvTabTwo.setOnClickListener(this);
        mTvTabThree.setOnClickListener(this);
    }

    /**
     * tab菜单选择
     *
     * @param curIndex 当前tab下标
     */
    private void tabSelected(int curIndex) {
        if (curIndex != oldIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            mTvTabOne.setSelected(false);
            mTvList.get(oldIndex).setSelected(false);
            mTvList.get(curIndex).setSelected(true);
            ft.hide(mFragments.get(oldIndex));
            //判断是否添加
            if (!mFragments.get(curIndex).isAdded()) {
                ft.add(R.id.content, mFragments.get(curIndex)).show(mFragments.get(curIndex)).commit();
            } else {
                ft.show(mFragments.get(curIndex)).commit();
            }
        }
        oldIndex = curIndex;
    }

    /**
     * 双击退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isExit) {
                finish();
            } else {
                ToastUtil.toastBottomView(mActivity
                        , getResources().getString(R.string.app_exit_tips));
                new Handler().postDelayed(() -> isExit = false, 2000);
                isExit = true;
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTabOne:
                //
                tabSelected(0);
                break;
            case R.id.tvTabTwo:
                //
                tabSelected(1);
                break;
            case R.id.tvTabThree:
                //
                tabSelected(2);
                break;
        }
    }
}
