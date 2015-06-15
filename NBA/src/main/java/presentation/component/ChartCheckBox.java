package presentation.component;

import javax.swing.JCheckBox;

public class ChartCheckBox extends JCheckBox{
	private static final long serialVersionUID = 1L;
	private int number;

	public ChartCheckBox(int number){
		super();

		this.number = number;
	}
	public int getNumber(){
		return number;
	}

}
