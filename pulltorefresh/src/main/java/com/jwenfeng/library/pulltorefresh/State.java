package com.jwenfeng.library.pulltorefresh;



import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;


public class State {


    @IntDef({REFRESH, LOADMORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface REFRESH_STATE {

    }

    public static final int REFRESH = 10;
    public static final int LOADMORE = 11;
}
