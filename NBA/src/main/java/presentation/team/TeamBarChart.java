package presentation.team;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import data.po.playerData.PlayerDataSeason_Avg_Basic;
import data.po.playerData.PlayerDataSeason_Tot_Basic;
import bussinesslogic.player.PlayerLogic_db;
import presentation.component.BgPanel;
import presentation.component.ChartCheckBox;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;
public class TeamBarChart extends BgPanel{

	private static final long serialVersionUID = 1L;
	private PlayerLogic_db playerLogic_db;
	private String[] playerNames;
	private PlayerDataSeason_Avg_Basic[] normalAvgs;
	private ChartCheckBox[] chartChekBox;
	private JPanel layeredBarChartPanel;
	private boolean[] select;
	private boolean isFirst, isNormal;
	private GLabel selectLabel, selectButton;
	private String[] dataType = {"三分％", "命中％", "罚球％", "助攻", "抢断", "篮板", 
		"盖帽", "失误", "犯规", "分钟", "得分"};
//	private JComboBox<String> avg;

	public TeamBarChart(String players){
		super("");
		
		playerLogic_db = new PlayerLogic_db();
		this.playerNames = players.split(";");
		this.normalAvgs = new PlayerDataSeason_Avg_Basic[playerNames.length];
		try {
			for(int i=0;i<playerNames.length;i++){
				ArrayList<PlayerDataSeason_Avg_Basic> list= playerLogic_db.gets_a_b(playerLogic_db.getIDbyName(this.playerNames[i], ""), "14-15");
				if(list.size()!=0){
					normalAvgs[i] = list.get(0);
				}else{
					normalAvgs[i] = new PlayerDataSeason_Avg_Basic();
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.setBounds(0, 100, 940, 500);
		//this.setBounds(0, 2030, 940, 600);
		this.setBackground(UIUtil.bgWhite);
		this.setLayout(null);
		this.setVisible(true);
		
		isFirst = true;
		isNormal = true;
		
		init();
		chartInit();
	}

	private void init(){
		selectButton = new GLabel("筛选", new Point(850, 0), new Point(40, 25), this, true, 0, 15);
		selectButton.setHorizontalAlignment(JLabel.CENTER);
		selectButton.setForeground(UIUtil.nbaBlue);
		selectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		selectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				if(selectLabel.isVisible()){
					selectLabel.setVisible(false);
					layeredBarChartPanel.setLocation(50, 25);
				}else{
					selectLabel.setVisible(true);
					layeredBarChartPanel.setLocation(50, 105);
				}
			}
		});
		
		selectLabel = new GLabel("", new Point(0, 25), new Point(940, 80), this, true);
		selectLabel.setBackground(UIUtil.bgGrey);
		selectLabel.setOpaque(true);
		selectLabel.setVisible(false);
		
		chartChekBox = new ChartCheckBox[dataType.length];
		select = new boolean[dataType.length];
		for(int i=0;i<dataType.length;i++){
			select[i] = false;
			chartChekBox[i] = new ChartCheckBox(i);
			
			if(i<6){
				chartChekBox[i].setBounds(50+(150)*i, 10, 100, 25);
			}else{
				chartChekBox[i].setBounds(50+(150)*(i-6), 45, 100, 25);
			}
			
			chartChekBox[i].setBackground(UIUtil.bgGrey);

			chartChekBox[i].setText(dataType[i]);
			chartChekBox[i].setForeground(UIUtil.bgWhite);
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
					chartInit();
				}
			});
			selectLabel.add(chartChekBox[i]);
		}
		select[0] = true;
		chartChekBox[0].setSelected(true);

//		String []isTot = {"场均", "总数"};
//		avg = new JComboBox<String>(isTot);
//		avg.setBounds(50, 0, 100, 25);
//		this.add(avg);
//		avg.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(avg.getSelectedIndex() == 0){
//					isNormal = true;
//				}else{
//					isNormal = false;
//				}
//				chartInit();
//			}
//		});
	}
	
	private void chartInit(){
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		
		if(isNormal){
			for(int j=0;j<playerNames.length;j++){
				if(select[0]){
					if((normalAvgs[j].getThper()!=null)&&(!normalAvgs[j].getThper().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getThper().replaceAll("%", "")), dataType[0], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[0], playerNames[j]);
					}
				}
				
				if(select[1]){
					if((normalAvgs[j].getFtper()!=null)&&(!normalAvgs[j].getFtper().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getFtper().replaceAll("%", "")), dataType[1], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[1], playerNames[j]);
					}
				}
				
				if(select[2]){
					if((normalAvgs[j].getShootper()!=null)&&(!normalAvgs[j].getShootper().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getShootper().replaceAll("%", "")), dataType[2], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[2], playerNames[j]);
					}
				}
				
				if(select[3]){
					if((normalAvgs[j].getAssist()!=null)&&(!normalAvgs[j].getAssist().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getAssist().replaceAll("%", "")), dataType[3], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[3], playerNames[j]);
					}
				}
				
				if(select[4]){
					if((normalAvgs[j].getSteal()!=null)&&(!normalAvgs[j].getSteal().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getSteal().replaceAll("%", "")), dataType[4], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[4], playerNames[j]);
					}
				}
				
				if(select[5]){
					if((normalAvgs[j].getBackbound()!=null)&&(!normalAvgs[j].getBackbound().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getBackbound().replaceAll("%", "")), dataType[5], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[5], playerNames[j]);
					}
				}
				
				if(select[6]){
					if((normalAvgs[j].getRejection()!=null)&&(!normalAvgs[j].getRejection().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getRejection().replaceAll("%", "")), dataType[6], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[6], playerNames[j]);
					}
				}
				
				if(select[7]){
					if((normalAvgs[j].getMiss()!=null)&&(!normalAvgs[j].getMiss().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getMiss().replaceAll("%", "")), dataType[7], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[7], playerNames[j]);
					}
				}
				
				if(select[8]){
					if((normalAvgs[j].getFoul()!=null)&&(!normalAvgs[j].getFoul().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getFoul().replaceAll("%", "")), dataType[8], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[8], playerNames[j]);
					}
				}
				
				if(select[9]){
					if((normalAvgs[j].getTime()!=null)&&(!normalAvgs[j].getTime().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getTime().replaceAll("%", "")), dataType[9], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[9], playerNames[j]);
					}
				}
				
				if(select[10]){
					if((normalAvgs[j].getPts()!=null)&&(!normalAvgs[j].getPts().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getPts().replaceAll("%", "")), dataType[10], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[10], playerNames[j]);
					}
				}
			}
		}else{
			for(int j=0;j<playerNames.length;j++){
				if(select[0]){
					if((normalAvgs[j].getThper()!=null)&&(!normalAvgs[j].getThper().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getThper().replaceAll("%", "")), dataType[0], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[0], playerNames[j]);
					}
				}
				
				if(select[1]){
					if((normalAvgs[j].getFtper()!=null)&&(!normalAvgs[j].getFtper().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getFtper().replaceAll("%", "")), dataType[1], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[1], playerNames[j]);
					}
				}
				
				if(select[2]){
					if((normalAvgs[j].getShootper()!=null)&&(!normalAvgs[j].getShootper().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getShootper().replaceAll("%", "")), dataType[2], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[2], playerNames[j]);
					}
				}
				
				if(select[3]){
					if((normalAvgs[j].getAssist()!=null)&&(!normalAvgs[j].getAssist().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getAssist().replaceAll("%", "")), dataType[3], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[3], playerNames[j]);
					}
				}
				
				if(select[4]){
					if((normalAvgs[j].getSteal()!=null)&&(!normalAvgs[j].getSteal().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getSteal().replaceAll("%", "")), dataType[4], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[4], playerNames[j]);
					}
				}
				
				if(select[5]){
					if((normalAvgs[j].getBackbound()!=null)&&(!normalAvgs[j].getBackbound().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getBackbound().replaceAll("%", "")), dataType[5], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[5], playerNames[j]);
					}
				}
				
				if(select[6]){
					if((normalAvgs[j].getRejection()!=null)&&(!normalAvgs[j].getRejection().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getRejection().replaceAll("%", "")), dataType[6], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[6], playerNames[j]);
					}
				}
				
				if(select[7]){
					if((normalAvgs[j].getMiss()!=null)&&(!normalAvgs[j].getMiss().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getMiss().replaceAll("%", "")), dataType[7], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[7], playerNames[j]);
					}
				}
				
				if(select[8]){
					if((normalAvgs[j].getFoul()!=null)&&(!normalAvgs[j].getFoul().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getFoul().replaceAll("%", "")), dataType[8], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[8], playerNames[j]);
					}
				}
				
				if(select[9]){
					if((normalAvgs[j].getTime()!=null)&&(!normalAvgs[j].getTime().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getTime().replaceAll("%", "")), dataType[9], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[9], playerNames[j]);
					}
				}
				
				if(select[10]){
					if((normalAvgs[j].getPts()!=null)&&(!normalAvgs[j].getPts().replaceAll("%", "").equals(""))){
						localDefaultCategoryDataset.addValue(Double.valueOf(normalAvgs[j].getPts().replaceAll("%", "")), dataType[10], playerNames[j]);
					}else{
						localDefaultCategoryDataset.addValue(new Double(0), dataType[10], playerNames[j]);
					}
				}
			}
		}

		if(!isFirst)this.remove(layeredBarChartPanel);
		LayeredBarChart layeredBarChart = new LayeredBarChart(localDefaultCategoryDataset);
		layeredBarChartPanel = layeredBarChart.getPanel();
		if(selectLabel.isVisible()){
			layeredBarChartPanel.setBounds(50, 105, 840, 450);
		}else{
			layeredBarChartPanel.setBounds(50, 25, 840, 450);
		}
		this.add(layeredBarChartPanel);
		
		this.updateUI();
	}
	
	private void setFalse(){
		for(int i=0;i<dataType.length;i++){
			select[i] = false;
			chartChekBox[i].setSelected(false);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frame = new JFrame();  
				frame.setSize(1000,700);
				frame.setVisible(true); 

				JLabel panel = new JLabel();
				panel.setBounds(0, 0, 940, 600);
				panel.setVisible(true);
				panel.setLayout(null);

				TeamBarChart teamBarChart = new TeamBarChart("科怀-伦纳德;托尼-帕克;蒂姆-邓肯;丹尼-格林;马努-吉诺比利;"+
				"马科-贝里内利;伯瑞斯-迪奥;蒂亚戈-斯普里特;帕特里克-米尔斯;科里-约瑟夫;阿隆-贝恩斯;奥斯丁-达耶;马特-邦纳;"+
				"杰夫-艾尔斯;凯尔-安德森;JaMychal Green;雷吉-威廉姆斯;");
				
				panel.add(teamBarChart);
				frame.add(panel);
			}
		});
	}
}

class LayeredBarChart{
	
	private ChartPanel chartPanel;
	
	public LayeredBarChart(CategoryDataset categoryDataset){
		JFreeChart localJFreeChart = createChart(categoryDataset);
		chartPanel = new ChartPanel(localJFreeChart);
	}

	private static JFreeChart createChart(CategoryDataset paramCategoryDataset){
		JFreeChart localJFreeChart = ChartFactory.createBarChart("Team Members Data (Season Avg) ", "players", "data", paramCategoryDataset);
	    CategoryPlot localCategoryPlot = (CategoryPlot)localJFreeChart.getPlot();
	    localCategoryPlot.setDomainGridlinesVisible(true);
	    localCategoryPlot.setRangeCrosshairVisible(true);
	    localCategoryPlot.setRangeCrosshairPaint(Color.blue);
	    NumberAxis localNumberAxis = (NumberAxis)localCategoryPlot.getRangeAxis();
	    localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	    BarRenderer localBarRenderer = (BarRenderer)localCategoryPlot.getRenderer();
	    localBarRenderer.setDrawBarOutline(false);
	    GradientPaint localGradientPaint1 = new GradientPaint(0.0F, 0.0F, UIUtil.nbaBlue, 0.0F, 0.0F, UIUtil.nbaBlue);
	    GradientPaint localGradientPaint2 = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F, new Color(0, 64, 0));
	    GradientPaint localGradientPaint3 = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, new Color(64, 0, 0));
	    localBarRenderer.setSeriesPaint(0, localGradientPaint1);
	    localBarRenderer.setSeriesPaint(1, localGradientPaint2);
	    localBarRenderer.setSeriesPaint(2, localGradientPaint3);
	    localBarRenderer.setLegendItemToolTipGenerator(new StandardCategorySeriesLabelGenerator("Tooltip: {0}"));
	    CategoryAxis localCategoryAxis = localCategoryPlot.getDomainAxis();
	    localCategoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.5235987755982988D));
	    return localJFreeChart;
	}

	public JPanel getPanel(){
		return chartPanel;
	}
}