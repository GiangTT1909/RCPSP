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

import java.util.Random;


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
        ArrayList<Double> answer = new ArrayList< Double>();

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

        System.out.println(data.maxEper);
        // Generate population
        // excel

        // title
        // Run Generation
        long endTime = System.currentTimeMillis();
//        
//        for(int i=1;i<=1;++i)
//        {
        startTime = System.currentTimeMillis();
        BufferedWriter writer = new BufferedWriter(new FileWriter("data\\output1.txt"));
        BufferedWriter writer2 = new BufferedWriter(new FileWriter("data\\output2.txt"));
        BufferedWriter writer3 = new BufferedWriter(new FileWriter("data\\output3.txt"));
        BufferedWriter writer4 = new BufferedWriter(new FileWriter("data\\output4.txt"));
        writer2.write("Fitness ; Time Finish ; Total Salary ; Total Exper \n");
        writer.write("Fitness ; Time Finish ; Total Salary ; Total Exper \n");

        // Generate population
        // Run Generation
        // excel
        // title
//        System.out.println(data.Budget);
//        for(int i=0;i<=1;i++){
//              Random rand = new Random();
//            int x = rand.nextInt(3);
//            data.setW1(1);
//             x = rand.nextInt(3);
//            data.setW2(1);
//             x = rand.nextInt(1);
//            data.setW3(1);
//               Population population = new Population(GeneticAlgorithm.NUM_OF_POPULATION).initializePopulation(data);
//        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
//        int numOfGen = 0;
//        while (numOfGen < GeneticAlgorithm.NUM_OF_GENARATION) {
//            
//            System.out.println(numOfGen+"-"+i);
//            
//            population = geneticAlgorithm.evolve(population, data);
//            population.sortChromosomesByFitness(data);
//            
//           
//            numOfGen++;
//         
//
//            }
//         writer2.write(population.getChromosomes()[0].getFitness(data)+ ";" + population.getChromosomes()[0].timeFinish +";" + population.getChromosomes()[0].totalSalary+ " ;" + population.getChromosomes()[0].totalExperience+ "\n");
////       
//        }
//        



        Population population = new Population(GeneticAlgorithm.NUM_OF_POPULATION).initializePopulation(data);
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        int numOfGen = 0;
        while (numOfGen < GeneticAlgorithm.NUM_OF_GENARATION) {
            

            System.out.println(numOfGen);
            
            population = geneticAlgorithm.evolve(population, data);
            population.sortChromosomesByFitness(data);
            
            writer2.write(population.getChromosomes()[0].getFitness(data)+ ";" + population.getChromosomes()[0].timeFinish +";" + population.getChromosomes()[0].totalSalary+ " ;" + population.getChromosomes()[0].totalExperience+ "\n");
            numOfGen++;
}
            writer3.write(population.getChromosomes()[0].getFitness(data)+ ";" + population.getChromosomes()[0].timeFinish +";" + population.getChromosomes()[0].totalSalary+ " ;" + population.getChromosomes()[0].totalExperience+ "\n");

            
//             for(int i=0;i<driver.numOfTask;i++){
//            writer3.write(population.getChromosomes()[0].genes[i]+" "+population.getChromosomes()[0].getBeginTask()[i+1]+" "+population.getChromosomes()[0].getEndTask()[i+1]+"\n");
//        }
//        for (int i = 0; i <= 150; i++) {
//            Random rand = new Random();
//            int x = rand.nextInt(10);
//            data.setW1(x);
//            x = rand.nextInt(10);
//            data.setW2(x);
//            x = rand.nextInt(10);
//            data.setW3(x);
//            Population population = new Population(GeneticAlgorithm.NUM_OF_POPULATION).initializePopulation(data);
//            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
//            int numOfGen = 0;
//            while (numOfGen < GeneticAlgorithm.NUM_OF_GENARATION) {
//
//                System.out.println(numOfGen + "-" + i);
//
//                population = geneticAlgorithm.evolve(population, data);
//                population.sortChromosomesByFitness(data);
//
//                numOfGen++;
//
//            }
//            writer2.write(population.getChromosomes()[0].getFitness(data) + ";" + population.getChromosomes()[0].timeFinish + ";" + population.getChromosomes()[0].totalSalary + " ;" + population.getChromosomes()[0].totalExperience + "\n");
////       
//        }
//
        writer.close();
        writer2.close();
        writer3.close();
        writer4.close();

    }
//            int[] genes = population.getChromosomes()[0].getGenes();
//            for (int j=0;j<genes.length;j++){
//                System.out.println("Task "+j+": "+genes[j]);
//            }

//        System.out.println(endTime - startTime);

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
