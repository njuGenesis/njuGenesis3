package presentation.player;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;

import data.po.playerData.PlayerDataPlayOff_Avg_Basic;
import data.po.playerData.PlayerDataPlayOff_Tot_Basic;
import data.po.playerData.PlayerDataSeason_Avg_Basic;
import data.po.playerData.PlayerDataSeason_Tot_Basic;
import bussinesslogic.player.PlayerLogic_db;
import presentation.component.BgPanel;
import presentation.component.ChartCheckBox;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;

public class PlayerCrosshairOverlay extends BgPanel{

	private static final long serialVersionUID = 1L;
	private PlayerLogic_db playerLogic_db;
	private ArrayList<PlayerDataSeason_Avg_Basic> playerDataSeason_Avg_Basic;
	private ArrayList<PlayerDataSeason_Tot_Basic> playerDataSeason_Tot_Basic;
	private ArrayList<PlayerDataPlayOff_Tot_Basic> playerDataPlayOff_Tot_Basic;
	private ArrayList<PlayerDataPlayOff_Avg_Basic> playerDataPlayOff_Avg_Basic;
	private CrosshairOverlayPanel crosshairOverlayPanel;
	private boolean[] select;
	private GLabel selectLabel;
	private ChartCheckBox[] selectBox;
	private boolean isFirst, isAvg, isNormal;
	private JCheckBox avg, total;
	private JComboBox<String> comboBoxNormal, comboBoxAvg;
	private String[] selectData = {"三分％", "命中％", "罚球％", "助攻", "抢断", "篮板", 
			"盖帽", "失误", "犯规", "分钟", "得分"};
	private String[] dataTypeFunction = {"thper","shootper","ftper","assist","steal","backbound","rejection","miss","foul","time","pts"};

	public PlayerCrosshairOverlay(int id) {
		super("");

		playerLogic_db = new PlayerLogic_db();
		try {
			playerDataSeason_Avg_Basic = playerLogic_db.gets_a_b(id);
			playerDataSeason_Tot_Basic = playerLogic_db.gets_t_b(id);
			playerDataPlayOff_Avg_Basic = playerLogic_db.getp_a_b(id);
			playerDataPlayOff_Tot_Basic = playerLogic_db.getp_t_b(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		this.setLayout(null);
		this.setBounds(0, 100, 940, 500);
		//this.setBounds(0, 0, 940, 530);
		this.setBackground(UIUtil.bgWhite);
		this.setVisible(true);

		init();
		initCrosshairOverlay();
	}

	private void init(){
		isFirst = true;
		isAvg = true;
		isNormal = true;

		selectLabel = new GLabel("", new Point(680, 25), new Point(260, 425), this, true);
		selectLabel.setBackground(UIUtil.bgWhite);
		selectLabel.setOpaque(true);

		select = new boolean[selectData.length];
		selectBox = new ChartCheckBox[select.length];
		for(int i=0;i<selectBox.length;i++){
			select[i] = false;
			
			selectBox[i] = new ChartCheckBox(i);

			if(i%2==0){
				selectBox[i].setBounds(30, 10+(i/2)*45, 100, 25);
			}else{
				selectBox[i].setBounds(130, 10+(i/2)*45, 100, 25);
			}
			
			selectBox[i].setOpaque(true);
			selectBox[i].setBackground(UIUtil.bgWhite);

			selectBox[i].setText(selectData[i]);
			selectBox[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					isFirst = false;
					ChartCheckBox chartChekBox = (ChartCheckBox)e.getSource();
					if(!chartChekBox.isSelected()){
						select[chartChekBox.getNumber()] = false;
					}else{
						select[chartChekBox.getNumber()] = true;
					}
					initCrosshairOverlay();
				}
			});
			selectLabel.add(selectBox[i]);
		}
		select[0] = true;
		select[1] = true;
		select[2] = true;
		select[3] = true;
		select[5] = true;
		selectBox[0].setSelected(true);
		selectBox[1].setSelected(true);
		selectBox[2].setSelected(true);
		selectBox[3].setSelected(true);
		selectBox[5].setSelected(true);
		
		String []normal = {"常规赛", "季后赛"};
		comboBoxNormal = new JComboBox<String>(normal);
		comboBoxNormal.setBounds(30, 10+(selectBox.length/2)*45+selectBox[0].getSize().height+30, 200, 30);
		comboBoxNormal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBoxNormal.getSelectedIndex() == 0){
					isNormal = true;
				}else{
					isNormal = false;
				}
				initCrosshairOverlay();
			}
		});
		selectLabel.add(comboBoxNormal);
		
		String []avg = {"场均", "总数"};
		comboBoxAvg = new JComboBox<String>(avg);
		comboBoxAvg.setBounds(30, 10+(selectBox.length/2)*45+selectBox[0].getSize().height+90, 200, 30);
		comboBoxAvg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBoxAvg.getSelectedIndex() == 0){
					isAvg = true;
				}else{
					isAvg = false;
				}
				initCrosshairOverlay();
			}
		});
		selectLabel.add(comboBoxAvg);

	}

	private void initCrosshairOverlay(){
		XYSeriesCollection localXYSeriesCollection = new XYSeriesCollection();
		
		for(int i=0;i<select.length;i++){
			if(select[i]){
				XYSeries XYSeries1 = new XYSeries(selectData[i]);
				if(isNormal){
					if(isAvg){
						for(int j=0;j<playerDataSeason_Avg_Basic.size();j++){
							if((!playerDataSeason_Avg_Basic.get(j).getSeason().contains("年"))
									&&(!playerDataSeason_Avg_Basic.get(j).getTeam().contains("总计"))){
								XYSeries1.add(changeSeason(playerDataSeason_Avg_Basic.get(j).getSeason()), 
										Double.valueOf(playerDataSeason_Avg_Basic.get(j).getProperty(dataTypeFunction[i]).replaceAll("%", "").
												equals("")?"0":playerDataSeason_Avg_Basic.get(j).getProperty(dataTypeFunction[i]).replaceAll("%", "")));
							}
						}
					}else{
						for(int j=0;j<playerDataSeason_Tot_Basic.size();j++){
							if((!playerDataSeason_Tot_Basic.get(j).getSeason().contains("年"))
									&&(!playerDataSeason_Tot_Basic.get(j).getTeam().contains("总计"))){
								XYSeries1.add(changeSeason(playerDataSeason_Tot_Basic.get(j).getSeason()), 
										Double.valueOf(playerDataSeason_Tot_Basic.get(j).getProperty(dataTypeFunction[i]).replaceAll("%", "").
												equals("")?"0":playerDataSeason_Tot_Basic.get(j).getProperty(dataTypeFunction[i]).replaceAll("%", "")));
							}
						}
					}
				}else{
					if(isAvg){
						for(int j=0;j<playerDataPlayOff_Avg_Basic.size();j++){
							if((!playerDataPlayOff_Avg_Basic.get(j).getSeason().contains("年"))
									&&(!playerDataPlayOff_Avg_Basic.get(j).getTeam().contains("总计"))){
								XYSeries1.add(changeSeason(playerDataPlayOff_Avg_Basic.get(j).getSeason()), 
										Double.valueOf(playerDataPlayOff_Avg_Basic.get(j).getProperty(dataTypeFunction[i]).replaceAll("%", "").
												equals("")?"0":playerDataPlayOff_Avg_Basic.get(j).getProperty(dataTypeFunction[i]).replaceAll("%", "")));
							}
						}
					}else{
						for(int j=0;j<playerDataPlayOff_Tot_Basic.size();j++){
							if((!playerDataPlayOff_Tot_Basic.get(j).getSeason().contains("年"))
									&&(!playerDataPlayOff_Tot_Basic.get(j).getTeam().contains("总计"))){
								XYSeries1.add(changeSeason(playerDataPlayOff_Tot_Basic.get(j).getSeason()), 
										Double.valueOf(playerDataPlayOff_Tot_Basic.get(j).getProperty(dataTypeFunction[i]).replaceAll("%", "").
												equals("")?"0":playerDataPlayOff_Tot_Basic.get(j).getProperty(dataTypeFunction[i]).replaceAll("%", "")));
							}
						}
					}
				}
				localXYSeriesCollection.addSeries(XYSeries1);
			}
		}

		if(!isFirst)this.remove(crosshairOverlayPanel);

		crosshairOverlayPanel = new CrosshairOverlayPanel(localXYSeriesCollection);
		crosshairOverlayPanel.setVisible(true);
		crosshairOverlayPanel.setBounds(0, 25, 680, 425);
		this.add(crosshairOverlayPanel);

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

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frame = new JFrame();  
				frame.setSize(940,600);
				frame.setVisible(true); 

				JLabel panel = new JLabel();
				panel.setBounds(0, -100, 940, 700);
				panel.setVisible(true);
				panel.setLayout(null);

				PlayerCrosshairOverlay playerCrosshairOverlay = new PlayerCrosshairOverlay(44);
				panel.add(playerCrosshairOverlay);
				frame.add(panel);
			}
		});
	}
}

class CrosshairOverlayPanel extends JPanel implements ChartMouseListener{
	private static final long serialVersionUID = 1L;
	private ChartPanel chartPanel;
	private Crosshair xCrosshair;
	private Crosshair[] yCrosshairs;

	public CrosshairOverlayPanel(XYDataset xyDataset){
		super();

		JFreeChart localJFreeChart = createChart(xyDataset);
		this.chartPanel = new ChartPanel(localJFreeChart);
		this.chartPanel.addChartMouseListener(this);

		CrosshairOverlay localCrosshairOverlay = new CrosshairOverlay();
		this.xCrosshair = new Crosshair((0.0D / 0.0D), Color.GRAY, new BasicStroke(0.0F));
		this.xCrosshair.setLabelVisible(true);
		localCrosshairOverlay.addDomainCrosshair(this.xCrosshair);
		this.yCrosshairs = new Crosshair[xyDataset.getItemCount(0)];
		for (int i = 0; i < yCrosshairs.length; i++){
			this.yCrosshairs[i] = new Crosshair((0.0D / 0.0D), Color.GRAY, new BasicStroke(0.0F));
			this.yCrosshairs[i].setLabelVisible(true);
			if (i % 2 != 0)
				this.yCrosshairs[i].setLabelAnchor(RectangleAnchor.TOP_RIGHT);
			localCrosshairOverlay.addRangeCrosshair(this.yCrosshairs[i]);
		}
		this.chartPanel.addOverlay(localCrosshairOverlay);
		this.setBackground(UIUtil.nbaBlue);
		this.chartPanel.updateUI();
		this.removeAll();
		add(this.chartPanel);
		this.updateUI();
	}

	private JFreeChart createChart(XYDataset paramXYDataset){
		JFreeChart localJFreeChart = ChartFactory.createXYLineChart("Player Line", "season", "data", paramXYDataset);
		return localJFreeChart;
	}

	private XYDataset createDataset(){
		XYSeriesCollection localXYSeriesCollection = new XYSeriesCollection();

		XYSeries XYSeries1 = new XYSeries("三分％");
		XYSeries1.add(2, 19);XYSeries1.add(1, 9);XYSeries1.add(2, 9);XYSeries1.add(3, 9);XYSeries1.add(4, 9);XYSeries1.add(5, 9);XYSeries1.add(6, 9);
		localXYSeriesCollection.addSeries(XYSeries1);
		//		for (int i = 0; i < 5; i++){
		//			XYSeries localXYSeries = new XYSeries("S" + i);
		//			for (int j = 0; j < 10; j++){
		//				localXYSeries.add(j, j + Math.random() * 4.0D);
		//			}
		//			localXYSeriesCollection.addSeries(localXYSeries);
		//		}
		return localXYSeriesCollection;
	}

	public void chartMouseClicked(ChartMouseEvent paramChartMouseEvent){
	}

	public void chartMouseMoved(ChartMouseEvent paramChartMouseEvent){
		//		Rectangle2D localRectangle2D = this.chartPanel.getScreenDataArea();
		//		JFreeChart localJFreeChart = paramChartMouseEvent.getChart();
		//		XYPlot localXYPlot = (XYPlot)localJFreeChart.getPlot();
		//		ValueAxis localValueAxis = localXYPlot.getDomainAxis();
		//		double d1 = localValueAxis.java2DToValue(paramChartMouseEvent.getTrigger().getX(), localRectangle2D, RectangleEdge.BOTTOM);
		//		this.xCrosshair.setValue(d1);
		//		for (int i = 0; i < 3; i++){
		//			double d2 = DatasetUtilities.findYValue(localXYPlot.getDataset(), i, d1);
		//			this.yCrosshairs[i].setValue(d2);
		//		}
	}
}