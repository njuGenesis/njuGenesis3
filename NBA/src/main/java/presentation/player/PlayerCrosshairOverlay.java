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
	private CrosshairOverlayPanel crosshairOverlayPanel;
	private boolean[] select;
	private GLabel selectButton, selectLabel;
	private ChartCheckBox[] selectBox;
	private boolean isFirst, isAvg;
	private JCheckBox avg, total;

	public PlayerCrosshairOverlay(int id) {
		super("");

		playerLogic_db = new PlayerLogic_db();
		try {
			playerDataSeason_Avg_Basic = playerLogic_db.gets_a_b(id);
			playerDataSeason_Tot_Basic = playerLogic_db.gets_t_b(id);
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

		avg = new JCheckBox("场均");
		avg.setBounds(130, 0, 70, 25);
		avg.setSelected(true);
		avg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFirst = false;

				if(avg.isSelected()){
					total.setSelected(false);
					isAvg = true;
					initCrosshairOverlay();
				}else{
					total.setSelected(true);
					isAvg = false;
					initCrosshairOverlay();
				}
			}
		});
		this.add(avg);
		total = new JCheckBox("总数");
		total.setBounds(200, 0, 70, 25);
		total.setSelected(false);
		total.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFirst = false;

				if(total.isSelected()){
					avg.setSelected(false);
					isAvg = false;
					initCrosshairOverlay();
				}else{
					avg.setSelected(true);
					isAvg = true;
					initCrosshairOverlay();
				}
			}
		});
		this.add(total);

		selectButton = new GLabel("筛选", new Point(770, 0), new Point(40, 25), this, true, 0, 15);
		selectButton.setHorizontalAlignment(JLabel.CENTER);
		selectButton.setForeground(UIUtil.nbaBlue);
		selectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		selectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				if(selectLabel.isVisible()){
					selectLabel.setVisible(false);
					crosshairOverlayPanel.setLocation(130, 25);
				}else{
					selectLabel.setVisible(true);
					crosshairOverlayPanel.setLocation(130, 105);
				}
			}
		});

		selectLabel = new GLabel("", new Point(0, 25), new Point(940, 80), this, true);
		selectLabel.setBackground(UIUtil.bgGrey);
		selectLabel.setOpaque(true);
		selectLabel.setVisible(false);

		String[] selectData = {"三分％", "命中％", "罚球％", "助攻", "抢断", "篮板", 
				"盖帽", "失误", "犯规", "分钟", "得分"};

		select = new boolean[selectData.length];
		selectBox = new ChartCheckBox[select.length];
		for(int i=0;i<selectBox.length;i++){
			select[i] = false;
			
			selectBox[i] = new ChartCheckBox(i);

			if(i<6){
				selectBox[i].setBounds(50+(150)*i, 10, 100, 25);
			}else{
				selectBox[i].setBounds(50+(150)*(i-6), 45, 100, 25);
			}

			selectBox[i].setText(selectData[i]);
			selectBox[i].setForeground(UIUtil.bgWhite);
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

	}

	private void initCrosshairOverlay(){
		XYSeriesCollection localXYSeriesCollection = new XYSeriesCollection();

		if(select[0]){
			XYSeries XYSeries1 = new XYSeries("三分％");
			if(isAvg){
				for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
					if((!playerDataSeason_Avg_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Avg_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Avg_Basic.get(i).getThper().equals("")){
							XYSeries1.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Avg_Basic.get(i).getThper().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basic.get(i).getThper().replaceAll("%", "")));
						}else XYSeries1.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}else{
				for(int i=0;i<playerDataSeason_Tot_Basic.size();i++){
					if((!playerDataSeason_Tot_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Tot_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Tot_Basic.get(i).getThper().equals("")){
							XYSeries1.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Tot_Basic.get(i).getThper().replaceAll("%", "").equals("")?"0":playerDataSeason_Tot_Basic.get(i).getThper().replaceAll("%", "")));
						}else XYSeries1.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}
			localXYSeriesCollection.addSeries(XYSeries1);
		}

		if(select[1]){
			XYSeries XYSeries2 = new XYSeries("命中％");
			if(isAvg){
				for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
					if((!playerDataSeason_Avg_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Avg_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Avg_Basic.get(i).getThper().equals("")){
							XYSeries2.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Avg_Basic.get(i).getShootper().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basic.get(i).getShootper().replaceAll("%", "")));
						}else XYSeries2.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}else{
				for(int i=0;i<playerDataSeason_Tot_Basic.size();i++){
					if((!playerDataSeason_Tot_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Tot_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Tot_Basic.get(i).getThper().equals("")){
							XYSeries2.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Tot_Basic.get(i).getShootper().replaceAll("%", "").equals("")?"0":playerDataSeason_Tot_Basic.get(i).getShootper().replaceAll("%", "")));
						}else XYSeries2.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}
			localXYSeriesCollection.addSeries(XYSeries2);
		}

		if(select[2]){
			XYSeries XYSeries3 = new XYSeries("罚球％");
			if(isAvg){
				for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
					if((!playerDataSeason_Avg_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Avg_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Avg_Basic.get(i).getThper().equals("")){
							XYSeries3.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Avg_Basic.get(i).getFtper().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basic.get(i).getFtper().replaceAll("%", "")));
						}else XYSeries3.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}else{
				for(int i=0;i<playerDataSeason_Tot_Basic.size();i++){
					if((!playerDataSeason_Tot_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Tot_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Tot_Basic.get(i).getThper().equals("")){
							XYSeries3.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Tot_Basic.get(i).getFtper().replaceAll("%", "").equals("")?"0":playerDataSeason_Tot_Basic.get(i).getFtper().replaceAll("%", "")));
						}else XYSeries3.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}
			localXYSeriesCollection.addSeries(XYSeries3);
		}

		if(select[3]){
			XYSeries XYSeries4 = new XYSeries("助攻");
			if(isAvg){
				for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
					if((!playerDataSeason_Avg_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Avg_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Avg_Basic.get(i).getThper().equals("")){
							XYSeries4.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Avg_Basic.get(i).getAssist().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basic.get(i).getAssist().replaceAll("%", "")));
						}else XYSeries4.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}else{
				for(int i=0;i<playerDataSeason_Tot_Basic.size();i++){
					if((!playerDataSeason_Tot_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Tot_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Tot_Basic.get(i).getThper().equals("")){
							XYSeries4.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Tot_Basic.get(i).getAssist().replaceAll("%", "").equals("")?"0":playerDataSeason_Tot_Basic.get(i).getAssist().replaceAll("%", "")));
						}else XYSeries4.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}
			localXYSeriesCollection.addSeries(XYSeries4);
		}

		if(select[4]){
			XYSeries XYSeries5 = new XYSeries("抢断");
			if(isAvg){
				for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
					if((!playerDataSeason_Avg_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Avg_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Avg_Basic.get(i).getThper().equals("")){
							XYSeries5.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Avg_Basic.get(i).getSteal().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basic.get(i).getSteal().replaceAll("%", "")));
						}else XYSeries5.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}else{
				for(int i=0;i<playerDataSeason_Tot_Basic.size();i++){
					if((!playerDataSeason_Tot_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Tot_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Tot_Basic.get(i).getThper().equals("")){
							XYSeries5.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Tot_Basic.get(i).getSteal().replaceAll("%", "").equals("")?"0":playerDataSeason_Tot_Basic.get(i).getSteal().replaceAll("%", "")));
						}else XYSeries5.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}
			localXYSeriesCollection.addSeries(XYSeries5);
		}

		if(select[5]){
			XYSeries XYSeries6 = new XYSeries("篮板");
			if(isAvg){
				for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
					if((!playerDataSeason_Avg_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Avg_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Avg_Basic.get(i).getThper().equals("")){
							XYSeries6.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Avg_Basic.get(i).getBackbound().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basic.get(i).getBackbound().replaceAll("%", "")));
						}else XYSeries6.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}else{
				for(int i=0;i<playerDataSeason_Tot_Basic.size();i++){
					if((!playerDataSeason_Tot_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Tot_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Tot_Basic.get(i).getThper().equals("")){
							XYSeries6.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Tot_Basic.get(i).getBackbound().replaceAll("%", "").equals("")?"0":playerDataSeason_Tot_Basic.get(i).getBackbound().replaceAll("%", "")));
						}else XYSeries6.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}
			localXYSeriesCollection.addSeries(XYSeries6);
		}

		if(select[6]){
			XYSeries XYSeries7 = new XYSeries("盖帽");
			if(isAvg){
				for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
					if((!playerDataSeason_Avg_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Avg_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Avg_Basic.get(i).getThper().equals("")){
							XYSeries7.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Avg_Basic.get(i).getRejection().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basic.get(i).getRejection().replaceAll("%", "")));
						}else XYSeries7.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}else{
				for(int i=0;i<playerDataSeason_Tot_Basic.size();i++){
					if((!playerDataSeason_Tot_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Tot_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Tot_Basic.get(i).getThper().equals("")){
							XYSeries7.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Tot_Basic.get(i).getRejection().replaceAll("%", "").equals("")?"0":playerDataSeason_Tot_Basic.get(i).getRejection().replaceAll("%", "")));
						}else XYSeries7.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}
			localXYSeriesCollection.addSeries(XYSeries7);
		}

		if(select[7]){
			XYSeries XYSeries8 = new XYSeries("失误");
			if(isAvg){
				for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
					if((!playerDataSeason_Avg_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Avg_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Avg_Basic.get(i).getThper().equals("")){
							XYSeries8.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Avg_Basic.get(i).getMiss().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basic.get(i).getMiss().replaceAll("%", "")));
						}else XYSeries8.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}else{
				for(int i=0;i<playerDataSeason_Tot_Basic.size();i++){
					if((!playerDataSeason_Tot_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Tot_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Tot_Basic.get(i).getThper().equals("")){
							XYSeries8.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Tot_Basic.get(i).getMiss().replaceAll("%", "").equals("")?"0":playerDataSeason_Tot_Basic.get(i).getMiss().replaceAll("%", "")));
						}else XYSeries8.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}
			localXYSeriesCollection.addSeries(XYSeries8);
		}

		if(select[8]){
			XYSeries XYSeries9 = new XYSeries("犯规");
			if(isAvg){
				for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
					if((!playerDataSeason_Avg_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Avg_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Avg_Basic.get(i).getThper().equals("")){
							XYSeries9.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Avg_Basic.get(i).getFoul().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basic.get(i).getFoul().replaceAll("%", "")));
						}else XYSeries9.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}else{
				for(int i=0;i<playerDataSeason_Tot_Basic.size();i++){
					if((!playerDataSeason_Tot_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Tot_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Tot_Basic.get(i).getThper().equals("")){
							XYSeries9.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Tot_Basic.get(i).getFoul().replaceAll("%", "").equals("")?"0":playerDataSeason_Tot_Basic.get(i).getFoul().replaceAll("%", "")));
						}else XYSeries9.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}
			localXYSeriesCollection.addSeries(XYSeries9);
		}

		if(select[9]){
			XYSeries XYSeries10 = new XYSeries("分钟");
			if(isAvg){
				for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
					if((!playerDataSeason_Avg_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Avg_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Avg_Basic.get(i).getThper().equals("")){
							XYSeries10.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Avg_Basic.get(i).getTime().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basic.get(i).getTime().replaceAll("%", "")));
						}else XYSeries10.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}else{
				for(int i=0;i<playerDataSeason_Tot_Basic.size();i++){
					if((!playerDataSeason_Tot_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Tot_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Tot_Basic.get(i).getThper().equals("")){
							XYSeries10.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Tot_Basic.get(i).getTime().replaceAll("%", "").equals("")?"0":playerDataSeason_Tot_Basic.get(i).getTime().replaceAll("%", "")));
						}else XYSeries10.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}
			localXYSeriesCollection.addSeries(XYSeries10);
		}

		if(select[10]){
			XYSeries XYSeries11 = new XYSeries("得分");
			if(isAvg){
				for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
					if((!playerDataSeason_Avg_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Avg_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Avg_Basic.get(i).getThper().equals("")){
							XYSeries11.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Avg_Basic.get(i).getPts().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basic.get(i).getPts().replaceAll("%", "")));
						}else XYSeries11.add(changeSeason(playerDataSeason_Avg_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}else{
				for(int i=0;i<playerDataSeason_Tot_Basic.size();i++){
					if((!playerDataSeason_Tot_Basic.get(i).getSeason().contains("年"))
							&&(!playerDataSeason_Tot_Basic.get(i).getTeam().contains("总计"))){
						if(!playerDataSeason_Tot_Basic.get(i).getThper().equals("")){
							XYSeries11.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), 
									Double.valueOf(playerDataSeason_Tot_Basic.get(i).getPts().replaceAll("%", "").equals("")?"0":playerDataSeason_Tot_Basic.get(i).getPts().replaceAll("%", "")));
						}else XYSeries11.add(changeSeason(playerDataSeason_Tot_Basic.get(i).getSeason()), new Double(0));
					}
				}
			}
			localXYSeriesCollection.addSeries(XYSeries11);
		}

		if(!isFirst)this.remove(crosshairOverlayPanel);

		crosshairOverlayPanel = new CrosshairOverlayPanel(localXYSeriesCollection);
		crosshairOverlayPanel.setVisible(true);
		if(selectLabel.isVisible()){
			crosshairOverlayPanel.setBounds(130, 105, 680, 425);
		}else{
			crosshairOverlayPanel.setBounds(130, 25, 680, 425);
		}
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
				frame.setSize(940,560);
				frame.setVisible(true); 

				JLabel panel = new JLabel();
				panel.setBounds(0, 0, 940, 560);
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
		JFreeChart localJFreeChart = ChartFactory.createXYLineChart("球员场均数据折线统计", "赛季", "数值", paramXYDataset);
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