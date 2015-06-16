package presentation.compare;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import data.po.playerData.PlayerDataPlayOff_Avg_Basic;
import data.po.playerData.PlayerDataPlayOff_Tot_Basic;
import data.po.playerData.PlayerDataSeason_Avg_Basic;
import data.po.playerData.PlayerDataSeason_Tot_Basic;
import data.po.playerData.PlayerDetailInfo;
import bussinesslogic.player.PlayerLogic_db;
import presentation.component.BgPanel;
import presentation.component.ChartCheckBox;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;

public class PlayerCompare extends BgPanel{

	private static final long serialVersionUID = 1L;
	private GLabel[] players = new GLabel[2];
	private PlayerLogic_db playerLogic_db = new PlayerLogic_db();;
	private PlayerDetailInfo[] playerDetailInfos = new PlayerDetailInfo[players.length];
	private int[] ids = new int[players.length];
	private boolean[] select;
	private boolean isFirst, isNormal, isAvg;
	private GLabel selectLabel, VS, dataLebel, search, name0, name1, bg, warn;
	private ChartCheckBox[] chartChekBox;
	private JComboBox<String> comboBoxNormal, comboBoxAvg, search0, search1;
	private String[] dataType = {"三分％", "命中％", "罚球％", "助攻", "抢断", "篮板", 
			"盖帽", "失误", "犯规", "分钟", "得分"};
	private String[] dataTypeFunction = {"thper","shootper","ftper","assist","steal","backbound","rejection","miss","foul","time","pts"};
	private String[] dataResult = {"均值","中位数","极差","方差","标准差","变异系数","偏度","峰度"};
	private GLabel[] dataResultLabel = new GLabel[dataResult.length]; 
	private GLabel[] dataResultShowLabel = new GLabel[dataResult.length*2];
 	private ChartPanel panel;
 	private Vector<String> playerNames = new Vector<>();
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frame = new JFrame();  
				frame.setSize(1000,700);
				frame.setVisible(true); 
				frame.setLayout(null);
				
				JPanel panel = new JPanel();
				panel.setBounds(0, 0, 1000, 700);
				panel.setBackground(UIUtil.lightGrey);
				panel.setLayout(null);

				PlayerCompare panel2 = new PlayerCompare();
				
				panel.add(panel2);
				frame.add(panel);
			}
		});
	}
	public PlayerCompare(){
		super("");

		this.setBounds(0, 0, 940, 600);
		this.setBackground(Color.white);
		this.setLayout(null);
		this.setVisible(true);

		isFirst = true;
		isAvg = true;
		isNormal = true;
		
		init();
	}
	
	private void init(){
		ArrayList<PlayerDetailInfo> list = new ArrayList<>();
		try {
			list = playerLogic_db.getAlldetail("null");
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		playerNames.add("球员英文名模糊查询");
		for(int i=0;i<list.size();i++){
			playerNames.add(list.get(i).getName());
		}
		
		search0 = new JComboBox<>(playerNames);
		search0.setEditable(true);
		search0.setBounds(135, 15, 200, 25);
		search0.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if((e.getKeyChar()>='a'&&e.getKeyChar()<='z')||(e.getKeyChar()>='A'&&e.getKeyChar()<='Z')||e.getKeyChar()==8){
					JTextField textField = (JTextField) search0.getEditor().getEditorComponent();
					String str = textField.getText();
					search0.setModel(new DefaultComboBoxModel<>(searchJundge(str)));
					search0.setPopupVisible(true);
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		search0.getEditor().getEditorComponent().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				JTextField textField = (JTextField) search0.getEditor().getEditorComponent();
				String str = textField.getText();
				if(str.equals("球员英文名模糊查询")){
					playerNames.set(0, "");
					search0.setModel(new DefaultComboBoxModel<>(playerNames));
				}
			}
		});
		this.add(search0);
		
		search1 = new JComboBox<>(playerNames);
		search1.setEditable(true);
		search1.setBounds(605, 15, 200, 25);
		search1.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if((e.getKeyChar()>='a'&&e.getKeyChar()<='z')||(e.getKeyChar()>='A'&&e.getKeyChar()<='Z')||e.getKeyChar()==8){
					JTextField textField = (JTextField) search1.getEditor().getEditorComponent();
					String str = textField.getText();
					search1.setModel(new DefaultComboBoxModel<>(searchJundge(str)));
					search1.setPopupVisible(true);
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		search1.getEditor().getEditorComponent().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				JTextField textField = (JTextField) search1.getEditor().getEditorComponent();
				String str = textField.getText();
				if(str.equals("球员英文名模糊查询")){
					playerNames.set(0, "");
					search1.setModel(new DefaultComboBoxModel<>(playerNames));
				}
			}
		});
		this.add(search1);
		
		search = new GLabel("比较", new Point(420, 15), new Point(100, 25), this, true, 1, 15);
		search.setForeground(UIUtil.nbaBlue);
		search.setCursor(new Cursor(Cursor.HAND_CURSOR));
		search.setHorizontalAlignment(JLabel.CENTER);
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				String name0 = search0.getEditor().getItem().toString();
				String name1 = search1.getEditor().getItem().toString();
				
				try {
					ids[0] = playerLogic_db.getIDbyName(name0, "");
					ids[1] = playerLogic_db.getIDbyName(name1, "");
					
					if((ids[0] == 0)||(ids[1] == 0)||(ids[0] == ids[1])){System.out.println(isFirst);
						removeData();
						bg.setVisible(false);
						warn.setVisible(true);
						selectLabel.setVisible(false);
						dataLebel.setVisible(false);
						isFirst = true;
					}else{
						bg.setVisible(false);
						warn.setVisible(false);
						selectLabel.setVisible(true);
						dataLebel.setVisible(true);
						dataInit();
						isFirst = false;
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		dataLebel = new GLabel("", new Point(520, 260), new Point(420, 340), this, false);
		
		for(int i=0;i<dataResultLabel.length;i++){
			dataResultLabel[i] = new GLabel(dataResult[i], new Point(70, 60+i*30), new Point(70, 30), dataLebel, true, 0, 15);
		}
		
		bg = new GLabel("img/compare/vs.png", new Point(220, 100), new Point(500, 500), this, true);
		warn = new GLabel("img/compare/warn.png", new Point(220, 100), new Point(500, 500), this, false);
		
		VS = new GLabel("VS", new Point(420, 60), new Point(100, 200), this, false, 1, 35);
		VS.setHorizontalAlignment(JLabel.CENTER);
		VS.setForeground(UIUtil.nbaBlue);
		
		selectLabel = new GLabel("", new Point(420, 260), new Point(100, 340), this, false);
		
		chartChekBox = new ChartCheckBox[dataType.length];
		select = new boolean[dataType.length];
		for(int i=0;i<dataType.length;i++){
			select[i] = false;
			chartChekBox[i] = new ChartCheckBox(i);

			chartChekBox[i].setBounds(0, 25*i, 100, 25);

			chartChekBox[i].setText(dataType[i]);
			
			chartChekBox[i].setBackground(UIUtil.bgWhite);
			chartChekBox[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					isFirst = false;
					ChartCheckBox chartChekBox = (ChartCheckBox)e.getSource();
					if(!chartChekBox.isSelected()){
						chartChekBox.setSelected(true);
					}else{
						setFalse();
						select[chartChekBox.getNumber()] = true;
						chartChekBox.setSelected(true);
					}
					dataInit();
				}
			});
			selectLabel.add(chartChekBox[i]);
		}
		select[0] = true;
		chartChekBox[0].setSelected(true);
		
		String []isSeason = {"常规赛", "季后赛"};
		comboBoxNormal = new JComboBox<String>(isSeason);
		comboBoxNormal.setBounds(0, 5+dataType.length*25, 100, 25);
		selectLabel.add(comboBoxNormal);
		comboBoxNormal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFirst = false;
				if(comboBoxNormal.getSelectedIndex() == 0){
					isNormal = true;
				}else{
					isNormal = false;
				}
				dataInit();
			}
		});
		
		String []isTot = {"场均", "总数"};
		comboBoxAvg = new JComboBox<String>(isTot);
		comboBoxAvg.setBounds(0, dataType.length*25+35, 100, 25);
		selectLabel.add(comboBoxAvg);
		comboBoxAvg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFirst = false;
				if(comboBoxAvg.getSelectedIndex() == 0){
					isAvg = true;
				}else{
					isAvg = false;
				}System.out.println(isAvg);
				dataInit();
			}
		});
	}
	
	private void setFalse(){
		for(int i=0;i<dataType.length;i++){
			select[i] = false;
			chartChekBox[i].setSelected(false);
		}
	}
	
	private Vector<String> searchJundge(String str){
		Vector<String> names = new Vector<>();
		names.add(str);
		
		for(int i=1;i<playerNames.size();i++){
			if(playerNames.get(i).toLowerCase().contains(str.toLowerCase())){
				names.add(playerNames.get(i));
			}
		}
		
		return names;
	}

	private void removeData(){
		if(!isFirst){System.out.println("here");
			this.remove(panel);
			for(int i=0;i<dataResultShowLabel.length;i++){
				dataLebel.remove(dataResultShowLabel[i]);
			}
			for(int i=0;i<players.length;i++){
				players[i].removeAll();
			}
			dataLebel.remove(name0);
			dataLebel.remove(name1);
		}
		
		this.updateUI();
	}
	private void dataInit(){
		removeData();
		
		for(int i=0;i<players.length;i++){
			try {
				playerDetailInfos[i] = playerLogic_db.getdetail(ids[i]);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			players[i] = new GLabel("", new Point(i*520, 60), new Point(420, 200), this, true);
			
			GLabel pic = new GLabel("img/players/"+ids[i]+".jpg", new Point(20, 10), new Point(180, 180), players[i], true);
			GLabel name = new GLabel(playerDetailInfos[i].getName(), new Point(220, 10), new Point(200, 30), players[i], true, 0, 15);
			GLabel birth = new GLabel(playerDetailInfos[i].getBirth(), new Point(name.getLocation().x, name.getLocation().y+name.getSize().height+5), new Point(name.getSize().width, name.getSize().height), players[i], true, 0, 15);
			GLabel height = new GLabel(playerDetailInfos[i].getHeight(), new Point(birth.getLocation().x, birth.getLocation().y+birth.getSize().height+5), new Point(name.getSize().width, name.getSize().height), players[i], true, 0, 15);
			GLabel weight = new GLabel(playerDetailInfos[i].getWeight(), new Point(height.getLocation().x, height.getLocation().y+height.getSize().height+5), new Point(name.getSize().width, name.getSize().height), players[i], true, 0, 15);
			GLabel position = new GLabel(playerDetailInfos[i].getPosition(), new Point(weight.getLocation().x, weight.getLocation().y+weight.getSize().height+5), new Point(name.getSize().width, name.getSize().height), players[i], true, 0, 15);
		}
		
		name0 = new GLabel(playerDetailInfos[0].getName(), new Point(140, 10), new Point(120, 30), dataLebel, true, 0, 15);
		name0.setHorizontalAlignment(JLabel.CENTER);
		name1 = new GLabel(playerDetailInfos[1].getName(), new Point(260, 10), new Point(120, 30), dataLebel, true, 0, 15);
		name1.setHorizontalAlignment(JLabel.CENTER);
		
		int k = 0;
		for(int i=0;i<select.length;i++){
			if(select[i]){
				k = i;
				break;
			}
		}
		
		XYSeriesCollection localXYSeriesCollection = new XYSeriesCollection();
		XYSeries localXYSeries1 = new XYSeries(playerDetailInfos[0].getName());
		XYSeries localXYSeries2 = new XYSeries(playerDetailInfos[1].getName());
		if(isNormal){
			if(isAvg){
				ArrayList<PlayerDataSeason_Avg_Basic> playerDataSeason_Avg_Basic0 = new ArrayList<>();
				ArrayList<PlayerDataSeason_Avg_Basic> playerDataSeason_Avg_Basic1 = new ArrayList<>();
				try {
					playerDataSeason_Avg_Basic0 = playerLogic_db.gets_a_b(playerDetailInfos[0].getId());
					playerDataSeason_Avg_Basic1 = playerLogic_db.gets_a_b(playerDetailInfos[1].getId());
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				for(int j=0;j<playerDataSeason_Avg_Basic0.size();j++){
					localXYSeries1.add(changeSeason(playerDataSeason_Avg_Basic0.get(j).getSeason()),
							Double.valueOf(playerDataSeason_Avg_Basic0.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")
									.equals("")?"0":playerDataSeason_Avg_Basic0.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")));
				}
				for(int j=0;j<playerDataSeason_Avg_Basic1.size();j++){
					localXYSeries2.add(changeSeason(playerDataSeason_Avg_Basic1.get(j).getSeason()), 
							Double.valueOf(playerDataSeason_Avg_Basic1.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")
									.equals("")?"0":playerDataSeason_Avg_Basic1.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")));
				}
			}else{
				ArrayList<PlayerDataSeason_Tot_Basic> playerDataSeason_Tot_Basic0 = new ArrayList<>();
				ArrayList<PlayerDataSeason_Tot_Basic> playerDataSeason_Tot_Basic1 = new ArrayList<>();
				try {
					playerDataSeason_Tot_Basic0 = playerLogic_db.gets_t_b(playerDetailInfos[0].getId());
					playerDataSeason_Tot_Basic1 = playerLogic_db.gets_t_b(playerDetailInfos[1].getId());
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				for(int j=0;j<playerDataSeason_Tot_Basic0.size();j++){
					localXYSeries1.add(changeSeason(playerDataSeason_Tot_Basic0.get(j).getSeason()), 
							Double.valueOf(playerDataSeason_Tot_Basic0.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")
									.equals("")?"0":playerDataSeason_Tot_Basic0.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")));
				}
				for(int j=0;j<playerDataSeason_Tot_Basic1.size();j++){
					localXYSeries2.add(changeSeason(playerDataSeason_Tot_Basic1.get(j).getSeason()), 
							Double.valueOf(playerDataSeason_Tot_Basic1.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")
									.equals("")?"0":playerDataSeason_Tot_Basic1.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")));
				}
			}
		}else{
			if(isAvg){
				ArrayList<PlayerDataPlayOff_Avg_Basic> playerDataPlayOff_Avg_Basic0 = new ArrayList<>();
				ArrayList<PlayerDataPlayOff_Avg_Basic> playerDataPlayOff_Avg_Basic1 = new ArrayList<>();
				try {
					playerDataPlayOff_Avg_Basic0 = playerLogic_db.getp_a_b(playerDetailInfos[0].getId());
					playerDataPlayOff_Avg_Basic1 = playerLogic_db.getp_a_b(playerDetailInfos[1].getId());
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				for(int j=0;j<playerDataPlayOff_Avg_Basic0.size();j++){
					localXYSeries1.add(changeSeason(playerDataPlayOff_Avg_Basic0.get(j).getSeason()), 
							Double.valueOf(playerDataPlayOff_Avg_Basic0.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")
									.equals("")?"0":playerDataPlayOff_Avg_Basic0.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")));
				}
				for(int j=0;j<playerDataPlayOff_Avg_Basic1.size();j++){
					localXYSeries2.add(changeSeason(playerDataPlayOff_Avg_Basic1.get(j).getSeason()), 
							Double.valueOf(playerDataPlayOff_Avg_Basic1.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")
									.equals("")?"0":playerDataPlayOff_Avg_Basic1.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")));
				}
			}else{
				ArrayList<PlayerDataPlayOff_Tot_Basic> playerDataPlayOff_Tot_Basic0 = new ArrayList<>();
				ArrayList<PlayerDataPlayOff_Tot_Basic> playerDataPlayOff_Tot_Basic1 = new ArrayList<>();
				try {
					playerDataPlayOff_Tot_Basic0 = playerLogic_db.getp_t_b(playerDetailInfos[0].getId());
					playerDataPlayOff_Tot_Basic1 = playerLogic_db.getp_t_b(playerDetailInfos[1].getId());
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				for(int j=0;j<playerDataPlayOff_Tot_Basic0.size();j++){
					localXYSeries1.add(changeSeason(playerDataPlayOff_Tot_Basic0.get(j).getSeason()), 
							Double.valueOf(playerDataPlayOff_Tot_Basic0.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")
									.equals("")?"0":playerDataPlayOff_Tot_Basic0.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")));
				}
				for(int j=0;j<playerDataPlayOff_Tot_Basic1.size();j++){
					localXYSeries2.add(changeSeason(playerDataPlayOff_Tot_Basic1.get(j).getSeason()), 
							Double.valueOf(playerDataPlayOff_Tot_Basic1.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")
									.equals("")?"0":playerDataPlayOff_Tot_Basic1.get(j).getProperty(dataTypeFunction[k]).replaceAll("%", "")));
				}
			}
		}

		localXYSeriesCollection.addSeries(localXYSeries1);
	    localXYSeriesCollection.addSeries(localXYSeries2);
	    
	    MyDemoPanel myDemoPanel= new MyDemoPanel(localXYSeriesCollection);
	    panel = myDemoPanel.getPanel();
	    panel.setBounds(0, 260, 420, 335);
	    this.add(panel);
	    
	    String[] message = {
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[0], isAvg, isNormal).get(k).getAvg()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[1], isAvg, isNormal).get(k).getAvg()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[0], isAvg, isNormal).get(k).getMedian()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[1], isAvg, isNormal).get(k).getMedian()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[0], isAvg, isNormal).get(k).getRange()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[1], isAvg, isNormal).get(k).getRange()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[0], isAvg, isNormal).get(k).getVar()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[1], isAvg, isNormal).get(k).getVar()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[0], isAvg, isNormal).get(k).getVarq()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[1], isAvg, isNormal).get(k).getVarq()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[0], isAvg, isNormal).get(k).getC_v()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[1], isAvg, isNormal).get(k).getC_v()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[0], isAvg, isNormal).get(k).getSkewness()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[1], isAvg, isNormal).get(k).getSkewness()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[0], isAvg, isNormal).get(k).getKurtosis()),
	    		String.valueOf(playerLogic_db.getPlayerStat(ids[1], isAvg, isNormal).get(k).getKurtosis())
	    };
	    for(int i=0;i<dataResultShowLabel.length;i++){
	    	if(i%2 == 0){
	    		dataResultShowLabel[i] = new GLabel(message[i], new Point(140, 60+(i/2)*30), new Point(120, 30), dataLebel, true, 0, 15);
	    	}else{
	    		dataResultShowLabel[i] = new GLabel(message[i], new Point(260, 60+(i/2)*30), new Point(120, 30), dataLebel, true, 0, 15);
	    	}
	    	dataResultShowLabel[i].setHorizontalAlignment(JLabel.CENTER);
	    }
	    
	    this.updateUI();
	}
	
	private Double changeSeason(String season){
		String year = season.split("-")[0];
		double number = Double.valueOf(year);
		if(number>50){
			number = 1900 + number;
		}else{
			number = 2000 + number;
		}
		return number;
	}
}

class MyDemoPanel{
  private ChartPanel panel;

  public MyDemoPanel(XYDataset xyDataset){
    super();
    this.panel = createChartPanel(xyDataset);
  }

  private ChartPanel createChartPanel(XYDataset xyDataset){
	  //创建主题样式  
	  StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
	  //设置标题字体  
	  standardChartTheme.setExtraLargeFont(new Font("宋书",Font.BOLD,20));  
	  //设置图例的字体  
	  standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
	  //设置轴向的字体  
	  standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));  
	  //应用主题样式  
	  ChartFactory.setChartTheme(standardChartTheme); 

	  NumberAxis localNumberAxis1 = new NumberAxis("season");
	  localNumberAxis1.setAutoRangeIncludesZero(false);
	  NumberAxis localNumberAxis2 = new NumberAxis("data");
	  localNumberAxis2.setAutoRangeIncludesZero(false);
	  XYLineAndShapeRenderer localXYLineAndShapeRenderer = new XYLineAndShapeRenderer();
	  XYPlot localXYPlot = new XYPlot(xyDataset, localNumberAxis1, localNumberAxis2, localXYLineAndShapeRenderer);
	  localXYPlot.setBackgroundPaint(Color.lightGray);
	  localXYPlot.setDomainGridlinePaint(Color.white);
	  localXYPlot.setRangeGridlinePaint(Color.white);
	  localXYPlot.setAxisOffset(new RectangleInsets(4.0D, 4.0D, 4.0D, 4.0D));
	  JFreeChart localJFreeChart = new JFreeChart("Data Line", JFreeChart.DEFAULT_TITLE_FONT, localXYPlot, true);
	  ChartUtilities.applyCurrentTheme(localJFreeChart);
	  ChartPanel localChartPanel = new ChartPanel(localJFreeChart);
	  return localChartPanel;
  }

  public ChartPanel getPanel(){
	  return this.panel;
  }
}
