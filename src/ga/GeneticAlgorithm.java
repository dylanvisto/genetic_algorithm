// Dylan Visto
// CSCI 426 - Intro to AI
// 3-17-18
// Description: This class is contains methods that create an initial population of random chromosomes and runs generations of chromosomes.
//              There are also methods used to calculate the fitness of all of chromosomes in an array.
//              Two other methods are also used to find the best fitness and the average fitness out of an array of chromosomes

package ga;
import java.util.Random;

import utils.Fitness;

public class GeneticAlgorithm {

	protected int mPopulationSize;
	protected int mTournamentsSize;
	protected double mCrossoverProb;
	protected double mMutationProb;
	
	//These arrays are used for storing the various chromosomes and their fitnesses
	String[] chromosomesX;
	String[] chromosomesY;
	String[] newChromosomesX;
	String[] newChromosomesY;
	Double[] fitness;
	String[] tournamentArray;
	Double[] tournamentFitness;
	

	public GeneticAlgorithm(int populationSize,
			int tournamentsSize, double crossoverProb, double mutationProb) {
		mPopulationSize = populationSize;
		mTournamentsSize = tournamentsSize;
		mCrossoverProb = crossoverProb;
		mMutationProb = mutationProb;
		
		//Initialize arrays to be able to hold the specified population size of chromosomes
		chromosomesX = new String[mPopulationSize];
		chromosomesY = new String[mPopulationSize];
		newChromosomesX = new String[mPopulationSize];
		newChromosomesY = new String[mPopulationSize];
		
		//Array to hold the fitness values
		fitness = new Double[mPopulationSize];
		
		//These arrays are used for the tournament selection process
		tournamentArray = new String[tournamentsSize];
		tournamentFitness = new Double[tournamentsSize];
		
		createInitialPopulation();
	}

	public void createInitialPopulation() {
		//for loop to generate initial population
		for(int x = 0; x < mPopulationSize; x++) {
			
			//String variables used to store the chromosomes and its bits
			String cStringValue = "";
			String chromosomeStringX = "";
			String chromosomeStringY = "";
			
			//Used for storing either a 0 or 1 for use in the creation of a chromosome
			int cValue = 0;
			
			//Math.random generates a number from 0 to 1
			for(int y = 0; y < 8; y++) {
				
				//I then round that number to either 0 or 1 depending on value
				cValue = Math.toIntExact(Math.round(Math.random()));
				cStringValue = Integer.toString(cValue);
				
				//concatenate the individual 0s and 1s for the X protion of the string
				chromosomeStringX = chromosomeStringX.concat(cStringValue);
			}
			for(int z = 0; z < 8; z++) {
				
				//I then round that number to either 0 or 1 depending on value
				cValue = Math.toIntExact(Math.round(Math.random()));
				cStringValue = Integer.toString(cValue);
				
				//concatenate the individual 0s and 1s for the Y portion of the string
				chromosomeStringY = chromosomeStringY.concat(cStringValue);
			}
			
			//assign the string to the chromosome arrays
			chromosomesX[x] = chromosomeStringX;
			chromosomesY[x] = chromosomeStringY;
		}
		
		//calculate the fitness
		calculateFitness();
		
	}

	public void runOneGeneration() {
		
		//string declaration and initialization
		String chromosomeXParent = "";
		String chromosomeYParent = "";
		String chromosomeChild1 = "";
		String chromosomeChild2 = "";
		
		//Double variable used for probability
		Double randD = 0.0;
		
		//Object of fitness class to use calculateFitness function
		Fitness f = new Fitness();
		
		//Used for storing the two fittest chromosomes in each tournament
		String fittestChromosome1 = "";
		String fittestChromosome2 = "";
		
		//Used to prevent duplicate chromosomes from being used in the same tournament
		int[] savedRand = new int[mTournamentsSize];
		
		//Variable to determine whether or not the chromosome is valid for use in the tournament
		int go = 1;
		
		//Initialize savedRand array to be all -1
		for(int i = 0; i < mTournamentsSize; i++) {
			savedRand[i] = -1;
		}
		
		//This loop will create the children chromosomes until their number is equal to the size of the old population
		for(int j = 0; j < mPopulationSize; j++) {
		
			//Takes size mTournamentsSize of chromosomes and determines each fitness
			for(int a = 0; a < mTournamentsSize; a++) {
				
				//random number between 0 and mPopulationSize value
				int rand = new Random().nextInt(mPopulationSize);
				
				//go initially set to 1
				go = 1;
				
				//This loop checks to make sure that each chromosome used in the tournament is unique
				for(int z = 0; z < mTournamentsSize; z++) {
					
					//If there is a duplicate; decrement a and restart the current cycle
					if(rand == savedRand[z]) {
						a--;
						go = 0;
					}
				}
				//If each chromosome is unique then find each tournament chromosome's fitness and choose the one with the best fitness
				if(go == 1) {
					
					//store the rand value for later uniqueness checking
					savedRand[a] = rand;
					
					//select the tournament chromosome from the entire population
					chromosomeXParent = chromosomesX[rand]; 
					chromosomeYParent = chromosomesY[rand];
					
					//calculate the fitness of the tournament chromosome
					tournamentFitness[a] = f.calculateFitness((Integer.parseInt(chromosomeXParent, 2)) * .0235294 - 3, (Integer.parseInt(chromosomeYParent, 2)) * .0235294 - 3);
					
					//store that chromosome in the tournament array
					tournamentArray[a] = chromosomeXParent + chromosomeYParent;	
					
					System.out.println("Chromosome : " + tournamentArray[a] + " has fitness : " + tournamentFitness[a]);
				}
				
			}
			
			//variable used to find the max fitness of the tournament chromosomes
			double maxFitness = -3.0;
			
			//Grabs first chromosome with highest fitness from the tournament which will be used for mating
			for (int i = 0; i < tournamentFitness.length; i++){
			     if (tournamentFitness[i] > maxFitness)
			     {
			      maxFitness = tournamentFitness[i];
			      fittestChromosome1 = tournamentArray[i];
			     }
			}
			
			//Fittest chromosome is now determined
			System.out.println("Fittest chromosome1 is : " + fittestChromosome1 + " with fitness : " + maxFitness);
			
			//Initialize savedRand array to be all -1
			for(int i = 0; i < mTournamentsSize; i++) {
				savedRand[i] = -1;
			}
			
			
			//!!----This is a repeat of the above code to find the second fittest chromosome from a group of selected chromosomes----!!
			for(int a = 0; a < mTournamentsSize; a++) {
				int rand = new Random().nextInt(mPopulationSize);
				go = 1;
				for(int z = 0; z < mTournamentsSize; z++) {
					if(rand == savedRand[z]) {
						a--;
						go = 0;
					}
				}
				if(go == 1) {
					System.out.println("Rand is this: " + rand);
					savedRand[a] = rand;
					chromosomeXParent = chromosomesX[rand]; 
					chromosomeYParent = chromosomesY[rand];
					tournamentFitness[a] = f.calculateFitness((Integer.parseInt(chromosomeXParent, 2)) * .0235294 - 3, (Integer.parseInt(chromosomeYParent, 2)) * .0235294 - 3);
					tournamentArray[a] = chromosomeXParent + chromosomeYParent;	
					System.out.println("Chromosome : " + tournamentArray[a] + " has fitness : " + tournamentFitness[a]);
				}
			}
			
			maxFitness = -3.0;
			
			//Grabs second chromosome with highest fitness from the tournament which will be used for mating
			for (int i = 0; i < tournamentFitness.length; i++){
			     if (tournamentFitness[i] > maxFitness)
			     {
			      maxFitness = tournamentFitness[i];
			      fittestChromosome2 = tournamentArray[i];
			     }
			}
			
			System.out.println("Fittest chromosome2 is : " + fittestChromosome2 + " with fitness : " + maxFitness);
			
			//Using crossover probability, exchange parts of the pair of chromosomes
			//Create two offspring
			randD = new Random().nextDouble();
			
			//Since randD chooses between 0.0 and 1.0, you can use probability to enter the if statement
			if(randD < mCrossoverProb) {
				
				//This rand determines where to split the strings for crossover
				int rand = new Random().nextInt(16);
				chromosomeChild1 = fittestChromosome1.substring(0, rand) + fittestChromosome2.substring(rand);
				chromosomeChild2 = fittestChromosome2.substring(0, rand) + fittestChromosome1.substring(rand);
			}
			else {
				
				//No crossover occurs
				chromosomeChild1 = fittestChromosome1;
				chromosomeChild2 = fittestChromosome2;
			}
			
			//Randomly changes the gene values based on the mutation probability for child 1
			StringBuilder cChild1 = new StringBuilder(chromosomeChild1);
			
			//Loop through each bit and determine if it should be mutated based on mutation probability
			for(int x = 0; x < 16; x++) {
				randD = new Random().nextDouble();
				
				//Since randD chooses between 0.0 and 1.0, you can use probability to enter the if statement
				if(randD < mMutationProb) {
					
					//if the character is 0 then switch it to 1
					if(chromosomeChild1.charAt(x) == '0') {
						cChild1.setCharAt(x, '1');
					}
					
					//else switch it to 0
					else {
						cChild1.setCharAt(x, '0');
					}
				}
			}
			
			//grab the string from the stringbuilder
			chromosomeChild1 = cChild1.toString();
			
			//Randomly changes the gene values based on the mutation probability for child 2
			StringBuilder cChild2 = new StringBuilder(chromosomeChild2);
			
			//Loop through each bit and determine if it should be mutated based on mutation probability
			for(int y = 0; y < 16; y++) {
				randD = new Random().nextDouble();
				
				//Since randD chooses between 0.0 and 1.0, you can use probability to enter the if statement
				if(randD < mMutationProb) {
					
					//if the character is 0 then switch it to 1
					if(chromosomeChild2.charAt(y) == '0') {
						cChild2.setCharAt(y, '1');
					}
					
					//else switch it to 0
					else {
						cChild2.setCharAt(y, '0');
					}
				}
			}
			
			//grab the string from the stringbuilder
			chromosomeChild2 = cChild2.toString();
			
			//Split up entire chromosome into X and Y portions
			newChromosomesX[j] = chromosomeChild1.substring(0, 8);
			newChromosomesY[j] = chromosomeChild1.substring(8);
			newChromosomesX[j+1] = chromosomeChild2.substring(0, 8);
			newChromosomesY[j+1] = chromosomeChild2.substring(8);
			System.out.println("This is the old Chromosome (Parent 1) " + (j+1) + " : " + fittestChromosome1);
			System.out.println("This is the old Chromosome (Parent 2) " + (j+1) + " : " + fittestChromosome2);
			System.out.println("This is the new Chromosome (Child  1) " + (j+1) + " : " + newChromosomesX[j] + " " + newChromosomesY[j]);
			System.out.println("This is the new Chromosome (Child  2) " + (j+1) + " : " + newChromosomesX[j+1] + " " + newChromosomesY[j+1]);
			System.out.println();
			//need to increment again since we are creating two children each iteration
			j++;
		}
		
		//replace the old population with the new population
		chromosomesX = newChromosomesX;
		chromosomesY = newChromosomesY;
		for(int j = 0; j < mPopulationSize; j++) {
			System.out.println("Chromosome " + (j+1) + "is : " + chromosomesX[j] + chromosomesY[j]);
		}
		
		//calculate the fitness
		calculateFitness();
	}

	//This method adds up all the numbers in the fitness array and divides by the population size to determine the average
	public double getAverageFitness() {
		double averageFitness = 0;
		for(int j = 0; j < mPopulationSize; j++) {
			averageFitness += fitness[j];
		}
		averageFitness = averageFitness / mPopulationSize;
		return averageFitness;
	}
	
	//This method will find the best fitness from -3 to 3
	public double getBestFitness() {
		double maxFitness = -3.0;
		for (int i = 0; i < fitness.length; i++){
		     if (fitness[i] > maxFitness)
		     {
		      maxFitness = fitness[i];
		     }
		}
		return maxFitness;
	}
	
	//This method calculates the fitness values for the entire population-
	public void calculateFitness() {
		Double[] convertChromosomesX = new Double[mPopulationSize];
		Double[] convertChromosomesY = new Double[mPopulationSize];
		Fitness f = new Fitness();
		
		//Converting chromosomes from binary to integer and setting the integers between -3 and 3
		for(int w = 0; w < mPopulationSize; w++) {
			convertChromosomesX[w] = (Integer.parseInt(chromosomesX[w], 2)) * .0235294 - 3;
			convertChromosomesY[w] = (Integer.parseInt(chromosomesY[w], 2)) * .0235294 - 3;
		}
		
		//Calculating fitness
		for(int a = 0; a < mPopulationSize; a++) {
			fitness[a] = f.calculateFitness(convertChromosomesX[a], convertChromosomesY[a]);
			System.out.println("Chromosome " + (a+1) + " Fitness : " + fitness[a]);
		}
	}
	

}
