package com.leo.pro.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.leo.pro.R;
import com.leo.pro.app.base.BaseFragmentActivity;
import com.leo.pro.databinding.ActivityMainBinding;
import com.leo.pro.fun1.view.Fun1Fragment;
import com.leo.pro.fun2.view.Fun2Fragment;
import com.leo.pro.fun3.view.Fun3Fragment;
import com.leo.pro.home.view.HomeFragment;
import com.leo.pro.main.contract.MainContract;
import com.leo.pro.main.presenter.MainPresenter;

import java.util.ArrayList;

/**
 * 创建人 LEO
 * 创建时间 2019/1/30 14:26
 */

public class MainActivity extends BaseFragmentActivity<MainPresenter, ActivityMainBinding> implements MainContract.View {

    private FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments = new ArrayList<>() ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mDoubleKeyExit = true ;
        super.onCreate(savedInstanceState);
    }

    @Override
    public int onSetContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onInitView() {
        mFragmentManager = getSupportFragmentManager();
        mFragments.add(new HomeFragment()) ;
        mFragments.add(new Fun1Fragment()) ;
        mFragments.add(new Fun2Fragment()) ;
        mFragments.add(new Fun3Fragment()) ;
    }

    @Override
    public void onInitData() {
        setCurrentFragment(0);
    }
    /**
     * 显示fragment
     * @param index
     */
    private void setCurrentFragment(int index){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragments.get(index);
        fragmentTransaction.replace(R.id.root_fragment_layout, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onInitListener() {
        mViewBinding.setClickListener(this);
        mViewBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_main:
                        setCurrentFragment(0);
                        break;
                    case R.id.rb_fun1:
                        setCurrentFragment(1);
                        break;
                    case R.id.rb_fun2:
                        setCurrentFragment(2);
                        break;
                    case R.id.rb_fun3:
                        setCurrentFragment(3);
                        break;
                }
            }
        });
    }
}
