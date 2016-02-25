package com.example.fenghao.towbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by fenghao on 2016/2/24.
 */
public class TowButton extends LinearLayout {

    /**
     * 默认button的宽度
     * 单位dp
     */
    private int btnDefalutWidth; //dp

    //设置最小值
    private int minNumber;

    //设置最大值(不要超过999 否则布局可能会出错)
    private int maxNumber;

    //减button
    private Button cutButton;
    //加button
    private Button addButton;
    //中间edit
    private EditText text;

    //中间edit背景
    private Drawable textBackground;
    //字体大小
    private float textSize;
    //字体颜色
    private int textColor;
    //edit文字
    private String textStr;
    //光标是否显示
    private Boolean textCursorVisible;
    //减button背景
    private Drawable leftBackground;
    //加button背景
    private Drawable rightBackground;


    private LayoutParams leftParams;
    private LayoutParams rigthParams;
    private LayoutParams centerParams;


    private Context mContext;

    public TowButton(Context context) {
        this(context, null);
    }

    public TowButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TowButton);
        textBackground = ta.getDrawable(R.styleable.TowButton_textBackground);
        textCursorVisible = ta.getBoolean(R.styleable.TowButton_textCursorVisible, false);
        textSize = ta.getDimension(R.styleable.TowButton_textSize, 10);
        textColor = ta.getColor(R.styleable.TowButton_textColor, Color.BLACK);
        textStr = ta.getString(R.styleable.TowButton_text);
        btnDefalutWidth = (int) ta.getDimension(R.styleable.TowButton_btnWidth, 20);
        minNumber = (int) ta.getFloat(R.styleable.TowButton_minNumber, 1);
        maxNumber = (int) ta.getFloat(R.styleable.TowButton_maxNumber, 99);

        leftBackground = ta.getDrawable(R.styleable.TowButton_leftBackground);

        rightBackground = ta.getDrawable(R.styleable.TowButton_rightBackground);

        ta.recycle();
        this.setGravity(Gravity.CENTER);

        int width = dp2px(context, btnDefalutWidth);


        cutButton = new Button(context);
        addButton = new Button(context);
        text = new EditText(context);

        cutButton.setBackground(leftBackground);
        addButton.setBackground(rightBackground);

        text.setBackground(textBackground);
        text.setTextSize(textSize);
        text.setTextColor(textColor);
        text.setText(textStr);
        text.setCursorVisible(textCursorVisible);

        leftParams = new LayoutParams(width, width);
        addView(cutButton, leftParams);

        //edit宽度为默认width的2.8倍  高度为2倍
        centerParams = new LayoutParams((int) (width * 2.8), width * 2);
        text.setSingleLine(true);
        text.setGravity(Gravity.CENTER);
        //设置只能输入数字
        text.setInputType(InputType.TYPE_CLASS_NUMBER );
        addView(text, centerParams);

        rigthParams = new LayoutParams(width, width);
        addView(addButton, rigthParams);
        listener();
    }

    /**
     * 加减btn监听
     *
     */
    public void listener(){
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > getMaxNumberLength()){
                    text.setText(maxNumber + "");
                    Toast.makeText(mContext, "已到达最大值", Toast.LENGTH_LONG).show();
                }else if (s.length() == 0){
                    text.setText(minNumber + "");
                    Toast.makeText(mContext, "不能再小了", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        cutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(text.getText().toString());
                if (num > minNumber){
                    --num;
                    text.setText(num + "");
                }else{
                    text.setText(minNumber + "");
                    Toast.makeText(mContext, "不能再小了", Toast.LENGTH_LONG).show();
                }

            }
        });
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(text.getText().toString());
                if (num < maxNumber){
                    ++num;
                    text.setText(num + "");
                }else {
                    text.setText(maxNumber + "");
                    Toast.makeText(mContext, "已到达最大值", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    /**
     * dp转px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * 返回eidt的数字
     * @return
     */
    public int getTextNum(){
        return Integer.parseInt(text.getText().toString());
    }

    /**
     * 得到最大数字的长度  例如9以内是1  99以内是2，以此内推
     * @return
     */
    public int getMaxNumberLength(){
        int maxNum = maxNumber;
        int count = 1;
        while (maxNum / 10 >= 10){
            count += 1;
            maxNum = maxNum / 10;
        }
        count = count + 1;
        return count;
    }
}
