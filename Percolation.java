import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
  private static final int OPEN = 0;
  private static final int BLOCKED = 1;
  private int[][] grid;
  private int num;
  private int top = 0;
  private int bottom = 0;  
  private WeightedQuickUnionUF wquf;
  
 
   public Percolation(int n)                // create n-by-n grid, with all sites blocked
   {
       if (n < 1)
           throw new java.lang.IndexOutOfBoundsException();
       
     grid = new int[n][n];
     for (int i = 0; i < n; i++)
     {
         for (int j = 0; j < n; j++)
         {
             grid[i][j] = BLOCKED;
         }
     }
     
     wquf = new WeightedQuickUnionUF(n*n + 3);
     bottom = n*n + 1;
     top = n*n + 2;
     num = n;
   }
   
   public void open(int row, int col)  // open site (row, col) if it is not open already
   {
       if (row < 1 || row > num || col < 1 || col > num)
       {
           throw new java.lang.IndexOutOfBoundsException();
       }
       
       if (grid[row-1][col-1] == OPEN) // already open
           return;
       
       grid[row-1][col-1] = OPEN;
       
       if (row == 1)
       {
           // connecting top row open sites
           wquf.union(getIndex(row, col), top);       
       }
       
       if (row == num)
       {
           // connecting bottom row open sites
           wquf.union(getIndex(row, col), bottom);
       }
       
       if (col > 1 && isOpen(row, col-1))
       {
           wquf.union(getIndex(row, col), getIndex(row, col-1));
       }
       if (col < num && isOpen(row, col+1))
       {
           wquf.union(getIndex(row, col), getIndex(row, col+1));
       }
       if (row > 1 && isOpen(row-1, col))
       {
           wquf.union(getIndex(row, col), getIndex(row-1, col));
       }
       if (row < num && isOpen(row+1, col))
       {
           wquf.union(getIndex(row, col), getIndex(row+1, col));
       }
       
       
   }    
   
   public boolean isOpen(int row, int col)  // is site (row, col) open?
   {
     if (grid[row-1][col-1] == OPEN)
         return true;

     return false;
   }
   
   public boolean isFull(int row, int col)  // is site (row, col) full?
   {
       if (row < 1 || row > num || col < 1 || col > num)
       {
           throw new java.lang.IndexOutOfBoundsException();
       }
       else
       {
           return wquf.connected(top, getIndex(row, col));
       }
       
   }
   
   public int numberOfOpenSites()       // number of open sites
   {
      int count = 0;
      for (int i = 0; i < num; i++)
      {
          for (int j = 0; j < num; j++)
          {
              if(grid[i][j] == OPEN)
                  count++;
          }
      }
      
      return count;
   }
   
   public boolean percolates()              // does the system percolate?
   {
       return wquf.connected(top, bottom);
   }
       
   private int getIndex(int row, int col)
   {
       return (row-1)*num + (col-1);
   }
   /* public static void main(String[] args)   // test client (optional) */
}
