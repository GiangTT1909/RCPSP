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
//        for(int i=1;i<=numOfPeople;++i) maxSalary = Math.max(maxSalary,salaryEachTime[i]);
//        for(int i=1;i<=numOfTask;++i)
//        {
//            maxDeadline+=durationTime[i];
//        }
//        maxDeadline+=Deadline;
//        maxSalary *= maxDeadline;
//        maxSalary *= numOfPeople;
//        for(int i=1;i<=numOfTask;++i)
//        {
//            int cur=0;
//            for(int j=1;j<=numOfPeople;++j) 
//            {
//                cur = Math.max(cur , Exper[i][j]);
//            }
//            maxEper+=cur;
//        }
        maxEper= 165552;
        maxSalary = 25903115;
        maxDeadline = 1233;
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
    
}
