package com.kumpra.christinebpolesti.kumpra;

import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by christine B. Polesti on 7/3/2015.
 */
public class InputInteractorImpl implements InputInteractor {

    @Override
    public void input(final String item, final String qty, final OnInputFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean error = false;
                if(TextUtils.isEmpty(item)) {
                    listener.onItemError();
                    error = true;
                }
                if(TextUtils.isEmpty(qty)) {
                    listener.onQtyError();
                    error = true;
                }
                if(!error) {
                    listener.onSuccess();
                }
            }
        }, 2000);
    }
}
