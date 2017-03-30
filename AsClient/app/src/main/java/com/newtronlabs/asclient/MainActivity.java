package com.newtronlabs.asclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SimpleClientTask.SimpleClientListener
{
    public static final String APP_SOCKET_SERVER_APP_ID="com.newtronlabs.asserver";
    public static final int APP_SOCKET_SERVER_PORT = 4546;

    private TextView mContentView;
    private Button mControlBtn;
    private SimpleClientTask mTask;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentView = (TextView)findViewById(R.id.content_view);

        mControlBtn =(Button)findViewById(R.id.btn_start);
        mControlBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.btn_start)
        {
            mContentView.setText("");
            Button btn = (Button)view;
            btn.setText(R.string.btn_stop);
            btn.setId(R.id.btn_close);
            // Try to connect to server.
            mTask = new SimpleClientTask(this, APP_SOCKET_SERVER_APP_ID, APP_SOCKET_SERVER_PORT, this);
            mTask.execute();
        }
        else if(view.getId() == R.id.btn_close)
        {
            mTask.cancel(true);
            Button btn = (Button)view;
            btn.setText(R.string.btn_start);
            btn.setId(R.id.btn_start);
        }
    }

    @Override
    public void onUpdate(String update)
    {
        mContentView.append(update +"\n");
    }

    @Override
    public void onClosed()
    {
        mContentView.append("Connection Closed.");
        mControlBtn.setId(R.id.btn_start);
        mControlBtn.setText(R.string.btn_start);
    }
}
