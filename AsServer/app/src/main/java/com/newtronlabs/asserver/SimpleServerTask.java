package com.newtronlabs.asserver;

import android.os.AsyncTask;

import com.newtronlabs.appsocket.IAppSocket;
import com.newtronlabs.appsocket.socket.AppServerSocket;
import com.newtronlabs.appsocket.socket.IAppServerSocket;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;

/**
 * Small sample task for handling some basic server socket setup and
 * client communication.
 */
public class SimpleServerTask extends AsyncTask<String, String, Void>
{
    /**
     * Simple helper listener.
     */
    public interface SimpleServerListener
    {
        /**
         * Called to notify update
         * @param update update to notify
         */
        void onUpdate(String update);

        /**
         * Called when the connection to the client
         * is terminated.
         */
        void onClosed();
    }

    private IAppServerSocket mServerSocket;
    private WeakReference<SimpleServerListener> mListener;

    public SimpleServerTask(int port, SimpleServerListener listener)
    {
        // Create server socket on specified port.
        mServerSocket = new AppServerSocket(port);
        mListener = new WeakReference<SimpleServerListener>(listener);
    }

    @Override
    protected void onCancelled()
    {
        super.onCancelled();

        SimpleServerListener listener = mListener.get();

        if(listener != null)
        {
            listener.onClosed();
        }
    }



    @Override
    protected void onProgressUpdate(String... values)
    {
        super.onProgressUpdate(values);

        SimpleServerListener listener = mListener.get();

        if(listener!= null)
        {
            listener.onUpdate(values[0]);
        }
    }

    @Override
    protected Void doInBackground(String... toSend)
    {
        IAppSocket clientSocket = null;
        try
        {
            publishProgress("Waiting for client...");
            // Wait for client.
            clientSocket = mServerSocket.accept();

            publishProgress("Client Connected!");

            // Send content to client.
            int index = 0;
            String itemSent;
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            while(!isCancelled()&& !clientSocket.isClosed())
            {
                writer.write(toSend[index]+"\n");
                writer.flush();

                itemSent = toSend[index];
                publishProgress("Sent: "+itemSent);

                index++;
                if(index >= toSend.length)
                {
                    break;
                }

                Thread.sleep(1000);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(clientSocket != null && !clientSocket.isClosed())
            {
                clientSocket.close();
            }

            mServerSocket.close();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);

        SimpleServerListener listener = mListener.get();

        if(listener != null)
        {
            listener.onClosed();
        }
    }
}
