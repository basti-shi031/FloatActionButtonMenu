package com.basti.floatingactionbuttonmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

/**
 * Created by SHIBW-PC on 2016/1/19.
 */
public class FabMenu extends LinearLayout {

    private FloatingActionButton rootFab;
    //private List<FloatingActionButton> childFabs;
    private ColorStateList rootBackgroundTint;
    private int rootRippleColor;
    private Drawable rootSrc;
    private float childMargin;
    private OnItemClick onItemClickListener;
    private boolean isOpen = false;
    private int count = 0;

    public FabMenu(Context context) {
        this(context, null);
    }

    public FabMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FabMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
    }
    //==============
    //以下为外部调用方法
    //==============

    public void setOnItemClick(OnItemClick onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addFab(ColorStateList childBackground, Drawable childRootSrc) {
        final int index = count;

        FloatingActionButton floatingActionButton = new FloatingActionButton(getContext());
        floatingActionButton.setBackgroundTintList(childBackground);
        floatingActionButton.setRippleColor(rootRippleColor);
        floatingActionButton.setImageDrawable(childRootSrc);
        floatingActionButton.setVisibility(GONE);

        floatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onChildFabChild(index);
                }
            }
        });

        addView(floatingActionButton);
        count++;

        //childFabs.add(floatingActionButton);
    }

    //==============
    //以下为内部调用方法
    //==============

    private void init() {
        setOrientation(VERTICAL);

        //初始化根Fab
        initRootFab();

    }

    private void initRootFab() {
        rootFab = new FloatingActionButton(getContext());
        rootFab.setBackgroundTintList(rootBackgroundTint);
        rootFab.setRippleColor(rootRippleColor);
        rootFab.setImageDrawable(rootSrc);

        addView(rootFab);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FabMenu);

        rootBackgroundTint = ta.getColorStateList(R.styleable.FabMenu_root_background_tint);
        rootRippleColor = ta.getColor(R.styleable.FabMenu_root_ripple, Color.parseColor("#33728dff"));
        rootSrc = ta.getDrawable(R.styleable.FabMenu_root_src);
        childMargin = ta.getDimension(R.styleable.FabMenu_item_margin, Utils.dp2px(getContext(), 16));

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount();
        int desireHeight = 0;
        int desireWidth = 0;

        for (int i = 0; i < count; i++) {

            FloatingActionButton childView = (FloatingActionButton) getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            desireHeight = desireHeight + childView.getMeasuredHeight();
            desireWidth = childView.getMeasuredWidth();

        }

        desireHeight += (int) (count * childMargin);
        setMeasuredDimension(resolveSize(desireWidth, widthMeasureSpec), resolveSize(desireHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            //定位主按钮
            layoutRootFab();

            layoutChildeFab();
        }

    }

    private void layoutChildeFab() {
        int childCount = getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            FloatingActionButton child = (FloatingActionButton) getChildAt(i + 1);

            int width = child.getMeasuredWidth();
            int height = child.getMeasuredWidth();

            int l = 0;
            int t = (int) (getMeasuredHeight() - height * (i + 2) - childMargin * (i + 1));
            int b = (int) (getMeasuredHeight() - height * (i + 1) - childMargin * (i + 1));
            int r = getMeasuredWidth();

            child.layout(l, t, r, b);
        }
    }

    private void layoutRootFab() {
        rootFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onRootFabClick();
                    isOpen = !isOpen;
                    operateChildView(isOpen);
                }
            }
        });

        int width = rootFab.getMeasuredWidth();
        int height = rootFab.getMeasuredWidth();

        int l = 0;
        int t = getMeasuredHeight() - height;
        int b = getMeasuredHeight();
        int r = getMeasuredWidth();

        rootFab.layout(l, t, r, b);
    }


    private void operateChildView(boolean isOpen) {
        int childCount = getChildCount();
        int offset = 150 / childCount;
        if (isOpen) {
            //打开
            for (int i = 0; i < childCount - 1; i++) {
                final FloatingActionButton childView = (FloatingActionButton) getChildAt(i + 1);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(childView, "Y", getMeasuredHeight() - childView.getMeasuredHeight(),
                        (getMeasuredHeight() - childView.getMeasuredHeight() * (i + 2) - childMargin * (i + 1)));
                objectAnimator.setDuration(150).setStartDelay(offset * i);
                objectAnimator.setInterpolator(new AccelerateInterpolator());
                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        childView.setVisibility(VISIBLE);
                    }
                });
                objectAnimator.start();
            }
        } else {
            //关闭
            for (int i = 0; i < childCount - 1; i++) {
                final FloatingActionButton childView = (FloatingActionButton) getChildAt(i + 1);

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(childView, "Y", childView.getY(), getMeasuredHeight() - childView.getMeasuredHeight());
                objectAnimator.setDuration(150).setStartDelay(offset * (childCount - i));
                objectAnimator.setInterpolator(new AccelerateInterpolator());
                objectAnimator.start();
                objectAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        childView.setVisibility(GONE);
                    }
                });
            }
        }

    }
}
