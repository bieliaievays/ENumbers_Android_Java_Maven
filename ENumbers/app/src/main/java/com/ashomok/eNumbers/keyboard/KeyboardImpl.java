package com.ashomok.eNumbers.keyboard;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.ashomok.eNumbers.R;

/**
 * Created by iuliia on 9/5/16.
 */
abstract class KeyboardImpl implements Keyboard {

    private static final String TAG = KeyboardImpl.class.getSimpleName();
    private boolean isVisible;
    Context context;
    OnVisibilityChangedListener onVisibilityChangedListener;
    EditText editText;

    FrameLayout switchButton;

    public void init(Context c) {
        this.context = c;
        switchButton = (FrameLayout) ((Activity) context).findViewById(R.id.switch_btn_root);

        editText = (EditText) ((Activity) context).findViewById(R.id.inputE);
    }

    @Override
    public void hide() {
        isVisible = false;
        switchButton.setVisibility(View.GONE);
    }

    @Override
    public void show() {

        isVisible = true;
        switchButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setOnVisibilityChangedListener(OnVisibilityChangedListener listener) {
        onVisibilityChangedListener = listener;
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

}
