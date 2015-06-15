package presentation.player;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;
import presentation.mainui.WebTable;
import bussinesslogic.match.MatchLogic;
import bussinesslogic.player.PlayerLogic_db;
import data.po.matchData.MatchPlayer;

public class PlayerMatch extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private WebTable basicTable;
	private MatchLogic matchLogic;
	private PlayerLogic_db playerLogic_db;
	private Rectangle rectangle;
	private GLabel label, border;
	private ArrayList<MatchPlayer> matchPlayer;
	private JComboBox<String> comboBox;
	private String playerName;

	public PlayerMatch(int id) {
		super("");

		playerLogic_db = new PlayerLogic_db();
		try {
			this.playerName = playerLogic_db.getdetail(id).getNameCn().equals("null")?playerLogic_db.getdetail(id).getName():playerLogic_db.getdetail(id).getNameCn();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}System.out.println(playerName);
		matchLogic = new MatchLogic();
		matchPlayer = matchLogic.GetPlayerMatch(playerName, "unknown", "unknown");
		Vector<String> season = new Vector<String>();
		for(int i=0;i<matchPlayer.size();i++){
			if(i==0){
				season.add(matchPlayer.get(i).getSeason());
			}else{
				for(int j=0;j<season.size();j++){
					if(matchPlayer.get(i).getSeason().equals(season.get(i))){
						continue;
					}
					season.add(matchPlayer.get(i).getSeason());
				}
			}
		}
		for(int i=0;i<season.size()-1;i++){System.out.println(season.get(i));
			for(int j=0;j<season.size()-1;j++){
				String a = season.get(j);
				String b = season.get(j+1);
				if(a.compareTo(b)<0){
					season.set(j, b);
					season.set(j+1, a);
				}
			}
		}
		
		matchPlayer = matchLogic.GetPlayerMatch(playerName, "unknown", season.get(0));
		
		comboBox = new JComboBox<String>(season);
		comboBox.setBounds(600, 0, 100, 30);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				matchPlayer = matchLogic.GetPlayerMatch(PlayerMatch.this.playerName, "unknown", comboBox.getSelectedItem().toString());
				basicSetting();
			}
		});

		this.setBounds(0, 1050, 940, 400);
		this.setLayout(null);
		this.setVisible(true);
		this.setBackground(UIUtil.bgWhite);

		init();

	}

	private void init(){
		rectangle = new Rectangle(50, 40, 840, 360);

		label = new GLabel("比赛信息", new Point(100, 0), new Point(100, 30), this, true, 0, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(UIUtil.bgWhite);
		label.setOpaque(true);
		
		border = new GLabel("", new Point(0, 13), new Point(300, 4), this, true);
		border.setBackground(UIUtil.nbaBlue);
		border.setOpaque(true);

		basicSetting();

	}
	
	private void basicSetting(){
		String[] header = {"日期", "客－主", "首发", "在场时间", "得分", "命中％", "出手", 
				"出手命中", "三分％", "三分出手", "三分命中", "罚球％", "罚球", "罚球命中", "篮板", 
				"进攻篮板", "防守篮板", "助攻", "抢断", "盖帽", "失误", "犯规", "比赛链接"};
		
		Object[][] data = new Object[matchPlayer.size()][header.length];
		for(int i=matchPlayer.size()-1;i>=0;i--){
			data[i][0] = matchPlayer.get(i).getDate();
			data[i][0] = matchPlayer.get(i).getTwoteam();
			if(matchPlayer.get(i).getIsFirst().equals("1")){
				data[i][0] = "是";
			}else{
				data[i][0] = "否";
			}
			data[i][0] = matchPlayer.get(i).getTime();
			data[i][0] = matchPlayer.get(i).getPoints();
			data[i][0] = matchPlayer.get(i).getShootEff();
			data[i][0] = matchPlayer.get(i).getShoot();
			data[i][0] = matchPlayer.get(i).getShootEffNumber();
			data[i][0] = matchPlayer.get(i).getTPShootEff();
			data[i][0] = matchPlayer.get(i).getTPShoot();
			data[i][0] = matchPlayer.get(i).getTPShootEffNumber();
			data[i][0] = matchPlayer.get(i).getFTShootEff();
			data[i][0] = matchPlayer.get(i).getFT();
			data[i][0] = matchPlayer.get(i).getFTShootEffNumber();
			data[i][0] = matchPlayer.get(i).getBank();
			data[i][0] = matchPlayer.get(i).getBankOff();
			data[i][0] = matchPlayer.get(i).getBankDef();
			data[i][0] = matchPlayer.get(i).getAss();
			data[i][0] = matchPlayer.get(i).getSteal();
			data[i][0] = matchPlayer.get(i).getRejection();
			data[i][0] = matchPlayer.get(i).getTo();
			data[i][0] = matchPlayer.get(i).getFoul();
			data[i][0] = matchPlayer.get(i).getMatchID();
		}
		
		basicTable = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		basicTable.setVisible(true);
		this.add(basicTable);
		
		this.repaint();
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
}
