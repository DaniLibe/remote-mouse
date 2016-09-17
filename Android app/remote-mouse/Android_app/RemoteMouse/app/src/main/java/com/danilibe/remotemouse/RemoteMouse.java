package com.danilibe.remotemouse;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class RemoteMouse extends AsyncTask<Void, Void, Void>
{
    public Activity main_activity;
    public RelativeLayout touchable_area;
    public int x;
    public int y;
    public int x_prev;
    public int y_prev;
    public String data_to_send;
    public String remote_ip;
    public int remote_port;
    public int screen_width;
    public int screen_height;
    public int action;

    public RemoteMouse(Activity main_activity_c, RelativeLayout touchable_area_c, String remote_ip_c, int remote_port_c)
    {
        this.main_activity = main_activity_c;
        this.touchable_area = touchable_area_c;
        this.x = 0;
        this.y = 0;
        this.remote_ip = remote_ip_c;
        this.remote_port = remote_port_c;
        this.screen_width = this.main_activity.getWindowManager().getDefaultDisplay().getWidth();
        this.screen_height = this.main_activity.getWindowManager().getDefaultDisplay().getHeight();
        this.action = 0;
    }

    public void get_coords()
    {
        this.touchable_area.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                RemoteMouse.this.x = (int)event.getX();
                RemoteMouse.this.y = (int)event.getY();

                if (event.getAction() == event.ACTION_DOWN)
                {
                    RemoteMouse.this.action = 1;
                }
                else if (event.getAction() == event.ACTION_MOVE)
                {
                    RemoteMouse.this.action = 0;
                }

                return true;
            }
        });
    }

    @Override
    public Void doInBackground(Void... arg0)
    {
        try
        {
            while (true)
            {
                this.x_prev = this.x;
                this.y_prev = this.y;

                get_coords();

                if ((this.x_prev != this.x) || (this.y_prev != this.y))
                {
                    this.data_to_send = "(" + this.action + "," + this.screen_width + "," + this.screen_height + "," + String.valueOf(this.x) + "," + String.valueOf(this.y) + ")";

                    //System.out.println(this.data_to_send);

                    DatagramSocket udp_socket = new DatagramSocket();

                    DatagramPacket packet = new DatagramPacket(this.data_to_send.getBytes(), 0, this.data_to_send.getBytes().length, InetAddress.getByName(this.remote_ip), this.remote_port);

                    udp_socket.send(packet);
                    udp_socket.close();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void onProgressUpdate(Void... arg1)
    {}

    @Override
    public void onPostExecute(Void arg2)
    {}

}
