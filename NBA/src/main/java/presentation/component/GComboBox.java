package presentation.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

@SuppressWarnings("rawtypes")
public class GComboBox extends JComboBox{

	private static final long serialVersionUID = 1L;
	
	private Font font = new Font("微软雅黑",0,12);

	public GComboBox(){
		super();
		init();
	}
	@SuppressWarnings("unchecked")
	public GComboBox(ComboBoxModel model){
		super(model);
		init();
	}
	@SuppressWarnings("unchecked")
	public GComboBox(Object[] items){
		super(items);
		init();
	}
	@SuppressWarnings("unchecked")
	public GComboBox(Vector<?> items){
		super(items);
		init();
	}
	private void init(){
		setOpaque(false);
		setUI(new GComboBoxUI());
		setRenderer(new GComboBoxRenderer());
		setBackground(new Color(250,250,250));

		setFont(font);

	}
	public Dimension getPreferredSize(){
		return super.getPreferredSize();
	}

}
