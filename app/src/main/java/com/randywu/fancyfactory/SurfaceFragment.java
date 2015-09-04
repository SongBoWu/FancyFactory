package com.randywu.fancyfactory;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.randywu.fancyfactory.view.BallSurfaceView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class SurfaceFragment extends PlaceholderFragment {

    private static final String TAG = SurfaceFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    private BallSurfaceView mBallSurfaceView;

    public SurfaceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCrateView");
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_surface, container, false);
//        mSurfaceView = (SurfaceView) view.findViewById(R.id.surface_container);
//        mSurfaceHolder = mSurfaceView.getHolder();
//        mSurfaceHolder.addCallback(this);

        mBallSurfaceView = new BallSurfaceView(getContext());

        for(int i=0;i<500000;i++) {
            arrayList.add(i);
        }

        new Thread(r1).start();
        new Thread(r2).start();

        return mBallSurfaceView;
    }


    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    private boolean attach = false;
    private Object object = new Object();

    private synchronized void test(String name) {
        Log.d(TAG, name + " arrayList.size=" + arrayList.size() + ", top=" + arrayList.get(0));
        arrayList.remove(0);
        Log.d(TAG, name + " after remove, top=" + arrayList.get(0));

        AtomicBoolean test = new AtomicBoolean();
    }

    private Runnable r1 = new Runnable() {
        @Override
        public void run() {
            while(attach) {

                test("GG1");

//                synchronized (object) {
//                    Log.d(TAG, "r1 arrayList.size=" + arrayList.size() + ", top=" + arrayList.get(0));
//                    arrayList.remove(0);
//                    Log.d(TAG, "r1 after remove, top=" + arrayList.get(0));
//                }

//                try {
//                    Thread.sleep(10);
//                } catch (Exception e) {
//                    Log.d(TAG, "r1 exception -> e=" + e.toString());
//                    e.printStackTrace();
//                }
            }
        }
    };

    private Runnable r2 = new Runnable() {
        @Override
        public void run() {
            while(attach) {

                test("GGGGGGGGGDAAAAAA");

//                synchronized (object) {
//                    Log.d(TAG, "r2222222 arrayList.size=" + arrayList.size() + ", top=" + arrayList.get(0));
//                    arrayList.remove(0);
//                    Log.d(TAG, "r2222222 after remove, top=" + arrayList.get(0));
//                }

//                try {
//                    Thread.sleep(10);
//                } catch (Exception e) {
//                    Log.d(TAG, "r2 exception -> e=" + e.toString());
//                    e.printStackTrace();
//                }
            }
        }
    };



    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach");
        super.onAttach(activity);
        attach = true;
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
        super.onDetach();
        attach = false;
    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        Log.d(TAG, "surfaceCreated");
//
//        new Thread(){
//            public void run() {
//                while(true){
//                    //1.这里就是核心了， 得到画布 ，然后在你的画布上画出要显示的内容
//                    Canvas c = mSurfaceHolder.lockCanvas(new Rect(0, 0, 200, 200));
//                    //2.开画
//                    Paint p =new Paint();
//                    p.setColor(Color.rgb((int) (Math.random() * 255),
//                            (int) (Math.random() * 255), (int) (Math.random() * 255)));
//                    Rect aa  =  new Rect(
//                            (int)(Math.random() * 100)
//                            ,(int)(Math.random() * 100)
//                            ,(int)(Math.random() * 500)
//                            ,(int)(Math.random() * 500) );
//                    c.drawRect(aa, p);
//                    //3. 解锁画布   更新提交屏幕显示内容
//                    mSurfaceHolder.unlockCanvasAndPost(c);
//                    try {
//                        Thread.sleep(1000);
//
//                    } catch (Exception e) {
//                    }
//                }
//            };
//        }.start();
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        Log.d(TAG, "surfaceChanged");
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        Log.d(TAG, "surfaceDestroyed");
//
//    }
}
