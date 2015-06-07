package presentation.mainui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

import presentation.component.GFrame;
import presentation.contenui.UIUtil;

public class WebTable extends JLabel{

	private static final long serialVersionUID = 1L;
	private JLabel[][] content;
	private HeaderLabel[] header;
	private JPanel dataLabel;
	private String[] headerData;
	private String[][] contentData;
	
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
		
		String header[] = {"<html>aa<br>aaas<html>", "b", "c", "d"};
		String[][] data = new String[25][4];
		for(int i=0;i<25;i++){
			data[i][0] = String.valueOf(i);data[i][1] = "1";data[i][2] = String.valueOf(i);data[i][3] = "1";
		}
		
		WebTable table = new WebTable(header, data, new Rectangle(70, 70, 900, 500), panel.getBackground());
		table.setRowBackground(0, UIUtil.nbaRed);
		table.setColumBackground(0, UIUtil.nbaRed);
		table.setRowForeground(0, UIUtil.nbaBlue);
		table.setColumForeground(0, UIUtil.nbaBlue);
		table.setRowDataCenter(0);
		table.setColumDataCenter(0);
		table.setHeaderBackground(UIUtil.bgGrey);
		table.setHeaderForeground(UIUtil.bgWhite);
		table.setRowHand(0);
		table.setColumHand(0);
		table.setOrder(0, Double.class);
		
		f.add(panel);
		panel.add(table);
		f.setVisible(true);
	}
	
	public WebTable(String[] header, Object[][] data, Rectangle r, Color background){
		this.setBounds(r.x, r.y, r.width+18, r.height);
		this.tableRectangle = r;
		this.bgColor = background;
		
		this.colum = header.length;
		this.row = data.length;
		this.headerData = header;
		this.contentData = new String[row][colum];
		for(int i=0;i<row;i++){
			for(int j=0;j<colum;j++){
				this.contentData[i][j] = String.valueOf(data[i][j]);
			}
		}
		
		headerInit();
		contentInit();
		scrollInit();
		
		this.repaint();
	}
	
	public void setModel(String[] header, Object[][] data){
		this.colum = header.length;
		this.row = data.length;
		this.headerData = header;
		for(int i=0;i<row;i++){
			for(int j=0;j<colum;j++){
				this.contentData[i][j] = String.valueOf(data[i][j]);
			}
		}
		
		headerInit();
		contentInit();
		scrollInit();
		
		this.repaint();
	}
	
	private void headerInit(){
		this.header = new HeaderLabel[colum];
		
		int columWeight = tableRectangle.width/colum;
		
		for(int i=0;i<colum;i++){
			this.header[i] = new HeaderLabel(headerData[i]);
			
			if(i<colum - 1){
				this.header[i].setSize(columWeight, 40);
			}else{
				this.header[i].setSize(tableRectangle.width-(colum-1)*columWeight, 40);
			}
			
			this.header[i].setLocation(i*columWeight, 0);
			this.header[i].setBackground(new Color(151, 183, 252));
			this.header[i].setOpaque(true);
			this.header[i].setHorizontalAlignment(JLabel.CENTER);
			this.header[i].setBorder(BorderFactory.createLineBorder(new Color(213, 213, 213), 1));
			
			this.add(this.header[i]);
		}
		
		this.repaint();
	}
	
	private void contentInit(){
		this.content = new JLabel[row][colum];
		
		int columWeight = tableRectangle.width/colum;
		int columHeight = 30;
		
		this.dataLabel = new JPanel();
		this.dataLabel.setBounds(0, header[0].getHeight(), tableRectangle.width, row*columHeight);
		this.dataLabel.setLayout(null);
		this.dataLabel.setPreferredSize(new Dimension(dataLabel.getWidth(), dataLabel.getHeight()));
		this.dataLabel.revalidate();
		this.dataLabel.setOpaque(false);
		
		for(int i=0;i<row;i++){
			for(int j=0;j<colum;j++){
				this.content[i][j] = new JLabel(contentData[i][j]);
				
				if(i%2 != 0){
					this.content[i][j].setBackground(new Color(190, 208, 247));
				}else{
					this.content[i][j].setBackground(UIUtil.bgWhite);
				}
				
				if(j<colum - 1){
					this.content[i][j].setSize(columWeight, columHeight);
				}else{
					this.content[i][j].setSize(tableRectangle.width-(colum-1)*columWeight, columHeight);
				}
				
				this.content[i][j].setLocation(j*columWeight, i*columHeight);
				this.content[i][j].setOpaque(true);
				this.content[i][j].setBorder(BorderFactory.createLineBorder(new Color(213, 213, 213), 1));
				
				this.dataLabel.add(this.content[i][j]);
			}
		}
		
		this.repaint();
	}
	
	private void scrollInit(){
		
		this.scrollPane = new JScrollPane();
		this.scrollPane.getViewport().setView(dataLabel);
		this.scrollPane.setBounds(0, header[0].getHeight(), tableRectangle.width+18, tableRectangle.height-header[0].getHeight());
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
        this.repaint();
	}
	
	private void update(){
//		for(int i=0;i<colum;i++){
//			this.header[i].setText(this.headerData[i]);
//		}
		for(int i=0;i<row;i++){
			for(int j=0;j<colum;j++){
				this.content[i][j].setText(this.contentData[i][j]);
			}
		}
	}
	
/////////////////////////////////////////////////////////////////////////////
//   							 										   //
//							  对外接口                                      //
//   						    										   //
/////////////////////////////////////////////////////////////////////////////
	//设置某一列内容居中
	public void setColumDataCenter(int columNumber){
		if(columNumber>=0&&columNumber<this.colum){
			for(int i=0;i<row;i++){
				this.content[i][columNumber].setHorizontalAlignment(JLabel.CENTER);
			}
		}
	}
	//设置某一行内容居中
	public void setRowDataCenter(int rowNumber){
		if(rowNumber>=0&&rowNumber<this.row){
			for(int i=0;i<colum;i++){
				this.content[rowNumber][i].setHorizontalAlignment(JLabel.CENTER);
			}
		}
	}
	//设置全部列内容居中
	public void setAllDataCenter(){
		for(int i=0;i<row;i++){
			for(int j=0;j<colum;j++){
				this.content[i][j].setHorizontalAlignment(JLabel.CENTER);
			}
		}
	}
	//设置行宽
	public void setRowHeight(int height){
		for(int i=0;i<row;i++){
			for(int j=0;j<colum;j++){
				this.content[i][j].setSize(this.content[i][j].getWidth(), height);			}
		}
	}
	//设置表头宽
	public void setHeaderHeight(int height){
		for(int i=0;i<colum;i++){
			this.header[i].setSize(this.header[i].getWidth(), height);
		}
	}
	//设置表头背景色
	public void setHeaderBackground(Color color){
		for(int i=0;i<colum;i++){
			this.header[i].setBackground(color);
		}
	}
	//设置表头前景色
	public void setHeaderForeground(Color color){
		for(int i=0;i<colum;i++){
			this.header[i].setForeground(color);
		}
	}
	//设置行背景色
	public void setRowBackground(int rowNumber, Color color){
		if(rowNumber>=0&&rowNumber<this.row){
			for(int i=0;i<colum;i++){
				this.content[rowNumber][i].setBackground(color);
			}
		}
	}
	//设置行前景色
	public void setRowForeground(int rowNumber, Color color){
		if(rowNumber>=0&&rowNumber<this.row){
			for(int i=0;i<colum;i++){
				this.content[rowNumber][i].setForeground(color);
			}
		}
	}
	//设置列背景色
	public void setColumBackground(int columNumber, Color color){
		if(columNumber>=0&&columNumber<this.colum){
			for(int i=0;i<row;i++){
				this.content[i][columNumber].setBackground(color);
			}
		}
	}
	//设置列前景色
	public void setColumForeground(int columNumber, Color color){
		if(columNumber>=0&&columNumber<this.colum){
			for(int i=0;i<row;i++){
				this.content[i][columNumber].setForeground(color);
			}
		}
	}
	//设置某列手形监听
	public void setColumHand(int columNumber){
		if(columNumber>=0&&columNumber<this.colum){
			for(int i=0;i<row;i++){
				this.content[i][columNumber].setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}
	//设置某行手形监听
	public void setRowHand(int rowNumber){
		if(rowNumber>=0&&rowNumber<this.row){
			for(int i=0;i<colum;i++){
				this.content[rowNumber][i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}
	}
	//get某单元格
	public JLabel getCell(int rowNumber, int columNumber){
		if(rowNumber>=0&&rowNumber<this.row&&columNumber>=0&&columNumber<this.colum){
			return this.content[rowNumber][columNumber];
		}else{
			return null;
		}
	}
	//get列
	public JLabel[] getColum(int columNumber){
		JLabel[] label = new JLabel[row];
		for(int i=0;i<row;i++){
			label[i] = this.content[i][columNumber];
		}
		return label;
	}
	//get行
	public JLabel[] getRow(int rowNumber){
		return this.content[rowNumber];
	}
	//设置某列排序(列号，类型)
	public void setOrder(final int columNumber, final Class<?> type){
		this.header[columNumber].setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.header[columNumber].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				HeaderLabel header = (HeaderLabel) e.getSource();
				if(header.getOrder()!=1){
					upOrder(columNumber, type);
					header.setUp();
				}else{
					downOrder(columNumber, type);
					header.setDown();
				}
			}
		});
	}
	private void upOrder(int columNumber, Class<?> type){
		if(type == Double.class){
			for(int k=0;k<row-1;k++){
				for(int i=0;i<row-1;i++){
					String[] move;
					double a = Double.valueOf(this.contentData[i][columNumber]);
					double b = Double.valueOf(this.contentData[i+1][columNumber]);
					if(a<b){
						move = this.contentData[i];
						this.contentData[i] = this.contentData[i+1];
						this.contentData[i+1] = move;
					}
				}
			}
		}else{
			if(type == String.class){
				for(int k=0;k<row-1;k++){
					for(int i=0;i<row-1;i++){
						String[] move;
						char a = this.contentData[i][columNumber].charAt(0);
						char b = this.contentData[i+1][columNumber].charAt(0);
						if(a<b){
							move = this.contentData[i];
							this.contentData[i] = this.contentData[i+1];
							this.contentData[i+1] = move;
						}
					}
				}
			}
		}
		
		update();
	}
	
	private void downOrder(int columNumber, Class<?> type){
		if(type == Double.class){
			for(int k=0;k<row-1;k++){
				for(int i=0;i<row-1;i++){
					String[] move;
					double a = Double.valueOf(this.contentData[i][columNumber]);
					double b = Double.valueOf(this.contentData[i+1][columNumber]);
					if(a>b){
						move = this.contentData[i];
						this.contentData[i] = this.contentData[i+1];
						this.contentData[i+1] = move;
					}
				}
			}
		}else{
			if(type == String.class){
				for(int k=0;k<row-1;k++){
					for(int i=0;i<row-1;i++){
						String[] move;
						char a = this.contentData[i][columNumber].charAt(0);
						char b = this.contentData[i+1][columNumber].charAt(0);
						if(a>b){
							move = this.contentData[i];
							this.contentData[i] = this.contentData[i+1];
							this.contentData[i+1] = move;
						}
					}
				}
			}
		}
		
		update();
	}
}

/////////////////////////////////////////////////////////////////////////////
//																		   //
//                         美化滚动条                                        //
//																		   //
/////////////////////////////////////////////////////////////////////////////
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
                 return new Dimension(1, 1);
             }
              
             @Override
             public Dimension getMinimumSize() {//将箭头最小大小设成0
                 return new Dimension(1, 1);
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
                 return new Dimension(1, 1);
             }
              
             @Override
             public Dimension getMinimumSize() {//将箭头最小大小设成0
                 return new Dimension(1, 1);
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

class HeaderLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	private int order = 0;
	
	public HeaderLabel(){
		super();
	}
	public HeaderLabel(String string) {
		super(string);
	}
	public int getOrder(){
		return order;
	}
	public void setUp(){
		this.order = 1;
	}
	public void setDown(){
		this.order = -1;
	}
}