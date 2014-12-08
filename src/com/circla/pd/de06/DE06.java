package com.circla.pd.de06;

import java.math.BigDecimal;
import java.util.Scanner;

import com.apollo.misc.NamePrinter;
import com.circla.pd.DEFunctions;

public class DE06 {

	public static void main(String[] args) {
		DEFunctions defunc = new DEFunctions();
		NamePrinter np = new NamePrinter();
		double classNo;
		
		// voltage source
		final int VCC = 12;
		
		// target values
		double totalCurrent;	// in mA
		double r1Current, r2Current, r3Current;	// in mA
		// top three output
		VariablesDE06[] topThree = new VariablesDE06[3];
		// %maxDesErr, %error(iT), %error(i1), %error(i2), %error(i3), iT, i1, i2, i3, R1, R2, R3
		
		// other variables
		Scanner input = new Scanner(System.in);
		
		
		// main program -- START
		System.out.print("Please input your class number (e.g: 15.3):\t");
		classNo = input.nextDouble();
		// solve for the target values
		totalCurrent = 20 + classNo;
		r1Current = ((5+classNo)/100) * totalCurrent;
		r2Current = ((10+classNo)/100) * totalCurrent;
		r3Current = ((85-(2*classNo))/100) * totalCurrent;
		System.out.println("[Class No]: " + classNo);
		System.out.println("[Target Values]:");
		System.out.println("   Total Current: " + totalCurrent + " mA");
		System.out.println("   Current 1: " + r1Current + " mA");
		System.out.println("   Current 2: " + r2Current + " mA");
		System.out.println("   Current 3: " + r3Current + " mA");
		System.out.print("\nSelect Version\n"
				+ "[1] Direct Solution\n"
				+ "[2] Top 3 Solution\n"
				+ "[3] 2-Resistor Solution\n"
				+ "[4] 3-Resistor Solution\n"
				+ "Answer:\t");
		int version = input.nextInt();
		
		if (version == 1) {
			// computation -- START
			System.out.println("\nComputing...\n");
			double r1 = VCC/(r1Current/1000);
			double r2 = VCC/(r2Current/1000);
			double r3 = VCC/(r3Current/1000);
			
			double commR1 = defunc.getCommercialValueR(r1);
			double commR2 = defunc.getCommercialValueR(r2);
			double commR3 = defunc.getCommercialValueR(r3);
			
			double compTotalResistance = 1/((1/commR1)+(1/commR2)+(1/commR3));
			double compTotalCurrent = (VCC/compTotalResistance) * 1000;
			double compCurrent1 = (VCC/commR1) * 1000;
			double compCurrent2 = (VCC/commR2) * 1000;
			double compCurrent3 = (VCC/commR3) * 1000;
			
			double desErrTotalCurrent = defunc.computeDesErr(totalCurrent, compTotalCurrent);
			double desErrCurrent1 = defunc.computeDesErr(r1Current, compCurrent1);
			double desErrCurrent2 = defunc.computeDesErr(r2Current, compCurrent2);
			double desErrCurrent3 = defunc.computeDesErr(r3Current, compCurrent3);
			
			VariablesDE06 compVariables = new VariablesDE06(desErrTotalCurrent, desErrCurrent1, desErrCurrent2, desErrCurrent3, compTotalCurrent, compCurrent1, compCurrent2, compCurrent3, commR1, commR2, commR3);
			System.out.println("\n\n");
			np.print("RESULT");
			compVariables.toString();
		}
		else if (version == 2) {
			// computation -- START
			// get values to trial and error total resistance
			System.out.println("\nComputing...\n");
			for (BigDecimal r1 : defunc.getE24()) {
				for (BigDecimal r2 : defunc.getE24()) {
					for (BigDecimal r3 : defunc.getE24()) {
						// temporary variables for resistors
						double compTotalResistance;
						double compTotalCurrent;
						double desErrTotalCurrent;
						double compCurrent1, compCurrent2, compCurrent3;
						double desErrCurrent1, desErrCurrent2, desErrCurrent3;
						compTotalResistance = 1/((1/r1.doubleValue())+(1/r2.doubleValue())+(1/r3.doubleValue()));
						compTotalCurrent = (VCC/compTotalResistance) * 1000;
						compCurrent1 = (VCC/r1.doubleValue()) * 1000;
						compCurrent2 = (VCC/r2.doubleValue()) * 1000;
						compCurrent3 = (VCC/r3.doubleValue()) * 1000;
						desErrTotalCurrent = defunc.computeDesErr(totalCurrent, compTotalCurrent);
						desErrCurrent1 = defunc.computeDesErr(r1Current, compCurrent1);
						desErrCurrent2 = defunc.computeDesErr(r2Current, compCurrent2);
						desErrCurrent3 = defunc.computeDesErr(r3Current, compCurrent3);
						
						VariablesDE06 compVariables = new VariablesDE06(desErrTotalCurrent, desErrCurrent1, desErrCurrent2, desErrCurrent3, compTotalCurrent, compCurrent1, compCurrent2, compCurrent3, r1.doubleValue(), r2.doubleValue(), r3.doubleValue());
						
						// adding to topThree
						if (topThree[0] == null) {
							add(0, topThree, compVariables);
						}
						else if (topThree[1] == null) {
							add(1, topThree, compVariables);
						}
						else if (topThree[2] == null) {
							add(2, topThree, compVariables);
						} 
						else if (compVariables.getMaxDesErr() < topThree[0].getMaxDesErr()) {
							add(0, topThree, compVariables);
						}
						else if (compVariables.getMaxDesErr() < topThree[1].getMaxDesErr()) {
							add(1, topThree, compVariables);
						}
						else if (compVariables.getMaxDesErr() < topThree[2].getMaxDesErr()) {
							add(2, topThree, compVariables);
						}
					}
				}
			}
			
			System.out.println("\n");
			np.print("RESULT");
			for (int i = 0; i < topThree.length; i++) {
				topThree[i].toString();
			}
		}
		else if (version == 3) {
			// computation -- START
			// get possible 6 resistors
			System.out.println("\nComputing...\n");
			final double totalR1 = VCC/(r1Current/1000);
			final double totalR2 = VCC/(r2Current/1000);
			final double totalR3 = VCC/(r3Current/1000);
			
			double[][] threeCommR1 = defunc.getThreePossiblePairs(totalR1);
			double[][] threeCommR2 = defunc.getThreePossiblePairs(totalR2);
			double[][] threeCommR3 = defunc.getThreePossiblePairs(totalR3);
			
			// let the user choose pairs of R1, R2, R3
			System.out.println("Target R1: " + totalR1 + " " + defunc.ohmSign());
			System.out.println("Please select the resistor pair you want to use for R1:");
			for (int i = 0; i < threeCommR1.length; i++) {
				System.out.println("["+(i+1)+"] " + threeCommR1[i][0] + " " + defunc.ohmSign() + " and " + threeCommR1[i][1] + " " + defunc.ohmSign() + " | Tolerance to target: " + defunc.to3SigFig(defunc.computeDesErr(totalR1, threeCommR1[i][0] + threeCommR1[i][1])) + " %");
			}
			int chosenR1 = input.nextInt() - 1;
			
			System.out.println("Target R2: " + totalR2 + " " + defunc.ohmSign());
			System.out.println("Please select the resistor pair you want to use for R2:");
			for (int i = 0; i < threeCommR2.length; i++) {
				System.out.println("["+(i+1)+"] " + threeCommR2[i][0] + " " + defunc.ohmSign() + " and " + threeCommR2[i][1] + " " + defunc.ohmSign() + " | Tolerance to target: " + defunc.to3SigFig(defunc.computeDesErr(totalR2, threeCommR2[i][0] + threeCommR2[i][1])) + " %");
			}
			int chosenR2 = input.nextInt() - 1;
			
			System.out.println("Target R3: " + totalR3 + " " + defunc.ohmSign());
			System.out.println("Please select the resistor pair you want to use for R3:");
			for (int i = 0; i < threeCommR3.length; i++) {
				System.out.println("["+(i+1)+"] " + threeCommR3[i][0] + " " + defunc.ohmSign() + " and " + threeCommR3[i][1] + " " + defunc.ohmSign() + " | Tolerance to target: " + defunc.to3SigFig(defunc.computeDesErr(totalR3, threeCommR3[i][0] + threeCommR3[i][1])) + " %");
			}
			int chosenR3 = input.nextInt() - 1;

			// solve
			double r11 = threeCommR1[chosenR1][0];
			double r12 = threeCommR1[chosenR1][1];
			double rt1 = r11 + r12;
			double r21 = threeCommR2[chosenR2][0];
			double r22 = threeCommR2[chosenR2][1];
			double rt2 = r21 + r22;
			double r31 = threeCommR3[chosenR3][0];
			double r32 = threeCommR3[chosenR3][1];
			double rt3 = r31 + r32;

			double compTotalResistance = 1/((1/rt1)+(1/rt2)+(1/rt3));
			double compTotalCurrent = (VCC/compTotalResistance) * 1000;
			double compCurrent1 = (VCC/rt1) * 1000;
			double compCurrent2 = (VCC/rt2) * 1000;
			double compCurrent3 = (VCC/rt3) * 1000;

			double desErrTotalCurrent = defunc.computeDesErr(totalCurrent, compTotalCurrent);
			double desErrCurrent1 = defunc.computeDesErr(r1Current, compCurrent1);
			double desErrCurrent2 = defunc.computeDesErr(r2Current, compCurrent2);
			double desErrCurrent3 = defunc.computeDesErr(r3Current, compCurrent3);
			VariablesDE06 compVariables = new VariablesDE06(desErrTotalCurrent, desErrCurrent1, desErrCurrent2, desErrCurrent3, compTotalCurrent, compCurrent1, compCurrent2, compCurrent3, r11, r12, r21, r22, r31, r32);
			np.print("RESULT");
			compVariables.toString();
		}
	}

	private static void add(int rowNumber, VariablesDE06[] topThree,
			VariablesDE06 compVariables) {
		for (int i = topThree.length - 1; i > rowNumber; i--) {
			topThree[i] = topThree[i-1];
		}
		
		topThree[rowNumber] = compVariables;
	}
}
