package com.randywu.fancyfactory.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class BallSurfaceView extends SurfaceView
        implements View.OnLongClickListener, View.OnTouchListener {

    private static final String TAG = BallSurfaceView.class.getSimpleName();

    private SurfaceHolder mViewHolder;
    private BallViewHolderCallback mViewHolderCb;
    private BallRunnable mRunnable;

    private boolean mViewCreatedDone = false;

//    private ArrayList<Ball> mBallList;
    private CopyOnWriteArrayList<Ball> mBallList;

    public BallSurfaceView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BallSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BallSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        Log.d(TAG, "init");

        mViewHolderCb = new BallViewHolderCallback();
        mRunnable = new BallRunnable();

        mViewHolder = getHolder();
        mViewHolder.addCallback(mViewHolderCb);
        setOnLongClickListener(this);
        setOnTouchListener(this);

//        mBallList = new ArrayList<Ball>();
        mBallList = new CopyOnWriteArrayList<Ball>();
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG, "draw");
        super.draw(canvas);

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (MotionEvent.ACTION_DOWN == action) {
            Log.d(TAG, "ACTION_DOWN");
            float x = event.getX();
            float y = event.getY();
            Ball ball = new Ball(100, Color.GREEN, x, y);
            mBallList.add(ball);

        }
        return false;
    }

    private class BallViewHolderCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated");
            mViewCreatedDone = true;
            new Thread(mRunnable).start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "surfaceChanged");
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "surfaceDestroyed");
            mViewCreatedDone = false;
        }
    }

    private class BallRunnable implements Runnable {

        private Paint paint;
        public BallRunnable() {
            paint = new Paint();
        }

        @Override
        public void run() {
            while(mViewCreatedDone) {
                Log.d(TAG,"run");
                Canvas canvas = null;

                try {
                    canvas = mViewHolder.lockCanvas();

                    paint.setColor(Color.YELLOW);
                    Rect rect = new Rect(0, 0, getWidth(), getHeight());
                    canvas.drawRect(rect, paint);

                    for(Ball ball : mBallList) {

                        paint.setColor(ball.color);
                        canvas.drawOval(ball.rect, paint);

                        ball.move();
                        ball.touchBound(getWidth(), getHeight());
                    }


                    Thread.sleep(10);
                } catch (Exception e) {
                    Log.w(TAG, "running exception = " + e.toString());
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        Log.d(TAG, "run finally");
                        mViewHolder.unlockCanvasAndPost(canvas);
                    }
                }

            }
        }
    }

    private static class Ball {
        public int radius;
        public int color;
        public RectF rect;
        public int dx = DELTA_X;
        public int dy = DELTA_Y;

        public static final float INIT_X = 0;
        public static final float INIT_Y = 0;
        public static final int DELTA_X = 10;
        public static final int DELTA_Y = 10;

        public Ball(){
            radius = 100;
            color = Color.BLUE;
            rect = new RectF(INIT_X, INIT_Y, 2*radius, 2*radius);
        }

        public Ball(int radius, int color, float initX, float initY) {
            this.radius = radius;
            this.color = color;
            rect = new RectF(initX, initY, 2*radius, 2*radius);
        }


        public void move() {
            int newLeft = (int)rect.left + dx;
            int newTop = (int)rect.top + dy;
            setCenter(newLeft, newTop);
        }

        private void setCenter(int left, int top) {
            rect.set(left, top, 2*radius + left, 2*radius + top);
            //Log.d(TAG, "left="+rect.left+", top="+rect.top+", right="+rect.right+", bottom="+rect.bottom);
        }

        public void touchBound(int width, int height) {
            //边界判断
            if (rect.left <= 0 || rect.right >= width) {
                dx *= -1;
            }
            if (rect.top <= 0 || rect.bottom >= height) {
                dy *= -1;
            }
        }
    }
}
