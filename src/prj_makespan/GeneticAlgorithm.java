
package prj_makespan;

public class GeneticAlgorithm {
    public static final int NUM_OF_POPULATION = 110;

    public static final int NUM_OF_GENARATION = 500;
    public static final int NUM_OF_ELITE_CHOMOSOMES = 10;
    public static final int TOURNAMET_SELECTION_SIZE = 10;
    public static final double MUTATION_RATE = 0.1;

    public Population evolve(Population population,Data data)
    {
        return mutatePopulation(crossoverrPopulation(population,data),data);
    }
    private Population crossoverrPopulation(Population population,Data data)
    {
        Population crossoverPopulation = new Population(population.getChromosomes().length);
        for(int x=0;x<NUM_OF_ELITE_CHOMOSOMES;++x)
        {


            crossoverPopulation.getChromosomes()[x] = population.getChromosomes()[x];
        }
        for(int x=NUM_OF_ELITE_CHOMOSOMES;x<population.getChromosomes().length;++x)
        {
            Chromosome chromosome1 = selectTournamentPopualtion(population,data).getChromosomes()[0];
            Chromosome chromosome2 = selectTournamentPopualtion(population,data).getChromosomes()[0];
            crossoverPopulation.getChromosomes()[x] = crossoverChromosome(chromosome1, chromosome2, data);
        }
        return crossoverPopulation;
    }
    private Population mutatePopulation(Population population,Data data)
    {
        Population mutatePopulation = new Population(population.getChromosomes().length);
        for(int x =0; x< NUM_OF_ELITE_CHOMOSOMES;++x)
        {

            mutatePopulation.getChromosomes()[x] = population.getChromosomes()[x];
        }
        for(int x = NUM_OF_ELITE_CHOMOSOMES;x<population.getChromosomes().length;++x)
        {

                mutatePopulation.getChromosomes()[x]=mutateChromosome(population.getChromosomes()[x],data);


            
        }
        return mutatePopulation;
    }
    
    private Chromosome crossoverChromosome(Chromosome chromosome1 , Chromosome chromosome2,Data data)
    {
        Chromosome crossChromosome = new Chromosome(data.numOfTask);
        for(int x=0;x<chromosome1.getGenes().length;++x)
        {
            if(Math.random()<0.5) crossChromosome.getGenes()[x]=chromosome1.getGenes()[x];
            else crossChromosome.getGenes()[x]=chromosome2.getGenes()[x];
        }
        return crossChromosome;
    }
    
    private Chromosome mutateChromosome(Chromosome chromosome,Data data)
    {
        Chromosome mutateChromosome = new Chromosome(data.numOfTask);
        for(int x=0;x<chromosome.getGenes().length;++x)
        {
            if(Math.random()<MUTATION_RATE)
            {
                int z = data.manAbleDo.get(x).size();
                int c=  (int)(Math.random()*z);
                mutateChromosome.getGenes()[x]=data.manAbleDo.get(x).get(c);
            }
            else mutateChromosome.getGenes()[x] = chromosome.getGenes()[x];
        }
        return mutateChromosome;
    }


    private Population selectTournamentPopualtion(Population population,Data data)
    {
        Population tournamentPopulation = new Population(TOURNAMET_SELECTION_SIZE);
        for(int x=0;x<TOURNAMET_SELECTION_SIZE;++x)
        {
            int c = (int)(Math.random()*population.getChromosomes().length);
            tournamentPopulation.getChromosomes()[x] = population.getChromosomes()[c];
        }
        tournamentPopulation.sortChromosomesByFitness(data);
        return tournamentPopulation;
    }
}
