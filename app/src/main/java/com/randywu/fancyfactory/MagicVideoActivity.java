package com.randywu.fancyfactory;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

import java.io.File;


public class MagicVideoActivity extends AppCompatActivity
        implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnVideoSizeChangedListener,
        SurfaceHolder.Callback {

    private static final String TAG = MagicVideoActivity.class.getSimpleName();

    private MediaPlayer mMediaPlayer = null;
    private String mSourcePath = null;

    // UI
    private SurfaceView mSurfaceView = null;
    private SurfaceHolder mSurfaceHolder = null;

    private boolean mInitialDone = false;
    private boolean mSurfaceCreated = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_magic_video);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnVideoSizeChangedListener(this);

        if (TextUtils.isEmpty(mSourcePath)) {
            mSourcePath = getDataSourcePath();
        }
    }

    private void play() {
        try {
            mMediaPlayer.setDataSource(mSourcePath);
        } catch(Exception e) {
            Log.e(TAG, "setDataSource error " + e.toString());
            e.printStackTrace();
        }

        mMediaPlayer.prepareAsync();
    }

    private static String getDataSourcePath() {
        String videoPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).getAbsolutePath()+"/100MEDIA/VIDEO0001.mp4";
        Log.d(TAG, "videoPath="+videoPath);

        File videoFile = new File(videoPath);
        if (videoFile.exists()) {
            Log.d(TAG, "source file exist");
            return videoPath;
        } else {
            Log.d(TAG, "source path doesn't correct");
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_magic_video, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion");

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "onPrepared");

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.d(TAG, "onVideoSizeChanged width="+width+", height="+height);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");

        mMediaPlayer.pause();
        mMediaPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");

        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        mMediaPlayer.setDisplay(mSurfaceHolder);
        play();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");

    }
}
