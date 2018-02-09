package com.iesvirgendelcarmen.dam.videos03;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class Videos03 extends AppCompatActivity implements SurfaceHolder.Callback {
    Button grabar, parar, play;
    SurfaceView surfaceView;
    private MediaRecorder grabador;
    private MediaPlayer reproductor;
    private SurfaceHolder surfaceHolder;
    private String ruta;
    private boolean grabando=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos03);

        grabar=(Button)findViewById(R.id.grabar);
        parar=(Button)findViewById(R.id.parar);
        play=(Button)findViewById(R.id.play);
        surfaceView=(SurfaceView)findViewById(R.id.surfaceview);

        surfaceView.getHolder().addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        ruta= Environment.getExternalStorageDirectory()+"/mivideo.mp4";

        grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabador.setAudioSource(MediaRecorder.AudioSource.MIC);
                grabador.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                grabador.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                grabador.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                grabador.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
                grabador.setOutputFile(ruta);
                try {
                    grabador.prepare();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                     e.printStackTrace();
                }
                grabador.start();
                grabar.setEnabled(false);
                parar.setEnabled(true);
                play.setEnabled(false);
                grabando=true;

            }
        });

        parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(grabando){
                    grabador.stop();
                    grabador.reset();
                    grabando=false;
                }else{
                    reproductor.stop();
                    reproductor.reset();
                    play.setText("PLAY");
                }
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(grabador==null){
            grabador=new MediaRecorder();
            grabador.setPreviewDisplay(holder.getSurface());
        }

        if(reproductor==null){
            reproductor=new MediaPlayer();
            reproductor.setDisplay(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        grabador.release();
        reproductor.release();
    }
}
