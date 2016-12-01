package cn.com.oasis;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: hewei@incoming.cn
 * 时间:  16/11/23 下午10:16
 */

public class CstView extends View{
    public CstView(Context context) {
        super(context);
    }

    public CstView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        Log.e("CstView", getParent() + "");
        if (getParent() instanceof CstLinearLayout){
            Log.e("CstView", "CstLinearLayout");
        }else {
            Log.e("CstView", "err");
        }
    }
}
