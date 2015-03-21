package com.healthhelp;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.Selection;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by bhargavsarvepalli on 21/03/15.
 */
public class AutoCompleteTextViewExtended extends AutoCompleteTextView{
    public AutoCompleteTextViewExtended(Context context) {
        super(context);
    }
    public AutoCompleteTextViewExtended(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoCompleteTextViewExtended(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoCompleteTextViewExtended(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void replaceText(CharSequence text){
        clearComposingText();
        // make sure we keep the caret at the end of the text view
        Editable spannable = getText();
        Selection.setSelection(spannable, spannable.length());
    }

}
