package presentation.mainui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

import presentation.component.GFrame;
import presentation.contenui.UIUtil;

public class WebTable extends JLabel{

	private static final long serialVersionUID = 1L;
	private JLabel[][] content;
	private JLabel[] header;
	private JPanel dataLabel;
	
	private Rectangle tableRectangle;
	
	private int colum, row;
	
	private JScrollPane scrollPane;
	public static Color bgColor;
	
	public static void main(String[] args) {
		GFrame f = new GFrame();
		f.setBounds(0, 0, 1000, 600);
		f.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, f.getWidth(), f.getHeight());
		panel.setLayout(null);
		panel.setBackground(UIUtil.nbaBlue);
		
		String header[] = {"a", "b", "c", "d"};
		String[][] data = new String[25][4];
		for(int i=0;i<25;i++){
			data[i][0] = String.valueOf(i);data[i][1] = "1";data[i][2] = "1";data[i][3] = "1";
		}
		
		WebTable table = new WebTable(header, data, new Rectangle(70, 70, 900, 500), panel.getBackground());
		
		f.add(panel);
		panel.add(table);
		f.setVisible(true);
	}
	
	public WebTable(String[] header, Object[][] data, Rectangle r, Color background){
		this.setBounds(r.x, r.y, r.width+18, r.height);
		this.tableRectangle = r;
		
		this.colum = header.length;
		this.row = data.length;
		
		this.bgColor = background;
		
		headerInit(header);
		contentInit(data);
		scrollInit();
	}
	
	private void headerInit(String[] header){
		this.header = new JLabel[colum];
		
		int columWeight = tableRectangle.width/colum;
		
		for(int i=0;i<colum;i++){
			this.header[i] = new JLabel(header[i]);
			
			if(i<colum - 1){
				this.header[i].setSize(columWeight, 30);
			}else{
				this.header[i].setSize(tableRectangle.width-(colum-1)*columWeight, 30);
			}
			
			this.header[i].setLocation(i*columWeight, 0);
			this.header[i].setBackground(new Color(151, 183, 252));
			this.header[i].setOpaque(true);
			this.header[i].setHorizontalAlignment(JLabel.CENTER);
			this.header[i].setBorder(BorderFactory.createLineBorder(new Color(213, 213, 213), 1));
			
			this.add(this.header[i]);
		}
	}
	
	private void contentInit(Object[][] data){
		this.content = new JLabel[row][colum];
		
		int columWeight = tableRectangle.width/colum;
		
		this.dataLabel = new JPanel();
		this.dataLabel.setBounds(0, 30, tableRectangle.width, row*30);
		this.dataLabel.setLayout(null);
		this.dataLabel.setPreferredSize(new Dimension(dataLabel.getWidth(), dataLabel.getHeight()));
		this.dataLabel.revalidate();
		this.dataLabel.setOpaque(false);
		
		for(int i=0;i<row;i++){
			for(int j=0;j<colum;j++){
				this.content[i][j] = new JLabel(String.valueOf(data[i][j]));
				
				if(i%2 != 0){
					this.content[i][j].setBackground(new Color(190, 208, 247));
				}else{
					this.content[i][j].setBackground(UIUtil.bgWhite);
				}
				
				if(j<colum - 1){
					this.content[i][j].setSize(columWeight, 30);
				}else{
					this.content[i][j].setSize(tableRectangle.width-(colum-1)*columWeight, 30);
				}
				
				this.content[i][j].setLocation(j*columWeight, i*30);
				this.content[i][j].setOpaque(true);
				this.content[i][j].setBorder(BorderFactory.createLineBorder(new Color(213, 213, 213), 1));
				
				this.dataLabel.add(this.content[i][j]);
			}
		}
	}
	
	private void scrollInit(){
		
		this.scrollPane = new JScrollPane();
		this.scrollPane.getViewport().setView(dataLabel);
		this.scrollPane.setBounds(0, 30, tableRectangle.width+18, tableRectangle.height-30);
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
		this.scrollPane.setOpaque(false);
		this.scrollPane.getViewport().setOpaque(false);
		
        this.scrollPane.getVerticalScrollBar().setUI(new IScrollBarUI());
        this.scrollPane.getHorizontalScrollBar().setUI(new IScrollBarUI());
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        
        if (scrollPane.getCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER) == null) {
            JLabel component = new JLabel() {
				private static final long serialVersionUID = 1L;

				@Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    Paint oldPaint = g2.getPaint();
                    Rectangle bounds = getBounds();
                    Paint backgroupRectPaint = new GradientPaint(0, 0, new Color(216, 216, 216),
                            bounds.width, bounds.height, new Color(152, 152, 152));
                    g2.setPaint(backgroupRectPaint);
                    g2.fillRect(0, 0, bounds.width, bounds.height);
                    g2.setPaint(oldPaint);

                }
            };
            scrollPane.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, component);
        }
        
        this.add(scrollPane);
	}
	
	public void setDataCenter(int colum){
		if(colum>=0&&colum<this.colum){
			for(int i=0;i<row;i++){
				this.content[i][colum].setHorizontalAlignment(JLabel.CENTER);
			}
		}
	}
}

class IScrollBarUI extends BasicScrollBarUI {

    private static final float ARC_NUMBER = 10.0f;

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {

        Graphics2D g2 = (Graphics2D) g;
        int w = thumbBounds.width/2 + 1;
        int h = thumbBounds.height - 1;
        Paint oldPaint = g2.getPaint();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.translate(thumbBounds.x, thumbBounds.y);

        Shape arcRect = new RoundRectangle2D.Float(0.0f, 0.0f, (float) w, (float) h, ARC_NUMBER, ARC_NUMBER);
        //填充滚动条矩形
        Paint arcRectPaint = null;
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            arcRectPaint = new GradientPaint(0, 0, new Color(151, 183, 252),
                    thumbBounds.width, 0, new Color(151, 183, 252));
        } else {
            arcRectPaint = new GradientPaint(0, 0, new Color(151, 183, 252),
                    0, thumbBounds.height, new Color(151, 183, 252));
        }
        g2.setPaint(arcRectPaint);
        g2.fill(arcRect);
        //画滚动条矩形边框
//        g2.setColor(new Color(150, 150, 150));
//        g2.draw(arcRect);
        //画滚动条矩形内圈边框
//        g2.setColor(new Color(230, 230, 230));
//        Rectangle bounds = arcRect.getBounds();
//        g2.drawRoundRect(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height - 2, (int) ARC_NUMBER, (int) ARC_NUMBER);

//        g2.translate(-thumbBounds.x, -thumbBounds.y);
//        g2.setPaint(oldPaint);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g;
        Paint foregroundRectPaint = null;
        Paint backgroupRectPaint = null;
        Paint oldPaint = g2.getPaint();
        //绘制滚动背景
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            foregroundRectPaint = new GradientPaint(0, 0, WebTable.bgColor,
                    trackBounds.width, 0, WebTable.bgColor);
            backgroupRectPaint = new GradientPaint(0, 0, WebTable.bgColor,
                    trackBounds.width, 0, WebTable.bgColor);
        } else {
            foregroundRectPaint = new GradientPaint(0, 0, WebTable.bgColor,
                    0, trackBounds.height, WebTable.bgColor);
            backgroupRectPaint = new GradientPaint(0, 0, WebTable.bgColor,
                    0, trackBounds.height, WebTable.bgColor);
        }
        g2.setPaint(backgroupRectPaint);
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        g2.setPaint(foregroundRectPaint);
        g2.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width - 1, trackBounds.height - 1, (int) ARC_NUMBER, (int) ARC_NUMBER);
        g2.setColor(WebTable.bgColor);
        g2.drawRoundRect(trackBounds.x, trackBounds.y, trackBounds.width - 1, trackBounds.height - 1, (int) ARC_NUMBER, (int) ARC_NUMBER);

        g2.setPaint(oldPaint);

        //始终没有进入这两个判断方法，通过单独调用它们中的任意一个即刻明白这两个方法的含义
        if (trackHighlight == DECREASE_HIGHLIGHT) {
            paintDecreaseHighlight(g);
        } else if (trackHighlight == INCREASE_HIGHLIGHT) {
            paintIncreaseHighlight(g);
        }
    }

    @Override
    protected void paintDecreaseHighlight(Graphics g) {
    }

    @Override
    protected void paintIncreaseHighlight(Graphics g) {
    }

    @Override
    protected javax.swing.JButton createDecreaseButton(int orientation) {
        return new BasicArrowButton(orientation) {

        	 @Override
             public Dimension getPreferredSize() {//将箭头默认大小设成0
                 return new Dimension(0, 0);
             }
              
             @Override
             public Dimension getMinimumSize() {//将箭头最小大小设成0
                 return new Dimension(0, 0);
             }
            @Override
            public void paint(Graphics g) {
                GradientPaint backgroupRectPaint = null;
                if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                    backgroupRectPaint = new GradientPaint(0, 0, WebTable.bgColor,
                            getWidth(), 0, WebTable.bgColor);
                } else {
                    backgroupRectPaint = new GradientPaint(0, 0, WebTable.bgColor,
                            0, getHeight(), WebTable.bgColor);
                }
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(backgroupRectPaint);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Draw the arrow
                //IScrollBarUI.this.paintTriangle(g2, getSize(), direction);
            }
        };
    }

    public void paintTriangle(Graphics2D g2, Dimension buttonSize,
            int direction) {
        int x, y, w, h, size;

        w = buttonSize.width;
        h = buttonSize.height;
        size = Math.min((h - 4) / 3, (w - 4) / 3);
        size = Math.max(size, 2);
        x = (w - size) / 2;
        y = (h - size) / 2;

        Color oldColor = g2.getColor();
        int mid, i, j;

        j = 0;
        size = Math.max(size, 2) + 2;
        mid = (size / 2) - 1;

        g2.translate(x, y);
        g2.setColor(Color.BLACK);
        switch (direction) {
            case NORTH:
                for (i = 0; i < size; i++) {
                    g2.drawLine(mid - i, i, mid + i, i);
                }
                break;
            case SOUTH:
                j = 0;
                for (i = size - 1; i >= 0; i--) {
                    g2.drawLine(mid - i, j, mid + i, j);
                    j++;
                }
                break;
            case WEST:
                for (i = 0; i < size; i++) {
                    g2.drawLine(i, mid - i, i, mid + i);
                }
                break;
            case EAST:
                j = 0;
                for (i = size - 1; i >= 0; i--) {
                    g2.drawLine(j, mid - i, j, mid + i);
                    j++;
                }
                break;
        }
        g2.translate(-x, -y);
        g2.setColor(oldColor);
    }

    @Override
    protected javax.swing.JButton createIncreaseButton(int orientation) {

        return new BasicArrowButton(orientation) {

        	 @Override
             public Dimension getPreferredSize() {//将箭头默认大小设成0
                 return new Dimension(0, 0);
             }
              
             @Override
             public Dimension getMinimumSize() {//将箭头最小大小设成0
                 return new Dimension(0, 0);
             }
            @Override
            public void paint(Graphics g) {
                GradientPaint backgroupRectPaint = null;
                if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                    backgroupRectPaint = new GradientPaint(0, 0, WebTable.bgColor,
                            getWidth(), 0, WebTable.bgColor);
                } else {
                    backgroupRectPaint = new GradientPaint(0, 0, WebTable.bgColor,
                            0, getHeight(), WebTable.bgColor);
                }
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(backgroupRectPaint);
                g2.fillRect(0, 0, getWidth(), getHeight());
                //IScrollBarUI.this.paintTriangle(g2, getSize(), direction);
            }
        };
    }
}
