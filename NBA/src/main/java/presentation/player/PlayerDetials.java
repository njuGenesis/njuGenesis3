package presentation.player;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import bussinesslogic.player.PlayerLogic_db;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;
import presentation.hotspot.SelectLabel;

public class PlayerDetials extends BgPanel{
	private static final long serialVersionUID = 1L;
	private GLabel title, borderUp, borderDown;
	private int id;
	private SelectLabel[] selectLabels;
	private String[] functions = {"基本信息", "赛季数据", "比赛数据", "联盟对比", "统计分析"};
	private JPanel[] panels;
 	
	public PlayerDetials(int id){
		super("");
		
		this.id = id;
		
		this.setBounds(0, 0, 940, 600);
		this.setLayout(null);
		this.setBackground(UIUtil.bgWhite);
		this.setVisible(true);
		
		init();
	}
	
	private void init(){
		title = new GLabel("  "+"name"//po.getName()
				, new Point(0, 4), new Point(940, 42), this, true, 0, 25);
		title.setOpaque(true);
		title.setBackground(UIUtil.bgWhite);
		title.setForeground(UIUtil.nbaBlue);
		
		borderUp = new GLabel("", new Point(0, 0), new Point(940, 4), this, true);
		borderUp.setOpaque(true);
		borderUp.setBackground(UIUtil.nbaBlue);
		
		borderDown = new GLabel("", new Point(0, 46), new Point(940, 4), this, true);
		borderDown.setOpaque(true);
		borderDown.setBackground(UIUtil.nbaBlue);
		
		int width = 940/functions.length;
		
		selectLabels = new SelectLabel[functions.length];
		for(int i=0;i<functions.length;i++){
			if(i<functions.length-1){
				selectLabels[i] = new SelectLabel(functions[i], new Point(i*width, 50), new Point(width, 50), this, true, 0, 15);
			}else{
				selectLabels[i] = new SelectLabel(functions[i], new Point(i*width, 50), new Point(940-i*width, 50), this, true, 0, 15);
			}
			selectLabels[i].setNumber(i);
			selectLabels[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					SelectLabel selectLabel = (SelectLabel)e.getSource();
					setFalse();
					selectLabel.setSelected(true);
					panels[selectLabel.getNumber()].setVisible(true);
				}
			});
		}
		
		panels = new JPanel[functions.length];

		PlayerInfo info = new PlayerInfo(id);
		PlayerData data = new PlayerData(id);
		PlayerMatch match = new PlayerMatch(id);
		PlayerCmp cmp = new PlayerCmp(id);
		PlayerCrosshairOverlay cross = new PlayerCrosshairOverlay(id);
		
		this.add(info);
		this.add(data);
		this.add(match);
		this.add(cmp);
		this.add(cross);
		
		panels[0] = info;
		panels[1] = data;
		panels[2] = match;
		panels[3] = cmp;
//		panels[2] = info;
//		panels[3] = info;
		panels[4] = cross;
		
		setFalse();
		panels[0].setVisible(true);
		selectLabels[0].setSelected(true);
	}
	
	private void setFalse(){
		for(int i=0;i<functions.length;i++){
			selectLabels[i].setSelected(false);
			panels[i].setVisible(false);
		}
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
			
		}
	}
}
