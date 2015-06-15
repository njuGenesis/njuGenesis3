package bussinesslogic.team;

import java.util.ArrayList;

public class LineCoefficient {

	public static void main(String[] args) {
		double[] x = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		double[] y = { 23, 44, 32, 56, 33, 34, 55, 65, 45, 55 };

	}

	public static ArrayList<Double> res=new ArrayList<Double>();
	public static double a;
	public static double b;
	public static double r2;

	public static ArrayList<Double> estimate(double[] x, double[] y) {
		res.clear();
		a = 0;
		b = 0;
		r2 = 0;
		a = getXc(x, y);
		b = getC(x, y, a);
		r2 = getR2(x, y);
		res.add(a);
		res.add(b);
		res.add(r2);
		return res;
	}

	public static double getR2(double[] x, double[] y) {
		double up = 0;
		double down = 0;
		for (int i = 0; i < x.length; i++) {
			up = up + Math.pow(a * x[i] + b - getavg(y), 2);
			down = down + Math.pow(y[i] - getavg(y), 2);
			System.out.println(up+"   "+down);
		}
		return up / down;
	}

	public static double getavg(double[] x) {
		double sum = 0.0;
		for (int i = 0; i < x.length; i++) {
			sum = sum + x[i];
		}
		return sum / x.length;
	}

	/**
	 * 计算 x 的系数
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double getXc(double[] x, double[] y) {
		int n = x.length;
		return (n * pSum(x, y) - sum(x) * sum(y))
				/ (n * sqSum(x) - Math.pow(sum(x), 2));
	}

	/**
	 * 计算常量系数
	 * 
	 * @param x
	 * @param y
	 * @param a
	 * @return
	 */
	public static double getC(double[] x, double[] y, double a) {
		int n = x.length;
		return sum(y) / n - a * sum(x) / n;
	}

	/**
	 * 计算常量系数
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double getC(double[] x, double[] y) {
		int n = x.length;
		double a = getXc(x, y);
		return sum(y) / n - a * sum(x) / n;
	}

	private static double sum(double[] ds) {
		double s = 0;
		for (double d : ds)
			s = s + d;
		return s;
	}

	private static double sqSum(double[] ds) {
		double s = 0;
		for (double d : ds)
			s = s + Math.pow(d, 2);
		return s;
	}

	private static double pSum(double[] x, double[] y) {
		double s = 0;
		for (int i = 0; i < x.length; i++)
			s = s + x[i] * y[i];
		return s;
	}
}
