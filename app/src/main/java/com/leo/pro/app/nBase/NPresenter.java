package com.leo.pro.app.nBase;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.leo.pro.app.net.HttpClient;
import com.leo.pro.app.utils.DiaLogUtils;
import com.leo.pro.app.utils.TextUtil;

import org.jetbrains.annotations.NotNull;

/**
 * 说明：
 * Created by code_nil on 2017/10/27.
 */

public class NPresenter<T extends NContract.View, E extends NContract.Model> {
    protected T view;
    protected E model;
    public Context context ;
    private AlertDialog dialog ;

    public void init(Object view, Object model) {
        this.view = (T) view;
        this.model = (E) model;
        context = ((T) view).context() ;
    }

    /**
     * 打开请求dialog
     */
    protected void showDialog(String msg){
        try{
            if (dialog == null) {
                if(TextUtil.isEmpty(msg))
                    msg = "请求数据中" ;
                dialog = DiaLogUtils.showDialog(view.context(), msg, null);
            } else if(!dialog.isShowing()) {
                dialog.show();
            }
        }catch(Exception e){
            e.printStackTrace() ;
        }
    }

    /**
     * 组装请求数据
     * @param keys
     * @param values
     * @return
     */
    protected HttpClient.Param[] createParams(@NotNull String[] keys, @NotNull String[] values){
        HttpClient.Param[] params = new HttpClient.Param[keys.length] ;
        for(int i = 0; i < keys.length ; i++){
            params[i] = new HttpClient.Param(keys[i],values[i]);
        }
        return params ;
    }

    /**
     * 显示toast信息
     * @param msg
     */
    protected void showMsg(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 关闭dialog
     */
    protected void closeDialog(){
        if(dialog != null){
            dialog.dismiss();
            dialog = null ;
        }
    }
    /**
     * 当onCreate或onCreateView方法执行完毕将会调用
     */
    public void start() {}

    /**
     * 当onDestroy或onDestroyView方法执行完毕将会调用
     */
    public void end() {
        model = null;
    }
}
