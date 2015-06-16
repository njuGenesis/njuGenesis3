package presentation.mainui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import presentation.component.GFrame;
import presentation.contenui.UIUtil;

public class WebTable extends JLabel{

	private static final long serialVersionUID = 1L;
	private contentLabel[][] content;
	private HeaderLabel[] header;
	private JPanel dataLabel;
	private String[] headerData;
	private String[][] contentData;

	private Rectangle tableRectangle;

	public int colum, row, cellHeight, cellWidth, headerHeight;

	private JScrollPane scrollPane;
	private boolean isScroll;
	public static Color bgColor;

	private Font font = new Font("黑体",0,12);
	
	public static void main(String[] args) {
		
		
		GFrame f = new GFrame();
		f.setBounds(0, 0, 1000, 600);
		f.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, f.getWidth(), f.getHeight());
		panel.setLayout(null);
		panel.setBackground(UIUtil.bgWhite);

		String header[] = {"<html>aa<br>aaas<html>", "b", "c", "d"};
		String[][] data = new String[50][4];
		for(int i=0;i<50;i++){
			data[i][0] = String.valueOf(i);data[i][1] = "1";data[i][2] = String.valueOf(i);data[i][3] = "1";
		}

		WebTable table = new WebTable(header, data, new Rectangle(70, 70, 900, 500), panel.getBackground());
		//		table.setRowBackground(0, UIUtil.nbaRed);
		//		table.setColumBackground(0, UIUtil.nbaRed);
		//		table.setRowForeground(0, UIUtil.nbaBlue);
		//		table.setColumForeground(0, UIUtil.nbaBlue);
		//		table.setRowDataCenter(0);
		//		table.setColumDataCenter(0);
		//		table.setHeaderForeground(UIUtil.bgWhite);
		//		table.setRowHand(0);
		//		table.setColumHand(0);
		//		table.setOrder(0, Double.class);

		int[] c = {0,3};
		int[] w = {200,50};
		
		table.setColumnWidth(c,w);

		f.add(panel);
		panel.add(table);
		f.setVisible(true);
	}

	public WebTable(String[] header, Object[][] data, Rectangle r, Color background){
		this.setBounds(r.x, r.y, r.width, r.height);
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

		this.cellHeight = 30;
		this.headerHeight = 40;
		if(row*cellHeight+headerHeight>tableRectangle.height){
			cellWidth = (tableRectangle.width-9)/colum;
			isScroll = true;
		}else{
			cellWidth = (tableRectangle.width)/colum;
			isScroll = false;
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

		for(int i=0;i<colum;i++){
			this.header[i] = new HeaderLabel(headerData[i]);

			if(i<colum - 1){
				this.header[i].setSize(cellWidth, headerHeight);
			}else{
				this.header[i].setSize(tableRectangle.width-(colum-1)*cellWidth, headerHeight);
			}

			this.header[i].setLocation(i*cellWidth, 0);
			this.header[i].setBackground(new Color(124, 124, 124));
			this.header[i].setForeground(UIUtil.bgWhite);
			this.header[i].setOpaque(true);
			this.header[i].setHorizontalAlignment(JLabel.CENTER);
			this.header[i].setBorder(BorderFactory.createLineBorder(new Color(213, 213, 213), 1));
			this.header[i].setFont(font);
			
			this.add(this.header[i]);
		}

		this.repaint();
	}

	private void contentInit(){
		this.content = new contentLabel[row][colum];

		this.dataLabel = new JPanel();
		if(isScroll){
			this.dataLabel.setBounds(0, header[0].getHeight(), tableRectangle.width-9, row*cellHeight);
		}else{
			this.dataLabel.setBounds(0, header[0].getHeight(), tableRectangle.width, row*cellHeight);
		}
		this.dataLabel.setLayout(null);
		this.dataLabel.setPreferredSize(new Dimension(dataLabel.getWidth(), dataLabel.getHeight()));
		this.dataLabel.revalidate();
		this.dataLabel.setOpaque(false);

		for(int i=0;i<row;i++){
			for(int j=0;j<colum;j++){
				this.content[i][j] = new contentLabel(contentData[i][j]);
				this.content[i][j].setPoint(i, j);

				//				if(i%2 != 0){
				//					this.content[i][j].setBackground(new Color(190, 208, 247));
				//				}else{
				this.content[i][j].setBackground(UIUtil.bgWhite);
				this.content[i][j].setDefaultBackground(UIUtil.bgWhite);
				this.content[i][j].setForeground(Color.black);
				this.content[i][j].setDefaultForeground(Color.black);
				//}

				if(j<colum - 1){
					this.content[i][j].setSize(cellWidth, cellHeight);
				}else{
					if(isScroll){
						this.content[i][j].setSize(tableRectangle.width-9-(colum-1)*cellWidth, cellHeight);
					}else{
						this.content[i][j].setSize(tableRectangle.width-(colum-1)*cellWidth, cellHeight);
					}
				}

				this.content[i][j].setLocation(j*cellWidth, i*cellHeight);
				this.content[i][j].setOpaque(true);
				this.content[i][j].setBorder(BorderFactory.createLineBorder(new Color(213, 213, 213), 1));
				this.content[i][j].setFont(font);
				
				
				this.content[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e){
						contentLabel label = (contentLabel)e.getSource();
						Point location = label.getPoint();

						WebTable.this.initBackground();
						WebTable.this.mouseEnter(location.y, location.x, UIUtil.nbaBlue);
					}
					@Override
					public void mouseExited(MouseEvent e){
						WebTable.this.initBackground();
					}
				});

				this.dataLabel.add(this.content[i][j]);
			}
		}

		this.repaint();
	}

	private void scrollInit(){

		if(isScroll){
			this.scrollPane = new JScrollPane();
			this.scrollPane.getViewport().setView(dataLabel);
			this.scrollPane.setBounds(0, header[0].getHeight(), tableRectangle.width+7, tableRectangle.height-header[0].getHeight());
			this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
			this.scrollPane.setOpaque(false);
			this.scrollPane.getViewport().setOpaque(false);
			this.scrollPane.setVisible(true);

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
		}else{
			this.add(dataLabel);
		}
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

	private void initBackground(){
		for(int i=0;i<row;i++){
			for(int j=0;j<colum;j++){
				this.content[i][j].setBackground(this.content[i][j].getDefaultBackground());
				this.content[i][j].setForeground(this.content[i][j].getDefaultForeground());
			}
		}
	}
	private void initBorderColor(){
		for(int i=0;i<row;i++){
			for(int j=0;j<colum;j++){
				this.content[i][j].setBorder(BorderFactory.createLineBorder(new Color(213, 213, 213), 1));
			}
		}
	}
	private void mouseEnter(int columNumber, int rowNumber, Color color){
		if(rowNumber>=0&&rowNumber<this.row){
			for(int i=0;i<colum;i++){
				this.content[rowNumber][i].setBackground(color);
				this.content[rowNumber][i].setForeground(UIUtil.bgWhite);
			}
		}
		if(columNumber>=0&&columNumber<this.colum){
			for(int i=0;i<row;i++){
				this.content[i][columNumber].setBackground(color);
				this.content[i][columNumber].setForeground(UIUtil.bgWhite);
			}
		}
	}
	/////////////////////////////////////////////////////////////////////////////
	//   							 										   //
	//							  对外接口                                      //
	//   						    										   //
	/////////////////////////////////////////////////////////////////////////////

	
	//设置某一列宽：参数为列号和宽度的两个int数组，一一对应
	public void setColumnWidth(int[] columNumber,int[] width){
		
		//----------修改表头宽度----------
		int otherWidth = this.getWidth();
		for(int i=0;i<width.length;i++){
			otherWidth -= width[i];
		}
		int leftWidth = otherWidth;
		otherWidth = otherWidth/(colum-width.length);
		
		for(int i=0;i<colum;i++){
			if(checkLast(columNumber)){
				if(i==colum-1){
					this.header[i].setSize(getNumWidth(otherWidth,i,columNumber,width), this.header[i].getHeight());
					this.header[i].setLocation(header[i-1].getX()+header[i-1].getWidth(), this.header[i].getY());
				}else if(i==0){
					this.header[i].setSize(getNumWidth(otherWidth,i,columNumber,width), this.header[i].getHeight());
					this.header[i].setLocation(0, this.header[i].getY());
				}else if(i==colum-2){
					this.header[i].setSize(leftWidth-otherWidth*(colum-1-columNumber.length), this.header[i].getHeight());
					this.header[i].setLocation(header[i-1].getX()+header[i-1].getWidth(), this.header[i].getY());
				}else{
					this.header[i].setSize(getNumWidth(otherWidth,i,columNumber,width), this.header[i].getHeight());
					this.header[i].setLocation(header[i-1].getX()+header[i-1].getWidth(), this.header[i].getY());
				}
			}else if(checkFirst(columNumber)){
				if(i==colum-1){
					this.header[i].setSize(leftWidth-otherWidth*(colum-1-columNumber.length), this.header[i].getHeight());
					this.header[i].setLocation(header[i-1].getX()+header[i-1].getWidth(), this.header[i].getY());
				}else if(i==0){
					this.header[i].setSize(getNumWidth(otherWidth,i,columNumber,width), this.header[i].getHeight());
					this.header[i].setLocation(0, this.header[i].getY());
				}else if(checkAt(i,columNumber)){
					this.header[i].setSize(getNumWidth(otherWidth,i,columNumber,width), this.header[i].getHeight());
					this.header[i].setLocation(header[i-1].getX()+header[i-1].getWidth(), this.header[i].getY());
				}else{
					this.header[i].setSize(otherWidth, this.header[i].getHeight());
					this.header[i].setLocation(header[i-1].getX()+header[i-1].getWidth(), this.header[i].getY());
				}
			}else{
				if(i==colum-1){
					this.header[i].setSize(leftWidth-otherWidth*(colum-1-columNumber.length), this.header[i].getHeight());
					this.header[i].setLocation(header[i-1].getX()+header[i-1].getWidth(), this.header[i].getY());
				}else if(checkAt(i,columNumber)){
					this.header[i].setSize(getNumWidth(otherWidth,i,columNumber,width), this.header[i].getHeight());
					this.header[i].setLocation(header[i-1].getX()+header[i-1].getWidth(), this.header[i].getY());
				}else if(i==0){
					this.header[i].setSize(otherWidth, this.header[i].getHeight());
					this.header[i].setLocation(0, this.header[i].getY());
				}else{
					this.header[i].setSize(otherWidth, this.header[i].getHeight());
					this.header[i].setLocation(header[i-1].getX()+header[i-1].getWidth(), this.header[i].getY());
				}
			}
		}

		//-----------修改表格宽度----------
		if(isScroll){
			for(int i=0;i<row;i++){
				for(int j=0;j<colum;j++){
					if(j==colum-1){
						this.content[i][j].setSize(this.header[j].getWidth()-9, this.content[i][j].getHeight());
						this.content[i][j].setLocation(this.header[j].getX(), this.content[i][j].getY());
					}else{
						this.content[i][j].setSize(this.header[j].getWidth(), this.content[i][j].getHeight());
						this.content[i][j].setLocation(this.header[j].getX(), this.content[i][j].getY());
					}
				}
			}
		}else{
			for(int i=0;i<row;i++){
				for(int j=0;j<colum;j++){
					this.content[i][j].setSize(this.header[j].getWidth(), this.content[i][j].getHeight());
					this.content[i][j].setLocation(this.header[j].getX(), this.content[i][j].getY());
				}
			}
		}
	}
	private int getNumWidth(int otherWidth,int current,int[] c,int[] w){
		for(int i=0;i<c.length;i++){
			if(current==c[i]){
				return w[i];
			}
		}
		return otherWidth;
	}
	private boolean checkLast(int[] c){
		for(int i=0;i<c.length;i++){
			if(c[i]==colum-1){
				return true;
			}
		}
		return false;
	}
	private boolean checkFirst(int[] c){
		for(int i=0;i<c.length;i++){
			if(c[i]==0){
				return true;
			}
		}
		return false;
	}
	private boolean checkAt(int n,int[] c){
		for(int i=0;i<c.length;i++){
			if(c[i]==n){
				return true;
			}
		}
		return false;
	}
	
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
				this.content[rowNumber][i].setDefaultBackground(color);
			}
		}
	}
	//设置行边框色
	public void setRowBorderColor(int rowNumber, Color color){
		if(rowNumber>=0&&rowNumber<this.row){
			for(int i=0;i<colum;i++){
				this.content[rowNumber][i].setBorder(BorderFactory.createLineBorder(color, 1));
			}
		}
	}
	//设置行前景色
	public void setRowForeground(int rowNumber, Color color){
		if(rowNumber>=0&&rowNumber<this.row){
			for(int i=0;i<colum;i++){
				this.content[rowNumber][i].setForeground(color);
				this.content[rowNumber][i].setDefaultForeground(color);
			}
		}
	}
	//设置列背景色
	public void setColumBackground(int columNumber, Color color){
		if(columNumber>=0&&columNumber<this.colum){
			for(int i=0;i<row;i++){
				this.content[i][columNumber].setBackground(color);
				this.content[i][columNumber].setDefaultBackground(color);
			}
		}
	}
	//设置列边框色
	public void setColumBorderColor(int columNumber, Color color){
		if(columNumber>=0&&columNumber<this.colum){
			for(int i=0;i<row;i++){
				this.content[i][columNumber].setBorder(BorderFactory.createLineBorder(color, 1));
			}
		}
	}
	//设置列前景色
	public void setColumForeground(int columNumber, Color color){
		if(columNumber>=0&&columNumber<this.colum){
			for(int i=0;i<row;i++){
				this.content[i][columNumber].setForeground(color);
				this.content[i][columNumber].setDefaultForeground(color);
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
	public void upOrder(int columNumber, Class<?> type){
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
						String a = this.contentData[i][columNumber];
						String b = this.contentData[i+1][columNumber];
						if(a.compareTo(b)<0){
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

	public void downOrder(int columNumber, Class<?> type){
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

class contentLabel extends JLabel{

	private static final long serialVersionUID = 1L;
	private Point point;
	private Color defaultBackground;
	private Color defaultForeground;

	public contentLabel(){
		super();
	}
	public contentLabel(String s){
		super(s);
	}

	public Point getPoint(){
		return this.point;
	}
	public void setPoint(int x, int y){
		this.point = new Point(x, y);
	}
	public void setDefaultBackground(Color color){
		this.defaultBackground = color;
	}
	public void setDefaultForeground(Color color){
		this.defaultForeground = color;
	}
	public Color getDefaultBackground(){
		return this.defaultBackground;
	}
	public Color getDefaultForeground(){
		return this.defaultForeground;
	}
}