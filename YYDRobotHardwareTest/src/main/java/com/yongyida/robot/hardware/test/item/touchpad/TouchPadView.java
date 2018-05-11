package com.yongyida.robot.hardware.test.item.touchpad;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by HuangXiangXiang on 2018/2/28.
 */
public class TouchPadView extends View {


    private final static int TEST_BLUE = 0xFF63B8FF ;

    private float mAverageHeight;
    private float mAverageWidth;

    private float mX, mY;
    private Path mPath;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mBitmapPaint;
    private Paint mBackGroudPaint, mLinePaint, mPaint;
    private RectF mRf = new RectF();
    private float[] mVertBaseline = new float[13];
    private float[] mHorBaseline = new float[19];
    private static final float TOUCH_TOLERANCE = 4;
    private ArrayList mLeftList = new ArrayList<Integer>();
    private ArrayList mRightList = new ArrayList<Integer>();
    private ArrayList mTopList = new ArrayList<Integer>();
    private ArrayList mBottomList = new ArrayList<Integer>();

    private ArrayList mVertList = new ArrayList<Integer>();
    private ArrayList mHorList_first = new ArrayList<Integer>();
    private ArrayList mHorList_second = new ArrayList<Integer>();
    private ArrayList mHorList_third = new ArrayList<Integer>();
    private ArrayList mHorList_fourth = new ArrayList<Integer>();
    private ArrayList mHorList_fifth = new ArrayList<Integer>();
    private ArrayList mHorList_sixth = new ArrayList<Integer>();
    private ArrayList mHorList_seventh = new ArrayList<Integer>();
    private ArrayList mHorList_eighth = new ArrayList<Integer>();
    private ArrayList mHorList_nineth = new ArrayList<Integer>();
    private ArrayList mHorList_tenth = new ArrayList<Integer>();

    public TouchPadView(Context context) {
        super(context);
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mBackGroudPaint = new Paint();
        mBackGroudPaint.setColor(TEST_BLUE);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.BLACK);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null == mBitmap) {
            mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }
        canvas.drawColor(Color.WHITE);

        if (0 == mAverageHeight) {
            mAverageHeight = getMeasuredHeight() / 10;
            mAverageWidth = getMeasuredWidth() / 18;
            for (int i = 0; i < 11; i++) {
                mVertBaseline[i] = i * mAverageHeight;
            }
            for (int i = 0; i < 19; i++) {
                mHorBaseline[i] = i * mAverageWidth;
            }
            mHorBaseline[18] = getMeasuredWidth();

        }
        for (int i = 1; i < 10; i++) {
            canvas.drawLine(0, mAverageHeight*i, getMeasuredWidth(), mAverageHeight*i, mLinePaint);
        }
        for (int i = 1; i < 18; i++) {
            canvas.drawLine(mAverageWidth*i, 0, mAverageWidth*i, getMeasuredWidth(), mLinePaint);
        }

        if (!mHorList_first.isEmpty()) {
            for (int i = 0; i < mHorList_first.size(); i++) {
                mRf.set(mHorBaseline[((Integer) mHorList_first.get(i)).intValue()], 0,
                        mHorBaseline[((Integer) mHorList_first.get(i)).intValue() + 1], mAverageHeight);
                canvas.drawRect(mRf, mBackGroudPaint);
            }
        }

        if (!mHorList_second.isEmpty()) {
            for (int i = 0; i < mHorList_second.size(); i++) {
                mRf.set(mHorBaseline[((Integer) mHorList_second.get(i)).intValue()], mAverageHeight,
                        mHorBaseline[((Integer) mHorList_second.get(i)).intValue() + 1], mAverageHeight*2);
                canvas.drawRect(mRf, mBackGroudPaint);
            }
        }

        if (!mHorList_third.isEmpty()) {
            for (int i = 0; i < mHorList_third.size(); i++) {
                mRf.set(mHorBaseline[((Integer) mHorList_third.get(i)).intValue()], mAverageHeight*2,
                        mHorBaseline[((Integer) mHorList_third.get(i)).intValue() + 1], mAverageHeight*3);
                canvas.drawRect(mRf, mBackGroudPaint);
            }
        }

        if (!mHorList_fourth.isEmpty()) {
            for (int i = 0; i < mHorList_fourth.size(); i++) {
                mRf.set(mHorBaseline[((Integer) mHorList_fourth.get(i)).intValue()], mAverageHeight*3,
                        mHorBaseline[((Integer) mHorList_fourth.get(i)).intValue() + 1], mAverageHeight*4);
                canvas.drawRect(mRf, mBackGroudPaint);
            }
        }

        if (!mHorList_fifth.isEmpty()) {
            for (int i = 0; i < mHorList_fifth.size(); i++) {
                mRf.set(mHorBaseline[((Integer) mHorList_fifth.get(i)).intValue()], mAverageHeight*4,
                        mHorBaseline[((Integer) mHorList_fifth.get(i)).intValue() + 1], mAverageHeight*5);
                canvas.drawRect(mRf, mBackGroudPaint);
            }
        }


        if (!mHorList_sixth.isEmpty()) {
            for (int i = 0; i < mHorList_sixth.size(); i++) {
                mRf.set(mHorBaseline[((Integer) mHorList_sixth.get(i)).intValue()], mAverageHeight*5,
                        mHorBaseline[((Integer) mHorList_sixth.get(i)).intValue() + 1], mAverageHeight*6);
                canvas.drawRect(mRf, mBackGroudPaint);
            }
        }

        if (!mHorList_seventh.isEmpty()) {
            for (int i = 0; i < mHorList_seventh.size(); i++) {
                mRf.set(mHorBaseline[((Integer) mHorList_seventh.get(i)).intValue()], mAverageHeight*6,
                        mHorBaseline[((Integer) mHorList_seventh.get(i)).intValue() + 1], mAverageHeight*7);
                canvas.drawRect(mRf, mBackGroudPaint);
            }
        }

        if (!mHorList_eighth.isEmpty()) {
            for (int i = 0; i < mHorList_eighth.size(); i++) {
                mRf.set(mHorBaseline[((Integer) mHorList_eighth.get(i)).intValue()], mAverageHeight*7,
                        mHorBaseline[((Integer) mHorList_eighth.get(i)).intValue() + 1], mAverageHeight*8);
                canvas.drawRect(mRf, mBackGroudPaint);
            }
        }

        if (!mHorList_nineth.isEmpty()) {
            for (int i = 0; i < mHorList_nineth.size(); i++) {
                mRf.set(mHorBaseline[((Integer) mHorList_nineth.get(i)).intValue()], mAverageHeight*8,
                        mHorBaseline[((Integer) mHorList_nineth.get(i)).intValue() + 1], mAverageHeight*9);
                canvas.drawRect(mRf, mBackGroudPaint);
            }
        }

        if (!mHorList_tenth.isEmpty()) {
            for (int i = 0; i < mHorList_tenth.size(); i++) {
                mRf.set(mHorBaseline[((Integer) mHorList_tenth.get(i)).intValue()], mAverageHeight*9,
                        mHorBaseline[((Integer) mHorList_tenth.get(i)).intValue() + 1], mAverageHeight*10);
                canvas.drawRect(mRf, mBackGroudPaint);
            }
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath(mPath, mPaint);

    }

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        if (null != mCanvas) {
            mPath.lineTo(mX, mY);
            mCanvas.drawPath(mPath, mPaint);
            mPath.reset();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (y < mAverageHeight) {
            int i = gnBinarySearch(x, mHorBaseline, 0, 9);
            if (-1 != i && !mHorList_first.contains(i)) {
                mHorList_first.add(i);
            }

        }
        else if ((y >= mAverageHeight)&&(y < mAverageHeight*2)){
            int i = gnBinarySearch(x, mHorBaseline, 0, 9);
            if (-1 != i && !mHorList_second.contains(i)) {
                mHorList_second.add(i);
            }

        }
        else if ((y >= mAverageHeight*2)&&(y < mAverageHeight*3)){
            int i = gnBinarySearch(x, mHorBaseline, 0, 9);
            if (-1 != i && !mHorList_third.contains(i)) {
                mHorList_third.add(i);
            }

        }
        else if ((y >= mAverageHeight*3)&&(y < mAverageHeight*4)){
            int i = gnBinarySearch(x, mHorBaseline, 0, 9);
            if (-1 != i && !mHorList_fourth.contains(i)) {
                mHorList_fourth.add(i);
            }

        }

        else if ((y >= mAverageHeight*4)&&(y < mAverageHeight*5)){
            int i = gnBinarySearch(x, mHorBaseline, 0, 9);
            if (-1 != i && !mHorList_fifth.contains(i)) {
                mHorList_fifth.add(i);
            }

        }

        else if ((y >= mAverageHeight*5)&&(y < mAverageHeight*6)){
            int i = gnBinarySearch(x, mHorBaseline, 0, 9);
            if (-1 != i && !mHorList_sixth.contains(i)) {
                mHorList_sixth.add(i);
            }

        }

        else if ((y >= mAverageHeight*6)&&(y < mAverageHeight*7)){
            int i = gnBinarySearch(x, mHorBaseline, 0, 9);
            if (-1 != i && !mHorList_seventh.contains(i)) {
                mHorList_seventh.add(i);
            }

        }

        else if ((y >= mAverageHeight*7)&&(y < mAverageHeight*8)){
            int i = gnBinarySearch(x, mHorBaseline, 0, 9);
            if (-1 != i && !mHorList_eighth.contains(i)) {
                mHorList_eighth.add(i);
            }

        }

        else if ((y >= mAverageHeight*8)&&(y < mAverageHeight*9)){
            int i = gnBinarySearch(x, mHorBaseline, 0, 9);
            if (-1 != i && !mHorList_nineth.contains(i)) {
                mHorList_nineth.add(i);
            }

        }

        else if ((y >= mAverageHeight*9)&&(y < mAverageHeight*10)){
            int i = gnBinarySearch(x, mHorBaseline, 0, 9);
            if (-1 != i && !mHorList_tenth.contains(i)) {
                mHorList_tenth.add(i);
            }
        }


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                if (180 == mHorList_first.size() + mHorList_second.size() + mHorList_third.size() + mHorList_fourth.size() + mHorList_fifth.size()
                        + mHorList_sixth.size() + mHorList_seventh.size() + mHorList_eighth.size() + mHorList_nineth.size() + mHorList_tenth.size()) {

//                    mTouchHandler.sendEmptyMessage(RIGHT_MESSAGE);
                    if(onTouchCompleteListener != null){

                        onTouchCompleteListener.onTouchComplete();
                    }

                }
                break;
        }
        return true;

    }
    public int gnBinarySearch(float elem, float[] array, int low, int high) {
        for (int i = 0; i < array.length - 1; i++) {
            if (elem >= array[i] && elem < array[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    //
    private OnTouchCompleteListener onTouchCompleteListener ;
    public void setOnTouchCompleteListener(OnTouchCompleteListener onTouchCompleteListener) {
        this.onTouchCompleteListener = onTouchCompleteListener;
    }

    public interface OnTouchCompleteListener {

        void onTouchComplete() ;
    }

    /**
     * 重置
     * */
    public void reset(){

        mHorList_first.clear();
        mHorList_second.clear();
        mHorList_third.clear();
        mHorList_fourth.clear();
        mHorList_fifth.clear();
        mHorList_sixth.clear();
        mHorList_seventh.clear();
        mHorList_eighth.clear();
        mHorList_nineth.clear();
        mHorList_tenth.clear();

        if(mBitmap!= null){

            mBitmap.recycle();
            mBitmap = null ;
        }

        invalidate();
    }

}
