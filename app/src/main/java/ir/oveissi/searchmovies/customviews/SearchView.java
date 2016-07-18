package ir.oveissi.searchmovies.customviews;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.oveissi.searchmovies.R;

public class SearchView extends RelativeLayout {

    public Context mContext;
    ImageButton imClearTextBtn;
    EditText etTerms;
    performSearchListener listener;


    public SearchView(Context context) {
        super(context);
        initializeViews(context,null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context,attrs);
    }
    public SearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context,attrs);
    }

    public void setListener(performSearchListener listener) {
        this.listener = listener;
    }

    private void initializeViews(Context context,AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_search, this);
        this.mContext=context;
        imClearTextBtn=(ImageButton)findViewById(R.id.imClearTextBtn);
        etTerms=(EditText)findViewById(R.id.etTerms);


        etTerms.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(((Activity)mContext).getCurrentFocus()!=null && ((Activity)mContext).getCurrentFocus() instanceof EditText){
                        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etTerms.getWindowToken(), 0);
                    }
                    if(listener!=null)
                        listener.performSearch(etTerms.getText().toString());
                    return true;
                }
                return false;
            }
        });

        etTerms.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imClearTextBtn.setVisibility(View.VISIBLE);
            }
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    imClearTextBtn.setVisibility(View.GONE);
                } else{
                    imClearTextBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        imClearTextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etTerms.setText("");
            }
        });



    }


    public String getValue()
    {
        return etTerms.getText().toString();
    }


    public void setText(String text)
    {
        etTerms.setText(text);
    }
    public interface performSearchListener
    {
        public void performSearch(String terms);
    }
    
}
