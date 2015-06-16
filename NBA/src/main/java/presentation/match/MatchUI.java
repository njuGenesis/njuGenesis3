package presentation.match;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import presentation.component.BgPanel;
import presentation.component.DatePanel;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;
import bussinesslogic.match.MatchLogic;
import data.po.matchData.MatchDataSeason;

public class MatchUI extends BgPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String bgStr = "";

	private GLabel title, borderUp, borderDown;
	GLabel date;
	DatePanel dateChooser;

	MatchLogic logic = new MatchLogic();
	MatchFactory factory = new MatchFactory();
	JScrollPane matchPane;

	public MatchDetailPanel detail;

	@Override
	public void refreshUI() {
		if(detail!=null){
			detail.refreshUI();
		}else{
			this.remove(dateChooser);
			this.remove(title);
			this.remove(date);
			this.remove(matchPane);

			init();
		}

	}

	public MatchUI(String s) {
		super(bgStr);


		this.setBounds(0, 0, 940, 600);
		this.setLayout(null);
		this.setOpaque(true);
		this.setBackground(UIUtil.bgWhite);

		init();

	}

	private void init(){
		dateChooser = new DatePanel(new Point(780,15),this);
		//		dateChooser.addActionListener(new DateListener());
		dateChooser.addDocuListener(new DateListener());

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


		date = new GLabel(getToday(),new Point(0,60),new Point(940,35),this,true,0,16);
		date.setOpaque(true);
		date.setBackground(UIUtil.bgWhite);
		date.setForeground(UIUtil.nbaBlue);
		date.setHorizontalAlignment(JLabel.CENTER);

		getMatchJSP();

		this.repaint();
	}

	public void getMatchJSP(){
		String day = checkDay(dateChooser.getText());
		String d = day.split("-")[0]+ "-" +day.split("-")[1] + "-" + day.split("-")[2];  


		ArrayList<MatchDataSeason> matches = logic.GetDateMatch(d);
		//		ArrayList<MatchDataPO> matches = logic.GetDateMatch(getSeason(day)+"_"+d, getSeason(day)+"_"+d);


		JScrollPane jsp = factory.getMatchPane(this,matches);
		matchPane = jsp;
		this.add(matchPane);
		this.repaint();

	}

	private String checkDay(String str){
		String[] d = str.split("-");
		if(d[1].startsWith("0")){
			return d[0]+"-"+d[1].charAt(1)+"-"+d[2];
		}else{
			return str;
		}
	}


	private String getToday(){
		Date dateNow = new Date();  
		SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy年M月d日");  
		String dateNowStr = dateFormat.format(dateNow);  
		return dateNowStr;
	}

	private void updateDate(String d){
		String[] str = d.split("-");
		date.setText(str[0]+"年"+str[1]+"月"+str[2]+"日");
	}


	public String getSeason(String date){
		String season = "13-14";

		int year = 2010;
		while(true){
			String d1 = String.valueOf(year)+"-10-01";
			String d2 = String.valueOf(year+1)+"-06-30";
			if(date.compareTo(d1)>=0 && date.compareTo(d2)<=0){
				season = String.valueOf(year).substring(2, 4)+"-"+String.valueOf(year+1).substring(2, 4);
				break;
			}else{
				year++;
			}
		}
		return season;
	}


	class DateListener implements ActionListener,DocumentListener{

		public void actionPerformed(ActionEvent e) {
			if(matchPane!=null){
				MatchUI.this.remove(matchPane);
				System.out.println("remove");
			}
			getMatchJSP();
			System.out.println(dateChooser.getText());
			updateDate(dateChooser.getText());

			MatchUI.this.repaint();
		}

		public void insertUpdate(DocumentEvent e) {
			//			Document doc = e.getDocument();  
			//			try {
			//				String s = doc.getText(0, doc.getLength());
			//			} catch (BadLocationException e1) {
			//				// TODO Auto-generated catch block
			//				e1.printStackTrace();
			//			} //返回文本框输入的内容 

			if(matchPane!=null){
				MatchUI.this.remove(matchPane);
			}
			getMatchJSP();
			System.out.println(dateChooser.getText());
			updateDate(dateChooser.getText());

			MatchUI.this.repaint();
		}

		public void removeUpdate(DocumentEvent e) {}

		public void changedUpdate(DocumentEvent e) {}

	}

}
