package presentation.component;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JFrame;

import com.sun.awt.AWTUtilities;

@SuppressWarnings("restriction")
public class GFrame extends JFrame{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭进程
		this.setUndecorated(true);
		this.setLayout(null);
		AWTUtilities.setWindowOpaque(this, false);
//		AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(  
//	            0.0D, 0.0D, this.getWidth(), this.getHeight(), 0.0D,  
//	            0.0D));
//		this.addComponentListener(new ComponentHandler());
	}
	
	public void setMiddle(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int x = (int)(screen.getWidth()-this.getWidth())/2;
		int y = (int)(screen.getHeight()-this.getHeight())/2-32;
		this.setLocation(x, y);//设置居中
	}
	
	private class ComponentHandler extends ComponentAdapter {  
		private ComponentHandler() {  
		}  

		@Override  
		public void componentResized(ComponentEvent e) {  
			Window win = (Window) e.getSource();  
			Frame frame = (win instanceof Frame) ? (Frame) win : null;  

			if ((frame != null)  
					&& ((frame.getExtendedState() & Frame.MAXIMIZED_BOTH) != 0)) {  
				AWTUtilities.setWindowShape(win, null);  
			} else {  
				/** 设置圆角 */  
				AWTUtilities.setWindowShape(win,  
						new RoundRectangle2D.Double(0.0D, 0.0D, win.getWidth(),  
								win.getHeight(), 26.0D, 26.0D));  
			}  
		}  
	}  
}
