package data.po;

import java.util.ArrayList;

public class RegreResult {
	double[] a;
	double[] v;
	double []dt;
	ArrayList<PersonR> value;
	public double[][] x;
	public  double[] y;
	public double[] getA() {
		return a;
	}
	public void setA(double[] a) {
		this.a = a;
	}
	public double[] getV() {
		return v;
	}
	public void setV(double[] v) {
		this.v = v;
	}
	public double[] getDt() {
		return dt;
	}
	public void setDt(double[] dt) {
		this.dt = dt;
	}
	public ArrayList<PersonR> getValue() {
		return value;
	}
	public void setValue(ArrayList<PersonR> value) {
		this.value = value;
	}
	public double[][] getX() {
		return x;
	}
	public void setX(double[][] x) {
		this.x = x;
	}
	public double[] getY() {
		return y;
	}
	public void setY(double[] y) {
		this.y = y;
	}
	
	

}
