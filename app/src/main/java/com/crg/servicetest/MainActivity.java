package com.crg.servicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mButton;
    private Button closeButton;
    private Button addButton;
    private static boolean  mBound = false;
    private LocalService mLocalService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);

        closeButton = (Button) findViewById(R.id.button2);
        closeButton.setOnClickListener(this);

        addButton = (Button) findViewById(R.id.button4);
        addButton.setOnClickListener(this);
    }
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("MainActivity --->", "MainActivity ---> ComponentName name " + name + "IBinder service: " + service);

            //拿到 LocalService 对象
            mLocalService = ((LocalService.LocalBinder)service).getService();
            Toast.makeText(MainActivity.this, "客户端链接上服务端", Toast.LENGTH_LONG).show();
            Log.d("MainActivity --->", "MainActivity ---> mBound " + mBound);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MainActivity --->", "MainActivity ---> ComponentName name " + name);
            mLocalService = null;
            Toast.makeText(MainActivity.this, "客户端和服务端断开", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                bindLocalSertvice();
                break;
            case R.id.button2:
                unBindLocalSertvice();
                break;
            case R.id.button4:
                int result = mLocalService.add(10, 8);
                Log.d("MainActivity --->", "MainActivity ---> result  mLocalService" + mLocalService);
                Log.d("MainActivity --->", "MainActivity ---> result " + result);
                break;
            default:
                break;
        }
    }
    private void bindLocalSertvice(){
            mBound = true;
            bindService(new Intent(this, LocalService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }
    private void unBindLocalSertvice(){
        if (mBound){
            mBound = false;
            unbindService(mServiceConnection);
        }
    }
}
