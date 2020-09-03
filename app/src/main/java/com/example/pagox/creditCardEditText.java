package com.example.pagox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.pagox.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class creditCardEditText extends AppCompatEditText {
    final int mDefaultDrawableResId = R.drawable.creditcard;
    int mCurrentDrawableResId = 0;
    Drawable mCurrentDrawable;

    public creditCardEditText(Context context) {
        super(context);
    }
    public creditCardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public creditCardEditText(Context context,
                              AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private SparseArray<Pattern> mCCPatterns = null;
    private void init() {
        if (mCCPatterns == null) {
            mCCPatterns = new SparseArray<>();
            // With spaces for credit card masking
            mCCPatterns.put(R.drawable.visa, Pattern.compile(
                    "^4[0-9]{2,12}(?:[0-9]{3})?$"));
            mCCPatterns.put(R.drawable.mastercard, Pattern.compile(
                    "^5[1-5][0-9]{1,14}$"));
            mCCPatterns.put(R.drawable.amex, Pattern.compile(
                    "^3[47][0-9]{1,13}$"));
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (mCCPatterns == null) {
            init();
        }
        int mDrawableResId = 0;
        for (int i = 0; i < mCCPatterns.size(); i++) {
            int key = mCCPatterns.keyAt(i);
            // get the object by the key.
            Pattern p = mCCPatterns.get(key);
            Matcher m = p.matcher(text);
            if (m.find()) {
                mDrawableResId = key;
                break;
            }
        }

        if (mDrawableResId > 0 && mDrawableResId !=
                mCurrentDrawableResId) {
            mCurrentDrawableResId = mDrawableResId;
        } else if (mDrawableResId == 0) {
            mCurrentDrawableResId = mDefaultDrawableResId;
        }
        mCurrentDrawable = getResources().getDrawable(mCurrentDrawableResId);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        // draw some text using FILL style
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setTextSize(75);
        canvas.drawText(String.valueOf(getText()), 150, 210, paint);
        if (mCurrentDrawable == null) {
            return;
        }
        // right offset for showing errors in the EditText
        int rightOffset = 0;
        if (getError() != null && getError().length() > 0) {
            rightOffset = (int) getResources().getDisplayMetrics()
                    .density;
        }
        int right = getWidth() - getPaddingRight() - rightOffset;

        int top = getPaddingTop();
        int bottom = getHeight() - getPaddingBottom();
        float ratio = (float) mCurrentDrawable.getIntrinsicWidth() /
                (float) mCurrentDrawable.getIntrinsicHeight();
        rightOffset = 0;
        if (getError() != null && getError().length() > 0) {
            rightOffset = (int) getResources().getDisplayMetrics()
                    .density;
        }

        right = getWidth() - getPaddingRight() - rightOffset;

        top = getPaddingTop();
        bottom = getHeight() - getPaddingBottom();
        ratio = (float) mCurrentDrawable.getIntrinsicWidth() /
                (float) mCurrentDrawable.getIntrinsicHeight();
        //if images are the correct size.
        //int left = right - mCurrentDrawable.getIntrinsicWidth();
        //scale image depending on height available.
        int left = (int) (right - ((bottom - top) * ratio));
        mCurrentDrawable.setBounds(left, top, right, bottom);

        mCurrentDrawable.draw(canvas);
}
}
