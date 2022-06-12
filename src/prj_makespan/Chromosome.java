package prj_makespan;

import java.util.ArrayList;
import java.util.Collections;

public class Chromosome {

    public boolean isFitnessChanged = true;
    public int[] genes;
    private double fitness = 0;
    public int totalSalary = 0;
    public int totalExperience = 0;
    public int timeFinish = 0;
    public int[] beginTask = new int[505];
    public int[] endTask = new int[505];
    public int[] timeSt = new int[505];
    public int[] timeFn = new int[505];

    public Chromosome(int len) {
        genes = new int[len];
    }

    public void setGenes(int[] genes) {
        isFitnessChanged = true;
        this.genes = genes;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public Chromosome initializeChromosome(Data data) {
        for (int i = 0; i < data.numOfTask; ++i) {
            int x = data.manAbleDo.get(i).size();
            int c = (int) (Math.random() * x);
            genes[i] = data.manAbleDo.get(i).get(c);
        }
        return this;
    }

    public double getFitness(Data data) {
        if (isFitnessChanged) {
            double x;
            recalculateFitness(data);
//            if(timeFinish>data.Deadline || totalSalary>data.Budget){
//                x=1;
//            }
//            else{
            x = (data.w1 * timeFinish / data.maxDeadline + data.w2 * totalSalary / data.maxSalary + data.w3 * (data.maxEper - totalExperience) / data.maxEper);

//                }
            fitness = x;
            isFitnessChanged = false;
        }
        return fitness;
    }

    public int[] getGenes() {
//        isFitnessChanged = true;
        return genes;
    }

    public void recalculateFitness(Data data) {
        ArrayList< Integer> lst = new ArrayList<Integer>();
        int[] lastMan = new int[505];
        int[] timeTask = new int[505];
        int[] working_total = new int[505];
        int[] worker_task = new int[505];
        totalExperience = 0;
        timeFinish = 0;
        totalSalary = 0;
        for (int i = 1; i <= data.numOfPeople; ++i) {
            timeSt[i] = 0;
            timeFn[i] = 0;
            lastMan[i] = 0;
            working_total[i] = 0;
        }
        for (int i = 1; i <= data.numOfTask; ++i) {
            timeTask[i] = 0;
            worker_task[i] = 0;
        }
        int[] DEG = new int[505];
        for (int i = 1; i <= data.numOfTask; ++i) {
            for (int j = 1; j <= data.numOfTask; ++j) {
                if (data.adjacency[i][j] == 1) {
                    DEG[j]++;
                }
            }
        }
        for (int i = 1; i <= data.numOfTask; ++i) {
            if (DEG[i] == 0) {
                lst.add(i);
            }
        }

        while (lst.size() > 0) {
//            Collections.sort(lst,(x1,x2)->{
//                int flag =0;
//                if(data.durationTime[x1]>data.durationTime[x2]) flag = -1;
//                if(data.durationTime[x1]<data.durationTime[x2]) flag = 1;
//                return flag;
//            });
            int x = lst.get(0);
            lst.remove(0);
            int y = genes[x - 1];
            totalExperience += data.Exper[x][y];
            int start = 0;
            for (int i = 1; i <= data.numOfTask; ++i) {
                if (data.adjacency[i][x] == 1) {
                    start = Math.max(start, timeTask[i]);
                }
            }
            start = Math.max(start, lastMan[y]);
            if (start == 0) {
                start = 1;
            }
            while (data.U[y][start] == 0 && start < data.Deadline) {
                start++;
            }
            int end = start;
            double cc = data.durationTime[x];
            double maxDec = 0;
            for (int i = 1; i < data.numOfTask; ++i) {
//                if(timeTask[i]!=0)
                if (worker_task[i] == y) {
                    maxDec = Math.max(maxDec, data.Z[i][x]);
                }
            }
            if (maxDec > 0.75) {
                cc *= 0.7;
            } else if (maxDec > 0.5) {
                cc *= 0.8;
            } else if (maxDec > 0.25) {
                cc *= 0.9;
            }
            while (end <= data.Deadline) {
                cc -= (data.U[y][end]);
                end++;
                if (cc <= 0) {
                    break;
                }
            }
            if (cc > 0) {
                end += (int) (cc + 0.9);
            }
            lastMan[y] = end;
            timeTask[x] = end;
            if (timeSt[y] == 0) {
                timeSt[y] = start;
            }
            timeFn[y] = Math.max(timeFn[y], end);
            working_total[y] += (end - start);
            for (int i = 1; i <= data.numOfTask; ++i) {
                if (data.adjacency[x][i] == 1) {
                    DEG[i]--;
                    if (DEG[i] == 0) {
                        lst.add(i);
                    }
                }
            }
            beginTask[x] = start;
            endTask[x] = end;
            timeFinish = Math.max(timeFinish, end);
        }
        for (int i = 1; i <= data.numOfPeople; ++i) {
//            totalSalary += data.salaryEachTime[i]*(timeFn[i]-timeSt[i]);
            totalSalary += data.salaryEachTime[i] * working_total[i];
        }
    }

    public int[] getBeginTask() {
        return beginTask;
    }

    public int[] getEndTask() {
        return endTask;
    }

    public int[] getTimeSt() {
        return timeSt;
    }

    public int[] getTimeFn() {
        return timeFn;
    }

}
