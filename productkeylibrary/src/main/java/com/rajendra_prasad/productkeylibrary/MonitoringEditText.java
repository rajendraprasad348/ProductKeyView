package com.rajendra_prasad.productkeylibrary;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import java.util.ArrayList;

/**
 * Created by Rajendra Prasad C on 24,December,2019
 * Manipal Karnataka
 */
public class MonitoringEditText extends AppCompatEditText {

    ArrayList<MonitoringViewListener> listeners;

    public MonitoringEditText(Context context) {
        super(context);
        listeners = new ArrayList<>();
    }

    public MonitoringEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        listeners = new ArrayList<>();
    }

    public MonitoringEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        listeners = new ArrayList<>();
    }

    public void addListener(MonitoringViewListener listener) {
        try {
            listeners.add(listener);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Here you can catch paste, copy and cut events
     */
    @Override
    public boolean onTextContextMenuItem(int id) {
        boolean consumed = super.onTextContextMenuItem(id);
        switch (id) {
            case android.R.id.cut:
                onTextCut();
                break;
            case android.R.id.paste:
                onTextPaste();
                break;
            case android.R.id.copy:
                onTextCopy();
        }
        return consumed;
    }

    public void onTextCut() {
    }

    public void onTextCopy() {
    }

    /**
     * adding listener for Paste for example
     */
    public void onTextPaste() {
        for (MonitoringViewListener listener : listeners) {
            listener.onUpdate();
        }
    }
}