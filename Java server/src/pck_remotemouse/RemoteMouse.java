package pck_remotemouse;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RemoteMouse
{
	public long computer_width;
	public long computer_height;
	public int[] commas;
	public Robot robot;
	
	public RemoteMouse()
	{
		this.computer_width = Toolkit.getDefaultToolkit().getScreenSize().width;
		this.computer_height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		this.commas = new int[4];
		
		try
		{
			this.robot = new Robot();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void move_mouse()
	{
		try
        {
            DatagramSocket udp_socket = new DatagramSocket(7898);

            byte[] data_bytes = new byte[4096];
            DatagramPacket packet = new DatagramPacket(data_bytes, 0, data_bytes.length); 
            
            udp_socket.receive(packet);
            
            String data_string = new String(data_bytes);
            
            udp_socket.close();
            
            int k = 0;
            for (int i = 0; i < data_string.length(); i++)
            {
            	if (data_string.charAt(i) == ',')
            	{
            		this.commas[k] = i;
            		k++;
            	}
            }            
            
            long smartphone_width = Long.valueOf(data_string.substring(this.commas[2] + 1, this.commas[3]));
            long smartphone_height = Long.valueOf(data_string.substring(this.commas[3] + 1, data_string.indexOf(')')));
            
            smartphone_width = this.map(smartphone_width, 0, Long.valueOf(data_string.substring(this.commas[0] + 1, this.commas[1])), 0, this.computer_width + 150);
            smartphone_height = this.map(smartphone_height, 0, Long.valueOf(data_string.substring(this.commas[1] + 1, this.commas[2])), 0, this.computer_height + 150);
            
            int action = Integer.valueOf(data_string.substring(data_string.indexOf('(') + 1, this.commas[0]));
            
            if (action == 0)
            {
            	this.robot.mouseMove((int) smartphone_width, (int) smartphone_height);
            }
            
            if (action == 1)
            {
            	this.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            	this.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }
            
            // DEBUG
            
            /*
            System.out.println(smartphone_width);
            System.out.println(smartphone_height);
            System.out.println(action);
            System.out.println();
            */
        }
		catch (Exception ex)
		{
            ex.printStackTrace();
        }
	}
	
	// Arduino's function
	public long map(long x, long in_min, long in_max, long out_min, long out_max)
	{
	  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

}
