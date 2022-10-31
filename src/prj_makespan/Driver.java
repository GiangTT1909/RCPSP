/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prj_makespan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Driver {

    public int numOfPeople;
    public int numOfTask;
    public int numOfSkill;
    public int[] durationTime = new int[505];
    public int[][] adjacency = new int[505][505];
    public int[][] K = new int[505][505];
    public int[][] R = new int[505][505];
    public int[] salaryEachTime = new int[505];
    public double[][] Z = new double[505][505];
    public int Deadline;
    public int Budget;
    public double[][] U = new double[505][10005];
    public ArrayList< ArrayList< Integer>> manAbleDo = new ArrayList< ArrayList< Integer>>();
    public int[][] Exper = new int[505][505];
    public static void main(String[] args) throws IOException {
        ArrayList <Double > answer = new ArrayList < Double >();
        Driver driver = new Driver();
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = Files.newBufferedReader(Paths.get("data\\input.txt"))) {

            String line;
            String[] arrString = null;

            // Read number of Tasks.
            line = br.readLine();
            arrString = line.split(" ");
            driver.numOfTask = Integer.valueOf(arrString[0]);

            //Read durationTime each Task.
            for (int i = 1; i <= driver.numOfTask; ++i) {
                line = br.readLine();
                arrString = line.split(" ");
                driver.durationTime[i] = Integer.valueOf(arrString[0]);
            }

            //Read number of skills
            line = br.readLine();
            arrString = line.split(" ");
            driver.numOfSkill = Integer.valueOf(arrString[0]);

            //Read number of people
            line = br.readLine();
            arrString = line.split(" ");
            driver.numOfPeople = Integer.valueOf(arrString[0]);

            //Read adjacency 
            for (int i = 1; i <= driver.numOfTask; ++i) {
                line = br.readLine();
                arrString = line.split(" ");
                for (int j = 1; j <= driver.numOfTask; ++j) {
                    driver.adjacency[i][j] = Integer.valueOf(arrString[j - 1]);
                }
            }

            //Read matrix K
            for (int i = 1; i <= driver.numOfPeople; ++i) {
                line = br.readLine();
                arrString = line.split(" ");
                for (int j = 1; j <= driver.numOfSkill; ++j) {
                    driver.K[i][j] = Integer.valueOf(arrString[j - 1]);
                }
            }

            //Read matrix R
            for (int i = 1; i <= driver.numOfTask; ++i) {
                line = br.readLine();
                arrString = line.split(" ");
                for (int j = 1; j <= driver.numOfSkill; ++j) {
                    driver.R[i][j] = Integer.valueOf(arrString[j - 1]);
                }
            }

            // Read array T :salary
            line = br.readLine();
            arrString = line.split(" ");
            for (int i = 1; i <= driver.numOfPeople; ++i) {
                driver.salaryEachTime[i] = Integer.valueOf(arrString[i - 1]);
            }

            // Read matrix Z;
            for (int i = 1; i <= driver.numOfTask; ++i) {
                line = br.readLine();
                arrString = line.split(" ");
                for (int j = 1; j <= driver.numOfTask; ++j) {
                    driver.Z[i][j] = Double.valueOf(arrString[j - 1]);
                }
            }

            // Read Deadline
            line = br.readLine();
            arrString = line.split(" ");
            driver.Deadline = Integer.valueOf(arrString[0]);
            // Read U:the concentration;
            for (int i = 1; i <= driver.numOfPeople; ++i) {
                line = br.readLine();
                arrString = line.split(" ");
                for (int j = 1; j <= driver.Deadline; ++j) {
                    driver.U[i][j] = Double.valueOf(arrString[j - 1]);
                }
            }
            //Read Budget;
            line = br.readLine();
            arrString = line.split(" ");
            driver.Budget = Integer.valueOf(arrString[0]);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        long startTime = System.currentTimeMillis();
        driver.manAbleDo = driver.ManAbleDo(driver.K, driver.R, driver.numOfTask, driver.numOfPeople, driver.numOfSkill);
        driver.Exper = driver.GetExper(driver.K, driver.R, driver.numOfTask, driver.numOfPeople, driver.numOfSkill);

        // Push data to calculate each steps;
        Data data = new Data(driver.numOfTask, driver.numOfSkill, driver.numOfPeople, driver.durationTime, driver.adjacency, driver.salaryEachTime, driver.Z, driver.U, driver.Budget, driver.Deadline, driver.manAbleDo, driver.Exper);
        data.Setup();

        // Generate population
        // excel
     
        // title
        
        
        // Run Generation

        for(int i=1;i<=1;++i)
        {
        
         data.change_w(4);   
        Population population = new Population(GeneticAlgorithm.NUM_OF_POPULATION).initializePopulation(data);
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        int numOfGen = 0;
        while (numOfGen < GeneticAlgorithm.NUM_OF_GENARATION) {
            
            
            Chromosome best1 = population.getChromosomes()[0];
             best1.recalculateFitness(data);
            System.out.println(best1.getFitness(data));
            population = geneticAlgorithm.evolve(population, data);
            population.sortChromosomesByFitness(data);
            numOfGen++;
            
            

            }
        
   
        Chromosome best = population.getChromosomes()[0];
        int[] task_mem = new int[data.numOfPeople+1];
        for (int j=0;j<data.numOfPeople;j++){
            task_mem[j] = 0;
        }
        for (int j =0; j<data.numOfTask;j++){
            int member = best.getGenes()[j];
            task_mem[member]++;
        }
        best.recalculateFitness(data);

         System.out.println(best.timeFinish);
 
        

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
    }
    public ArrayList< ArrayList< Integer>> ManAbleDo(int[][] K, int[][] R, int n, int m, int s) {
        ArrayList< ArrayList< Integer>> manAbleDo = new ArrayList< ArrayList< Integer>>();
        for (int i = 1; i <= n; ++i) {
            ArrayList< Integer> lst = new ArrayList<Integer>();
            for (int j = 1; j <= m; ++j) {
                boolean ok = true;
                for (int k = 1; k <= s; ++k) {
                    if (R[i][k] > K[j][k]) {
                        ok = false;
                    }
                }
                if (ok == true) {
                    lst.add(j);
                }
            }
            manAbleDo.add(lst);
        }
        return manAbleDo;
    }

    public int[][] GetExper(int[][] K, int[][] R, int n, int m, int s) {
        int[][] Exper = new int[505][505];
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= m; ++j) {
                boolean ok = true;
                int cur = 0;
                for (int k = 1; k <= s; ++k) {
                    if (R[i][k] > K[j][k]) {
                        ok = false;
                    } else if (R[i][k] > 0) {
                        cur += K[j][k];
                    }
                }
                if (ok == true) {
                    Exper[i][j] = cur;
                }
            }
        }
        return Exper;
    }

}
