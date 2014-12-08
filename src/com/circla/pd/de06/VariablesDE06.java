package com.circla.pd.de06;

import java.util.Arrays;

import com.circla.pd.DEFunctions;

public class VariablesDE06 {
	private DEFunctions defunc = new DEFunctions();
	
	private double maxDesErr;
	
	private double desErrCurrentTotal;
	
	private double desErrCurrent1;
	
	private double desErrCurrent2;
	
	private double desErrCurrent3;
	
	private double totalCurrent;
	
	private double current1;
	
	private double current2;
	
	private double current3;
	
	private Double r1;
	
	private Double r2;
	
	private Double r3;
	
	private Double r11;
	
	private Double r12;
	
	private Double r13;
	
	private Double r21;
	
	private Double r22;
	
	private Double r23;
	
	private Double r31;
	
	private Double r32;

	private Double r33;
	
	public VariablesDE06() {
		// TODO Auto-generated constructor stub
	}

	public VariablesDE06(double desErrTotalCurrent, double desErrCurrent1,
			double desErrCurrent2, double desErrCurrent3,
			double compTotalCurrent, double compCurrent1, double compCurrent2,
			double compCurrent3, Double r1, Double r2, Double r3) {
		this.desErrCurrentTotal = desErrTotalCurrent;
		this.desErrCurrent1 = desErrCurrent1;
		this.desErrCurrent2 = desErrCurrent2;
		this.desErrCurrent3 = desErrCurrent3;
		this.totalCurrent = compTotalCurrent;
		this.current1 = compCurrent1;
		this.current2 = compCurrent2;
		this.current3 = compCurrent3;
		this.r1 = r1;
		this.r2 = r2;
		this.r3 = r3;
		computeMaxDesErr();
	}

	public VariablesDE06(double desErrTotalCurrent, double desErrCurrent1,
			double desErrCurrent2, double desErrCurrent3,
			double compTotalCurrent, double compCurrent1, double compCurrent2,
			double compCurrent3, double r11, double r12,
			double r21, double r22, double r31,
			double r32) {
		this.desErrCurrentTotal = desErrTotalCurrent;
		this.desErrCurrent1 = desErrCurrent1;
		this.desErrCurrent2 = desErrCurrent2;
		this.desErrCurrent3 = desErrCurrent3;
		this.totalCurrent = compTotalCurrent;
		this.current1 = compCurrent1;
		this.current2 = compCurrent2;
		this.current3 = compCurrent3;
		this.r11 = r11;
		this.r12 = r12;
		this.r21 = r21;
		this.r22 = r22;
		this.r31 = r31;
		this.r32 = r32;
		computeMaxDesErr();
	}

	private void computeMaxDesErr() {
		double desErrArray[] = {desErrCurrentTotal, desErrCurrent1, desErrCurrent2, desErrCurrent3};
		
		Arrays.sort(desErrArray);
		this.maxDesErr = desErrArray[3];
	}

	public double getMaxDesErr() {
		return maxDesErr;
	}

	public double getDesErrCurrentTotal() {
		return desErrCurrentTotal;
	}

	public double getDesErrCurrent1() {
		return desErrCurrent1;
	}

	public double getDesErrCurrent2() {
		return desErrCurrent2;
	}

	public double getDesErrCurrent3() {
		return desErrCurrent3;
	}

	public double getTotalCurrent() {
		return totalCurrent;
	}

	public double getCurrent1() {
		return current1;
	}

	public double getCurrent2() {
		return current2;
	}

	public double getCurrent3() {
		return current3;
	}

	public Double getR1() {
		return r1;
	}

	public Double getR2() {
		return r2;
	}

	public Double getR3() {
		return r3;
	}

	public Double getR11() {
		return r11;
	}

	public Double getR12() {
		return r12;
	}

	public Double getR13() {
		return r13;
	}

	public Double getR21() {
		return r21;
	}

	public Double getR22() {
		return r22;
	}

	public Double getR23() {
		return r23;
	}

	public Double getR31() {
		return r31;
	}

	public Double getR32() {
		return r32;
	}

	public Double getR33() {
		return r33;
	}

	public String toString() {
		System.out.println("Highest DesErr:\t" + defunc.to3SigFig(maxDesErr) + " %");
		System.out.println("--Resistor Comm. Values--");
		if (r1 != null && r2 != null && r3 != null) {
			System.out.println("   R1:\t" + r1 + " " + defunc.ohmSign());
			System.out.println("   R2:\t" + r2 + " " + defunc.ohmSign());
			System.out.println("   R3:\t" + r3 + " " + defunc.ohmSign());
		}
		else {
			if (r12 != null && r22 != null && r32 != null) {
				System.out.println("   R1:\t" + r11 + " " + defunc.ohmSign() + " + " + r12 + " " + defunc.ohmSign());
				System.out.println("   R2:\t" + r21 + " " + defunc.ohmSign() + " + " + r22 + " " + defunc.ohmSign());
				System.out.println("   R3:\t" + r31 + " " + defunc.ohmSign() + " + " + r32 + " " + defunc.ohmSign());
			}
			else if (r13 != null && r23 != null && r33 != null) {
				System.out.println("   R1:\t" + r11 + " " + defunc.ohmSign() + " + " + r12 + " " + defunc.ohmSign() + " + " + r13 + " " + defunc.ohmSign());
				System.out.println("   R2:\t" + r21 + " " + defunc.ohmSign() + " + " + r22 + " " + defunc.ohmSign() + " + " + r23 + " " + defunc.ohmSign());
				System.out.println("   R3:\t" + r31 + " " + defunc.ohmSign() + " + " + r32 + " " + defunc.ohmSign() + " + " + r33 + " " + defunc.ohmSign());
			}
		}
		System.out.println("--Computed Currents--");
		System.out.println("   iT:\t" + totalCurrent + " mA");
		System.out.println("   i1:\t" + current1 + " mA");
		System.out.println("   i2:\t" + current2 + " mA");
		System.out.println("   i3:\t" + current3 + " mA");
		System.out.println("--Design Errors--");
		System.out.println("   iT (DesErr):\t" + defunc.to3SigFig(desErrCurrentTotal) + " %");
		System.out.println("   i1 (DesErr):\t" + defunc.to3SigFig(desErrCurrent1) + " %");
		System.out.println("   i2 (DesErr):\t" + defunc.to3SigFig(desErrCurrent2) + " %");
		System.out.println("   i3 (DesErr):\t" + defunc.to3SigFig(desErrCurrent3) + " %");
		
		return null;
	}

}
