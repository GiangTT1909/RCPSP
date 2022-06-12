package prj_makespan;

import java.util.ArrayList;


public class Data {
    public int numOfPeople;
    public int numOfTask;
    public int numOfSkill;
    public int[] durationTime = new int[505];
    public int[][] adjacency = new int[505][505];
    public int[] salaryEachTime = new int[505];
    public double[][] Z = new double[505][505];
    public int Deadline;
    public int[] exp_change = new int[505];
    public int salary_change;
    public int Budget;
    public double[][] U = new double[505][10005];
    public ArrayList< ArrayList < Integer > > manAbleDo = new ArrayList< ArrayList < Integer > >();
    public int[][] Exper = new int[505][505];
    public int maxSalary ;
    public int maxDeadline ;
    public int maxEper;
    public double w1,w2,w3;
    public Data(int numOfTask, int numOfSkill, int numOfPeople,int[] durationTime, int[][] adjacency, int[] salaryEachTime , double[][] Z  ,double[][] U , int Budget, int Deadline, ArrayList<ArrayList<Integer>> manAbleDo, int[][] Exper) {
        this.numOfTask = numOfTask;
        this.numOfSkill = numOfSkill;
        this.numOfPeople = numOfPeople;
        this.adjacency = adjacency;
        this.Budget = Budget;
        this.Deadline = Deadline; 
        this.Exper = Exper ;
        this.manAbleDo = manAbleDo;
        this.salaryEachTime = salaryEachTime;
        this.Z = Z;
        this.U = U;
        this.durationTime = durationTime;
    }
    public void Setup() // get the limit max of sumsalary , deadline , exper ;
    {
        
        int min_salary = -1;
        for(int i=1;i<=numOfPeople;++i) {
            maxSalary = Math.max(maxSalary,salaryEachTime[i]);
            if (min_salary ==-1) min_salary = salaryEachTime[i];
            min_salary = Math.min(min_salary, salaryEachTime[i]);
        }
        salary_change = maxSalary-min_salary;
        for(int i=1;i<=numOfTask;++i)
        {
            maxDeadline+=durationTime[i];
        }
        maxDeadline+=Deadline;
        maxSalary *= maxDeadline;
        maxSalary *= numOfPeople;
        for(int i=1;i<=numOfTask;++i)
        {
            int cur=0;
            int cur_min = -1;
            for(int j=1;j<=numOfPeople;++j) 
            {
                cur = Math.max(cur , Exper[i][j]);
                if (cur_min==-1) cur_min = Exper[i][j];
                cur_min = Math.min(cur_min, Exper[i][j]);
            }
            exp_change[i] = cur-cur_min;
            maxEper+=cur;
        }
        this.w1=1;
        this.w2=1;
        this.w3=1;
    }
    
    public void change_w(int num){
        this.w1 = 1;
        this.w2 = 1;
        this.w3 = 1;
        if (num==1) this.w1*=20;
        else if (num==2) this.w2*=20;
        else if (num==3) this.w3*=20;
    }

    public double getW1() {
        return w1;
    }

    public void setW1(double w1) {
        this.w1 = w1;
    }

    public double getW2() {
        return w2;
    }

    public void setW2(double w2) {
        this.w2 = w2;
    }

    public double getW3() {
        return w3;
    }

    public void setW3(double w3) {
        this.w3 = w3;
    }
    
}
