package presentation.match;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.TableUtility;
import presentation.contenui.UIUtil;
import presentation.hotspot.SelectLabel;
import bussinesslogic.match.MatchLogic;
import data.po.matchData.MatchDataSeason;

public class MatchDetailPanel extends BgPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String bg = "";
//	private MatchDataPO po;
	
	private MatchDataSeason po;

	private GLabel title, borderUp, borderDown;
	private SelectLabel info;  //基本信息
	private SelectLabel team1;  //技术统计：队伍一
	private SelectLabel team2;  //技术统计：队伍二
	private SelectLabel compare;  //球队对比
	private SelectLabel[] menuItem = new SelectLabel[4];


	private int labelWeight = 234;

	private JPanel detailPanel;

	private MatchFactory factory = new MatchFactory();
	
	private MatchLogic logic = new MatchLogic();


	@Override
	public void refreshUI() {
		this.remove(title);
		this.remove(info);
		this.remove(team1);
		this.remove(team2);
		this.remove(compare);
		this.remove(detailPanel);

		init();
	}


	public MatchDetailPanel(MatchDataSeason po) {
		super(bg);
		this.po = po;

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {}

		this.setSize(940, 600);
		this.setLocation(0, 0);
		this.setLayout(null);
		this.setOpaque(true);
		this.setBackground(UIUtil.bgWhite);

		init();
	}

	private void init(){
		//--------------------标题--------------------

		borderUp = new GLabel("", new Point(0,0), new Point(940,4), this, true);
		borderUp.setOpaque(true);
		borderUp.setBackground(UIUtil.nbaBlue);

		borderDown = new GLabel("", new Point(0,56), new Point(940,4), this, true);
		borderDown.setOpaque(true);
		borderDown.setBackground(UIUtil.nbaBlue);

		title = new GLabel("   比赛",new Point(0,4),new Point(940,52),this,true,0,24);
		title.setOpaque(true);
		title.setBackground(UIUtil.bgWhite);
		title.setForeground(UIUtil.nbaBlue);

		info = new SelectLabel("基本信息",new Point(0,60),new Point(labelWeight,35),this,true,0,16);
		info.setSelected(true);
		info.addMouseListener(new MenuListener());
		menuItem[0] = info;

		getDetailPanel(0);
		
		String team = TableUtility.checkNOH(po.getTeam()); // 主队
		String otherTeam = TableUtility.checkNOH(po.getOtherTeam()); // 客队

		team1 = new SelectLabel(TableUtility.getShortChTeam(team)+" 技术统计",new Point(0+labelWeight+1,60),new Point(labelWeight,35),this,true,0,16);
		team1.addMouseListener(new MenuListener());
		menuItem[1] = team1;

		team2 = new SelectLabel(TableUtility.getShortChTeam(otherTeam)+" 技术统计",new Point(0+2*labelWeight+2,60),new Point(labelWeight,35),this,true,0,16);
		team2.addMouseListener(new MenuListener());
		menuItem[2] = team2;

		compare = new SelectLabel("球队对比",new Point(0+3*labelWeight+3,60),new Point(labelWeight,35),this,true,0,16);
		compare.addMouseListener(new MenuListener());
		menuItem[3] = compare;

		this.repaint();
	}

	public void getDetailPanel(int num){
		switch(num){
		case 0:
			detailPanel = factory.getInfoPanel(po);break;
		case 1:
			detailPanel = factory.getTeamPanel(po.getTeamPlayer());break;
		case 2:
			detailPanel = factory.getTeamPanel(po.getOtherteamPlayer());
			System.out.println(po.getOtherteamPlayer().get(0).getPlayername());
			System.out.println(po.getOtherteamPlayer().get(1).getPlayername());
			break;

		case 3:
			detailPanel = factory.getComparePanel(po);break;
		}

		this.add(detailPanel);
		this.repaint();

	}



	class MenuListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			SelectLabel sl = (SelectLabel)e.getSource();
			int num = 0;

			for(int i=0;i<4;i++){
				if(sl == menuItem[i]){
					num = i;
					break;
				}
			}

			for(int i=0;i<menuItem.length;i++){
				menuItem[i].setSelected(false);
			}
			sl.setSelected(true);

			if(detailPanel!=null){
				MatchDetailPanel.this.remove(detailPanel);
			}

			MatchDetailPanel.this.getDetailPanel(num);
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
