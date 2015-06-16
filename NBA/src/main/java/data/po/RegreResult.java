package data.po;

import java.math.BigDecimal;
import java.util.ArrayList;

public class RegreResult {
	double[] a;  // 方程的系数，  a[0]是value中第一个属性的参数，  最后一个是常数项
	double[] v;//自变量属性的偏相关系数
	double []dt;// dt[0]随即误差q dt[1] 平均标准偏差s  dt[2]返回复相关系数r  dt[3]返回回归偏差平方和u ,dt[4]是总偏差平方和
	ArrayList<PersonR> value;//每个PersonR存有该项属性的type，personR值，球员的id
	public double[][] x;    //    x[0][0-n] 即第一个自变量x的所有取值
	public  double[] y;//  y[0-n]存有散点图的y值
	public int m;  //m值为自变量的个数
	public int n;//n值为数据的组数
	public double F;
	public String equation;
	
	
	
	public String getEquation() {
		return equation;
	}
	public void setEquation(String equation) {
		this.equation = equation;
	}
	public double getF() {
		return F;
	}
	public void setF(double f) {
		F = f;
	}
	public int getM() {
		return m;
	}
	public void setM(int m) {
		this.m = m;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	public double[] getA() {
		return a;
	}
	public void setA(double[] a) {
		for(int i=0;i<a.length;i++){
			BigDecimal bg = new BigDecimal(a[i]);
			a[i] = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		this.a = a;
	}
	public double[] getV() {
		return v;
	}
	public void setV(double[] v) {
		for(int i=0;i<v.length;i++){
			BigDecimal bg = new BigDecimal(v[i]);
			v[i] = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		this.v = v;
	}
	public double[] getDt() {
		return dt;
	}
	public void setDt(double[] dt) {
		this.dt = dt;
	}
	public ArrayList<PersonR> getValue() {
		for(int i=0;i<dt.length;i++){
			BigDecimal bg = new BigDecimal(dt[i]);
			dt[i] = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
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
