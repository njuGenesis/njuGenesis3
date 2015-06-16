package presentation.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import presentation.contenui.UIUtil;

public class GComboBoxUI extends BasicComboBoxUI{

	private JButton arrow;
	private boolean boundsLight = false;

	public GComboBoxUI() {
		super();
	}

	protected JButton createArrowButton() {
		arrow = new JButton();
		arrow.setSize(30, 30);
		arrow.setLocation(90, 0);
		//	  arrow.setIcon(XUtil.defaultComboBoxArrowIcon);
		arrow.setIcon(UIUtil.defaultComboBoxArrowIcon);
		arrow.setRolloverEnabled(true);
		//	  arrow.setRolloverIcon(XUtil.defaultComboBoxArrowIcon_Into);
		arrow.setBorder(null);
		arrow.setOpaque(false);
		arrow.setContentAreaFilled(false);
		return arrow;
	}

	public void paint(Graphics g, JComponent c) {
		hasFocus = comboBox.hasFocus();
		Graphics2D g2 = (Graphics2D)g;
		if (!comboBox.isEditable()) {
			Rectangle r = rectangleForCurrentValue();
			//重点:JComboBox的textfield 的绘制 并不是靠Renderer来控制 这点让我很吃惊.
			//它会通过paintCurrentValueBackground来绘制背景
			//然后通过paintCurrentValue();去绘制JComboBox里显示的值
			paintCurrentValueBackground(g2, r, hasFocus);
			paintCurrentValue(g2, r, hasFocus);
		}

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(0, 110, 185));
		g2.drawRect(0,0,comboBox.getWidth()-1,comboBox.getHeight()-1);
	}

	public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
		Font oldFont = comboBox.getFont();

		super.paintCurrentValue(g, bounds, hasFocus);
		comboBox.setFont(oldFont);
	}

	public Dimension getPreferredSize(JComponent c) {
		return super.getPreferredSize(c);
	}

	public boolean isBoundsLight() {
		return boundsLight;
	}

	public void setBoundsLight(boolean boundsLight) {
		this.boundsLight = boundsLight;
	}

	protected Rectangle rectangleForCurrentValue() { 
		int width = comboBox.getWidth();
		int height = comboBox.getHeight();
		Insets insets = getInsets();
		return new Rectangle(insets.left, insets.top, width, height);
	} 

	protected ComboPopup createPopup() {
		ComboPopup popup = new BasicComboPopup(comboBox) {
			protected JScrollPane createScroller() {
				JScrollPane sp = new JScrollPane(list,
						ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				sp.setHorizontalScrollBar(null);
				JScrollBar jsb = new JScrollBar();
				jsb.setUI(new IScrollBarUI());
				sp.setHorizontalScrollBar(jsb);
				return sp;
			}
			//重载paintBorder方法 来画出我们想要的边框..
			public void paintBorder(Graphics g){
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(0, 110, 185));
				//g2.drawRoundRect(0,-arrow.getHeight(),getWidth()-1,getHeight()+arrow.getHeight()-1,0,0);
//				g2.drawRect(0, -arrow.getHeight(), getWidth()-1, getHeight()-1);
				g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
			}
		};
		return popup;
	}

}
