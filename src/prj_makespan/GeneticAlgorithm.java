
package prj_makespan;

public class GeneticAlgorithm {
    public static final int NUM_OF_POPULATION = 110;
    public static final int NUM_OF_GENARATION =1000;
    public static final int NUM_OF_ELITE_CHOMOSOMES = 10;
    public static final int TOURNAMET_SELECTION_SIZE = 2;
    public static final double MUTATION_RATE = 0.3;
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
            if (x<0){
                mutatePopulation.getChromosomes()[x] = local_search(population.getChromosomes()[x], data);
            }else{
                mutatePopulation.getChromosomes()[x] = population.getChromosomes()[x];
            }
            
        }
        for(int x = NUM_OF_ELITE_CHOMOSOMES;x<population.getChromosomes().length;++x)
        {
                if (Math.random()<MUTATION_RATE){
                    mutatePopulation.getChromosomes()[x]=mutateChromosome(population.getChromosomes()[x],data);
                }else{
                    mutatePopulation.getChromosomes()[x] = population.getChromosomes()[x];
                }
                

            
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
    
    //local search method
    private Chromosome local_search(Chromosome individual, Data data){
        Chromosome best = new Chromosome(data.numOfTask);
        Chromosome candidate = new Chromosome(data.numOfTask);
        //copy individual to best
        best.setGenes(individual.getGenes().clone());
        best.setFitness(individual.getFitness(data));

        
        //run for every task
        for (int i=0;i<data.numOfTask;i++){
            //pass if less than 1 avalible man
            if (data.manAbleDo.get(i).size()<=1) continue;
            //run for every able man
            for (int j=0;j<data.manAbleDo.get(i).size();j++){
                
                int change_value = data.manAbleDo.get(i).get(j);
                int curr_value = individual.getGenes()[i];
                //pass if the current value
                if (curr_value==change_value) continue;
                
                
                //copy to candidate
                
                candidate.setGenes(individual.getGenes().clone());
                //change value
                
                candidate.getGenes()[i] = change_value;
               
//                double candidate_fitness = 
//                        data.w1*(data.U[change_value][individual.beginTask[i]]-data.U[curr_value][individual.beginTask[i]])+
//                        data.w2*(data.salaryEachTime[change_value]-data.salaryEachTime[curr_value])/data.salary_change+
//                        data.w3*(data.Exper[i][curr_value]-data.Exper[i][change_value])/data.exp_change[i]
//                        ;
                //check if bestter
                if (candidate.getFitness(data)<best.getFitness(data)){
                    
                    best.setGenes(candidate.getGenes().clone());
                    candidate.isFitnessChanged = false;
                    best.setFitness(candidate.getFitness(data));
                    best.isFitnessChanged = false;
                }

//                  if (candidate_fitness<0){
//                      best.setGenes(candidate.getGenes().clone());
//                  }
            }
        }

        return best;
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
