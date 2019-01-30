package com.leo.pro.login.view;

import android.view.View;

import com.leo.pro.MainActivity;
import com.leo.pro.R;
import com.leo.pro.base.BaseActivity;
import com.leo.pro.databinding.ActivityLoginBinding;
import com.leo.pro.login.contract.LoginContract;
import com.leo.pro.login.presenter.LoginPresenter;
import com.leo.pro.utils.ActivityUtils;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 10:34
 */

public class LoginActivity extends BaseActivity<LoginPresenter,ActivityLoginBinding> implements LoginContract.View {
    @Override
    public int onSetContentView() {
        return R.layout.activity_login ;
    }

    @Override
    public void onInitView() {

    }

    @Override
    public void onInitData() {

    }

    @Override
    public void onInitListener() {
        mViewBinding.setClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_login:
                ActivityUtils.startActivityIntent(mContext, MainActivity.class);
                break;
        }
    }
}
