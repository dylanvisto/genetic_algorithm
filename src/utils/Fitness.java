package utils;

public class Fitness {

	private double fitness;

	public double calculateFitness(double x, double y) {
		
		fitness = Math.exp(-Math.pow(y+1.0,2.0)-Math.pow(x,2))*Math.pow(x-1.0,2.0)+Math.exp(-Math.pow(x,2)-Math.pow(y,2))*(-x+Math.pow(x,3)+Math.pow(y,5)); 

		return fitness;
	}
}
