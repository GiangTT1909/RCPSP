
package prj_makespan;

import java.util.Arrays;
public class Population {
    private Chromosome[] chromosomes = new Chromosome[GeneticAlgorithm.NUM_OF_POPULATION];

    public Population(int len) {
        chromosomes = new Chromosome[len];
    }

    public Population initializePopulation(Data data)
    {
        for(int i=0;i<GeneticAlgorithm.NUM_OF_POPULATION;++i)
        {
            chromosomes[i] = new Chromosome(data.numOfTask).initializeChromosome(data);
        }
        sortChromosomesByFitness(data);
        return this;
    }
    public Chromosome[] getChromosomes()
    {
        return chromosomes;
    }
    
    public void sortChromosomesByFitness(Data data)
    {
        Arrays.sort(chromosomes,(chromosome1 , chromosome2)->{
            int flag=0;
            if(chromosome1.getFitness(data) < chromosome2.getFitness(data)) flag = -1;
            if(chromosome1.getFitness(data) > chromosome2.getFitness(data)) flag = 1;
            return flag;
        });  
    }
}
