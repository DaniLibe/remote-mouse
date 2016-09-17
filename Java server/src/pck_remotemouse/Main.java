package pck_remotemouse;

public class Main
{
	public static void main(String[] args)
	{
		RemoteMouse remote_mouse = new RemoteMouse();
		
		while (true)
		{
			remote_mouse.move_mouse();
		}
	}
}
