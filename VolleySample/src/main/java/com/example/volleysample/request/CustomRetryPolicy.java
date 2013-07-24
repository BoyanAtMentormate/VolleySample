package com.example.volleysample.request;

import com.android.volley.DefaultRetryPolicy;

/**
 * Created by boyan on 7/22/13.
 */
public class CustomRetryPolicy extends DefaultRetryPolicy {
    private static final int initialTimeoutMs = 3000;
    private static final int maxNumRetries = 3;
    private static final float backoffMultiplier = 1f;

    public CustomRetryPolicy() {
        super(initialTimeoutMs, maxNumRetries, backoffMultiplier);
    }
}
