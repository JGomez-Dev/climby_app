package com.app.climby.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class SpinnerExtension extends Spinner {

    public SpinnerExtension(@NonNull Context context) {
        super(context);
    }

    public SpinnerExtension(@NonNull Context context, int mode) {
        super(context, mode);
    }

    public SpinnerExtension(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpinnerExtension(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SpinnerExtension(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public SpinnerExtension(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode, int popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        super.setAdapter(adapter);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            // Get private mPopup member variable and try cast to ListPopupWindow
            ListPopupWindow popupWindow = (ListPopupWindow) popup.get(this);
            if (popupWindow != null) {
                popupWindow.setHeight(this.getMeasuredHeight() * 5);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}