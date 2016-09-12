package com.ashomok.eNumbers.keyboard;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.ashomok.eNumbers.R;

import java.lang.reflect.Method;

/**
 * Created by iuliia on 9/4/16.
 */
public class DefaultKeyboard extends KeyboardImpl {
    private static final String TAG = DefaultKeyboard.class.getSimpleName();
    private View view;


    @Override
    public void init(Context context) {

        this.context = context;

        EditText editText = (EditText) ((Activity) context).findViewById(R.id.inputE);
        editText.setText("");

        //init custom button
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        ViewGroup relLayout = (ViewGroup) ((Activity) context).findViewById(R.id.keyboard_root);
        view = inflater.inflate(R.layout.default_keyboard, relLayout, true);

        ImageView switchButton = (ImageView) view.findViewById(R.id.switch_btn);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //switch keyboard
                OnKeyboardSwitchListener keyboardSwitchListener = getKeyboardSwitchListener();
                if (keyboardSwitchListener != null) {
                    keyboardSwitchListener.onKeyboardSwitch();
                }
            }
        });

        view.setVisibility(View.GONE);
    }

    public OnKeyboardSwitchListener getKeyboardSwitchListener() {
        if (onKeyboardSwitchListener == null) {
            Log.e(TAG, "keyboardSwitchListener was not setted. Call setKeyboardSwitchListener(OnKeyboardSwitchListener keyboardSwitchListener) before.");
        }
        return onKeyboardSwitchListener;
    }


    @Override
    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        //// TODO: 9/6/16
    }

    @Override
    public void hide() {
        super.hide();
        Log.d(TAG, "hide");
        view.setVisibility(View.GONE);

        View v = ((Activity) context).getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        //  block default keyboard
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((EditText) v).setShowSoftInputOnFocus(false);
        } else {
            try {
                final Method method = EditText.class.getMethod(
                        "setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(v, false);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());

            }
        }

    }

    @Override
    public void show() {
        Log.d(TAG, "show");
        super.show();
        view.setVisibility(View.VISIBLE);

        View v = ((Activity) context).getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(v, 0);
        }

        //  unblock default keyboard
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((EditText) v).setShowSoftInputOnFocus(true);
        } else {
            try {
                final Method method = EditText.class.getMethod(
                        "setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(v, true);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());

            }
        }
    }
}
