package com.toda.consultant.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.toda.consultant.LoginActivity;
import com.toda.consultant.view.dialog.CommonDialog;

/**
 * Created by guugangzhu on 2017/2/21.
 */

public class HouseUtil {
    /***
     * 登录异常提示
     * @param context
     */
    public static void showLoginErrDialog(final Context context){
        Dialog dialog = new CommonDialog(context, "", "账户异常，请重新登录", "确定", "", new CommonDialog.CallBackListener() {
            @Override
            public void callBack() {
                UserUtils.loginOut((Activity)context);
                HouseUtil.goPage(context, LoginActivity.class, null);
                ActivityManagerTool.getInstance().removeAllActivity();
            }
        }, null);
        dialog.setCancelable(false);
        dialog.show();
    }

    /***
     * 跳转到指定页面
     *
     * @param clas        指定页面
     * @param data        传入数据
     */
    public static void goPage(Context context,Class<? extends Activity> clas, Bundle data) {
        if (clas == null) {
            return;
        }
        Intent intent = new Intent(context, clas);
        if (data != null) {
            intent.putExtra(Ikeys.KEY_DATA, data);
        }
        context.startActivity(intent);
    }
}
