import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static Percolation per;
    private int cnt = 0;
    private double[] prob;
    
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   {
       if (n <= 0 || trials <= 0)
       {
           throw new java.lang.IllegalArgumentException();
       }
       
       cnt = trials;
       prob = new double[cnt];
       
       for (int i = 0; i < trials; i++)
       {
           per = new Percolation(n);
           int opensites = 0;
           
           while (!per.percolates())
           {
               int row = StdRandom.uniform(1, n+1);
               int col = StdRandom.uniform(1, n+1);
               
               if (!per.isOpen(row, col))
               {
                   per.open(row, col);
                   opensites++;
               }
           }
           
           prob[i] = (double) opensites/(n*n);
       }
   }
   
   public double mean()                        // sample mean of percolation threshold
   {
       return StdStats.mean(prob);
   }
   
   public double stddev()                        // sample standard deviation of percolation threshold
   {
       return StdStats.stddev(prob);
   }
   
   public double confidenceLo()                 // low  endpoint of 95% confidence interval
   {
       return mean() - (1.96*stddev()) / Math.sqrt(cnt);
   }
   
   public double confidenceHi()                 // high endpoint of 95% confidence interval
   {
       return mean() + (1.96*stddev()) / Math.sqrt(cnt);   
   }
   
   public static void main(String[] args)       // test client (described below)
   {
       int n = Integer.parseInt(args[0]);
       int trials = Integer.parseInt(args[1]);
       
       PercolationStats perStat = new PercolationStats(n, trials);
       System.out.println("mean = " + perStat.mean());
       System.out.println("stddev = " + perStat.stddev());
       System.out.println("95% confidenceL interval = [" + perStat.confidenceLo() + ", " + perStat.confidenceHi() + "]");
   }
}