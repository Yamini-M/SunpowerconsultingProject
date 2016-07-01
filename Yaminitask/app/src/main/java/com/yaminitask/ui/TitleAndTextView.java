package com.yaminitask.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaminitask.R;

/**
 * Created by jampalar on 01/07/16.
 */
public class TitleAndTextView extends LinearLayout {

    private TextView title, subText;

    public TitleAndTextView(Context context) {
        super(context);
    }

    public TitleAndTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleAndTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        title = (TextView) findViewById(R.id.header);
        subText = (TextView) findViewById(R.id.text);
    }

    public void populateWith(String titleStr, String textStr) {
        title.setText(titleStr);
        subText.setText(textStr);
    }

}
