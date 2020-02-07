package com.rajendra_prasad.productkeylibrary;

import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.content.ContentValues.TAG;

/**
 * Created by Rajendra Prasad C on 20,November,2019
 * Manipal Karnataka
 */
public class ProductKeyView extends LinearLayout {
    MonitoringEditText editText1, editText2, editText3, editText4;
    private LinearLayout ll_total_view;

    public static final int NORMAL = 0;
    public static final int BOLD = 1;
    public static final int ITALIC = 2;
    public static final int BOLD_ITALIC = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NORMAL, BOLD, ITALIC, BOLD_ITALIC})
    public @interface FontFaceType {
    }

    @FontFaceType
    private int pkvTextStyle = NORMAL;


    private int pkvMaxLength; // change here to validate fields
    private boolean pkvViewEnabled;
    private String pkvHint = "- - - -";
    private int pkvHintColor, pkvBackground, pkvItemHeight, pkvTextColor;
    private float pkvTextSize;
    private Drawable pkvItemBackground;


    public ProductKeyView(Context context) {
        super(context);
    }

    public ProductKeyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProductKeyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProductKeyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }


    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_product_key_view, this, true);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.custom, 0, 0);
        try {

            pkvHint = typedArray.getString(R.styleable.custom_pkv_hint);
            pkvBackground = typedArray.getInt(R.styleable.custom_pkv_background, getColor(R.color.pkv_default_color));
            pkvTextColor = typedArray.getInt(R.styleable.custom_pkv_textColor, getColor(R.color.text_default_color));
            pkvItemHeight = typedArray.getInt(R.styleable.custom_pkv_itemHeight, 6);
            pkvHintColor = typedArray.getColor(R.styleable.custom_pkv_hintColor, getColor(R.color.hint_default_color));
            pkvItemBackground = typedArray.getDrawable(R.styleable.custom_pkv_itemBackground);
            pkvViewEnabled = typedArray.getBoolean(R.styleable.custom_pkv_viewEnabled, true);
            pkvMaxLength = typedArray.getInt(R.styleable.custom_pkv_maxLength, 4);
            pkvTextSize = typedArray.getDimensionPixelSize(R.styleable.custom_pkv_textSize, context.getResources().getDimensionPixelSize(R.dimen.text_size_default));
            pkvTextStyle = typedArray.getInt(R.styleable.custom_pkv_textStyle, Typeface.defaultFromStyle(Typeface.NORMAL).getStyle());

        } finally {
            typedArray.recycle();
        }


        initComponents();

    }

    private void initComponents() {
        editText1 = findViewById(R.id.inputCode1);
        editText2 = findViewById(R.id.inputCode2);
        editText3 = findViewById(R.id.inputCode3);
        editText4 = findViewById(R.id.inputCode4);
        ll_total_view = findViewById(R.id.ll_total_view);

        setPkvEnabled(pkvViewEnabled);
        setPkvHintColor(pkvHintColor);
        setPkvHint(pkvHint);
        setPkvItemBackground(pkvItemBackground);
        setPkvViewBackground(pkvBackground);
        setPkvItemHeight(pkvItemHeight);
        setPkvMaxLength(pkvMaxLength);
        setPkvTextSize(pkvTextSize);
        setPkvTextColor(pkvTextColor);
        setPkvTextStyle(pkvTextStyle);


        editText4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editText4.onTouchEvent(event);
                editText4.setCursorVisible(true);
                editText4.setSelection(editText4.getText().length());
                return true;
            }
        });


        editText2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (editText2.getText().toString().length() == 0) {
                        editText1.requestFocus();
                    }
                }
                return false;
            }
        });

        editText3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (editText3.getText().toString().length() == 0) {
                        editText2.requestFocus();
                    }
                }
                return false;
            }
        });

        editText4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (editText4.getText().toString().length() == 0) {
                        editText3.requestFocus();
                    }
                }
                return false;
            }
        });


        editText1.addTextChangedListener(new GenericTextWatcher(editText1));
        editText2.addTextChangedListener(new GenericTextWatcher(editText2));
        editText3.addTextChangedListener(new GenericTextWatcher(editText3));
        editText4.addTextChangedListener(new GenericTextWatcher(editText4));


        editText1.addListener(new MonitoringViewListener() {
            @Override
            public void onUpdate() {
                setProductKeyText(readFromClipboard());
            }
        });
        editText2.addListener(new MonitoringViewListener() {
            @Override
            public void onUpdate() {
                setProductKeyText(readFromClipboard());
            }
        });
        editText3.addListener(new MonitoringViewListener() {
            @Override
            public void onUpdate() {
                setProductKeyText(readFromClipboard());
            }
        });
        editText4.addListener(new MonitoringViewListener() {
            @Override
            public void onUpdate() {
                setProductKeyText(readFromClipboard());
            }
        });

    }

    public String readFromClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip()) {
            android.content.ClipDescription description = clipboard.getPrimaryClipDescription();
            android.content.ClipData data = clipboard.getPrimaryClip();
            if (data != null && description != null && description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
                return String.valueOf(data.getItemAt(0).getText());
        }
        return "";
    }

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (view.getId() == R.id.inputCode1) {
                if (text.length() == pkvMaxLength) {
                    editText2.requestFocus();
                    if (editText2.hasFocus()) {
                        editText2.setCursorVisible(true);
                    }
                } else {
                    editText1.requestFocus();
                    if (editText1.hasFocus()) {
                        editText1.setCursorVisible(true);
                    }
                }
            } else if (view.getId() == R.id.inputCode2) {
                if (text.length() == pkvMaxLength) {
                    editText3.requestFocus();
                    if (editText3.hasFocus()) {
                        editText3.setCursorVisible(true);
                    }
                } else {
                    editText2.requestFocus();
                    if (editText2.hasFocus()) {
                        editText2.setCursorVisible(true);
                    }
                }
            } else if (view.getId() == R.id.inputCode3) {

                if (text.length() == pkvMaxLength) {
                    editText4.requestFocus();
                    if (editText4.hasFocus()) {
                        editText4.setCursorVisible(true);
                    }
                } else {
                    editText3.requestFocus();
                    if (editText3.hasFocus()) {
                        editText3.setCursorVisible(true);
                    }
                }
            } else if (view.getId() == R.id.inputCode4) {
                if (text.length() == pkvMaxLength) {

                    hideSoftKeyboard(getContext(), view);

                    if (editText4.hasFocus()) {
                        editText4.setCursorVisible(false);
                    }
                } else {
                    editText4.requestFocus();
                    if (editText4.hasFocus()) {
                        editText4.setCursorVisible(false);
                    }
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence text, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence text, int arg1, int arg2, int arg3) {
        }

    }


    // hide keyboard
    public void hideSoftKeyboard(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            Log.d(TAG, "HideSoftKeyboard: " + e);
        }
    }


    public ProductKeyView setPkvEnabled(boolean disable) {
        this.pkvViewEnabled = disable;
        if (pkvViewEnabled) {
            editText1.setEnabled(true);
            editText2.setEnabled(true);
            editText3.setEnabled(true);
            editText4.setEnabled(true);
        } else {
            editText1.setEnabled(false);
            editText2.setEnabled(false);
            editText3.setEnabled(false);
            editText4.setEnabled(false);
        }
        return this;
    }

    public ProductKeyView setPkvHintColor(int hintColor) {
        this.pkvHintColor = hintColor;
        editText1.setHintTextColor(pkvHintColor);
        editText2.setHintTextColor(pkvHintColor);
        editText3.setHintTextColor(pkvHintColor);
        editText4.setHintTextColor(pkvHintColor);
        return this;
    }

    public ProductKeyView setPkvHint(String hintText) {
        this.pkvHint = hintText;
        editText1.setHint(pkvHint);
        editText2.setHint(pkvHint);
        editText3.setHint(pkvHint);
        editText4.setHint(pkvHint);
        return this;
    }

    public ProductKeyView setPkvTextSize(float textSize) {
        this.pkvTextSize = textSize;
        editText1.setTextSize(TypedValue.COMPLEX_UNIT_PX, pkvTextSize);
        editText2.setTextSize(TypedValue.COMPLEX_UNIT_PX, pkvTextSize);
        editText3.setTextSize(TypedValue.COMPLEX_UNIT_PX, pkvTextSize);
        editText4.setTextSize(TypedValue.COMPLEX_UNIT_PX, pkvTextSize);
        return this;
    }

    public ProductKeyView setPkvTextColor(int textColor) {
        this.pkvTextColor = textColor;
        editText1.setTextColor(pkvTextColor);
        editText2.setTextColor(pkvTextColor);
        editText3.setTextColor(pkvTextColor);
        editText4.setTextColor(pkvTextColor);
        return this;
    }

    public ProductKeyView setPkvItemBackground(Drawable image) {
        this.pkvItemBackground = image;
        editText1.setBackground(pkvItemBackground);
        editText2.setBackground(pkvItemBackground);
        editText3.setBackground(pkvItemBackground);
        editText4.setBackground(pkvItemBackground);
        return this;
    }

    public ProductKeyView setPkvViewBackground(int color) {
        this.pkvBackground = color;
        ll_total_view.setBackgroundColor(pkvBackground);
        return this;
    }

    public ProductKeyView setPkvItemHeight(int height) {
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        this.pkvItemHeight = height;
        editText1.setPadding(pkvItemHeight, pkvItemHeight, pkvItemHeight, pkvItemHeight);
        editText2.setPadding(pkvItemHeight, pkvItemHeight, pkvItemHeight, pkvItemHeight);
        editText3.setPadding(pkvItemHeight, pkvItemHeight, pkvItemHeight, pkvItemHeight);
        editText4.setPadding(pkvItemHeight, pkvItemHeight, pkvItemHeight, pkvItemHeight);
        return this;
    }

    public ProductKeyView setPkvMaxLength(int length) {
        this.pkvMaxLength = length;
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(pkvMaxLength);
        editText1.setFilters(filterArray);
        editText2.setFilters(filterArray);
        editText3.setFilters(filterArray);
        editText4.setFilters(filterArray);
        return this;
    }

    public int getPkvMaxLength(){
        return pkvMaxLength;
    }

    public ProductKeyView setPkvTextStyle(int textStyle) {
        this.pkvTextStyle = textStyle;
        try {
            if (pkvTextStyle == BOLD_ITALIC) {
                setStyleForEditView(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
            } else if (pkvTextStyle == ITALIC) {
                setStyleForEditView(Typeface.defaultFromStyle(Typeface.ITALIC));
            } else if (pkvTextStyle == BOLD) {
                setStyleForEditView(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                setStyleForEditView(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void setStyleForEditView(Typeface typeface) {
        editText1.setTypeface(typeface);
        editText2.setTypeface(typeface);
        editText3.setTypeface(typeface);
        editText4.setTypeface(typeface);
    }

    public String getProductKeyText() {
        String value = editText1.getText().toString() + editText2.getText().toString() + editText3.getText().toString() + editText4.getText().toString();
        return value;
    }


    public void setProductKeyText(String text) {
        if (text.trim().contains("/")) {
            String[] updatedText = text.split("/");
            if (updatedText.length == 4) {
                editText1.setText(updatedText[0]);
                editText2.setText(updatedText[1]);
                editText3.setText(updatedText[2]);
                editText4.setText(updatedText[3]);
            }
        } else if (text.contains("-")) {
            String[] updatedText = text.split("-");
            if (updatedText.length == 4) {
                editText1.setText(updatedText[0]);
                editText2.setText(updatedText[1]);
                editText3.setText(updatedText[2]);
                editText4.setText(updatedText[3]);
            }
        } else {
            String[] updatedText = text.split("\\s");
            if (updatedText.length == 4) {
                editText1.setText(updatedText[0]);
                editText2.setText(updatedText[1]);
                editText3.setText(updatedText[2]);
                editText4.setText(updatedText[3]);
            }
        }
    }

    public void clearProductKeyText() {
        editText1.getText().clear();
        editText2.getText().clear();
        editText3.getText().clear();
        editText4.getText().clear();
    }

    public boolean isValidated() {
        boolean isValidated = false;
        if (!editText1.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty() && !editText3.getText().toString().isEmpty() && !editText4.getText().toString().isEmpty()) {
            if (editText1.getText().length() == pkvMaxLength && editText2.getText().length() == pkvMaxLength && editText3.getText().length() == pkvMaxLength && editText4.getText().length() == pkvMaxLength) {
                isValidated = true;
            }
        }
        return isValidated;
    }

    private int getColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().getColor(id);
        } else {
            return getResources().getColor(id);
        }
    }
}
