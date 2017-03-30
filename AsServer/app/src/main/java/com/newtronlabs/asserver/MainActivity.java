package com.newtronlabs.asserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SimpleServerTask.SimpleServerListener
{

    public static final int APP_SOCKET_SERVER_PORT = 4546;

    private TextView mContentView;
    private Button mControlBtn;
    private SimpleServerTask mTask;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentView = (TextView)findViewById(R.id.content_sent_view);

        mControlBtn = (Button)findViewById(R.id.btn_start);
        mControlBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.btn_start)
        {
            mContentView.setText("");
            Button controlBtn = (Button)view;
            //controlBtn.setId(R.id.btn_idle);
            //controlBtn.setText(R.string.listening);

            controlBtn.setId(R.id.btn_close);
            controlBtn.setText(R.string.btn_stop);

            // Start Listening on port: 4546
            mTask = new SimpleServerTask(4546, this);
            mTask.execute("Newtron Labs Libraries:",
                    "(Ib) IPC Event Bus",
                    "(Wd) Watchdog",
                    "(Sl) Specialized Logger",
                    "(Bf) Bluetooth Filter",
                    "(Sm) Shared Memory",
                    "(As) App Socket");
        }
        else if(view.getId() == R.id.btn_close)
        {
            Button controlBtn = (Button)view;
            controlBtn.setId(R.id.btn_start);
            controlBtn.setText(R.string.btn_start);
            mTask.cancel(true);
        }
    }

    @Override
    public void onUpdate(String update)
    {
        mContentView.append(update + "\n");
    }

    @Override
    public void onClosed()
    {
        mContentView.append("Connection Closed.");
        mControlBtn.setId(R.id.btn_start);
        mControlBtn.setText(R.string.btn_start);
    }
}
