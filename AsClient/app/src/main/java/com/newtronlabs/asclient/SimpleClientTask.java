package com.newtronlabs.asclient;

import android.content.Context;
import android.os.AsyncTask;

import com.newtronlabs.appsocket.IAppSocket;
import com.newtronlabs.appsocket.socket.AppSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

/**
 * Small sample task for handling some basic communication with
 * a server app.
 */
public class SimpleClientTask extends AsyncTask<String, String, Void>
{
    /**
     * Simple helper listener
     */
    public interface SimpleClientListener
    {
        /**
         * Called to notify update
         * @param update update to notify.
         */
        void onUpdate(String update);

        /**
         * Called when the connection is closed.
         */
        void onClosed();
    }

    private WeakReference<SimpleClientListener> mListener;
    private Context mContext;
    private IAppSocket mSocket;
    private String mRemoteAppId;
    private int mRemoteListeningPort;
    public SimpleClientTask(Context context, String remoteAppId, int port, SimpleClientListener listener)
    {
        mContext = context.getApplicationContext();
        mSocket = new AppSocket();
        mRemoteAppId = remoteAppId;
        mRemoteListeningPort = port;
        mListener = new WeakReference<SimpleClientListener>(listener);
    }

    @Override
    protected void onCancelled()
    {
        super.onCancelled();

        SimpleClientListener listener = mListener.get();

        if(listener != null)
        {
            listener.onClosed();
        }
    }

    @Override
    protected void onProgressUpdate(String... values)
    {
        super.onProgressUpdate(values);

        SimpleClientListener listener = mListener.get();

        if(listener != null)
        {
            listener.onUpdate(values[0]);
        }
    }

    @Override
    protected Void doInBackground(String... strings)
    {
        try
        {
            publishProgress("Connecting to Server...");

            //Attempt to connect to server app.
            mSocket.connect(mContext, mRemoteAppId, mRemoteListeningPort);

            publishProgress("Connected: "+mRemoteAppId);

            BufferedReader reader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            String inputLine;

            // Read from socket.
            while(!isCancelled() && !mSocket.isClosed())
            {
                while((inputLine = reader.readLine())!= null)
                {
                    publishProgress("Received: " + inputLine);
                }
            //    Thread.sleep(1000);
            }

            reader.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(!mSocket.isClosed())
            {
                mSocket.close();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);

        SimpleClientListener listener = mListener.get();

        if(listener != null)
        {
            listener.onClosed();
        }
    }
}
