import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.Random.*;

public class conwaysFitness{
   public static void main(String[] args){
      int[][] design1 = new int[8][8];
      int[][] design2 = new int[8][8];
      
      for(int c = 0; c < 8; c++){
         design1[4][c] = 1;
      }
      
      design2[2][2] = 1;
      design2[3][1] = 1;
      design2[3][2] = 1;
      design2[3][3] = 1;
      design2[4][1] = 1;
      design2[4][3] = 1;
      design2[5][2] = 1;
 
      System.out.println("The fitness of Design 1 is: " + fitness(design1)); 
      System.out.println("The fitness of Design 2 is: " + fitness(design2));
  
      int[][] current= new int [32][32]; 
      int[][] start = randSeed();
      int[][] bestDesign = start;
      int bestFit = fitness(start);
     
      long begin = System.currentTimeMillis();
      long timeLapse = 0;
      int loop = 0;
      System.out.println("Beginning test of random 8x8 designs to determine the best fitness.");
      while(timeLapse<=7200000){
         current = randSeed(); 
         int currFit = fitness(current);
         if(currFit > bestFit){
            bestFit = currFit;
            bestDesign = current;
            System.out.println("New best fitness: " + bestFit + "\tIteration: " + loop);  
         }
         long finish = System.currentTimeMillis();
         timeLapse = finish - begin;
         loop++;
      } 
      System.out.println("The best design has a fitness of " + bestFit  + " after " + loop + " iterations");
      printGrid(bestDesign);        
   }
   
   public static int[][] randSeed(){
      int[][] newSeed = new int[8][8];
      for(int d = 0; d < 8; d++){
         for(int e = 0; e < 8; e++){
            Random rand = new Random();
            int n = rand.nextInt(2) + 1;
            newSeed[d][e] = n-1;
         }
      }
      return newSeed;
   }
   
   public static int fitness(int[][] seed){
      int[][] current = new int[32][32];
      for(int x = 12; x < 20; x++){
         for(int i = 12; i < 20; i++){
            current[x][i] = seed[x-12][i-12];
         }
      }
      
      int[][] next;
      int fitnessVal = 0;
      for(int j = 0; j < 1000; j++){
         next = conwaysMethod(current);
         current = next;
      }  
      
      for(int a = 0; a < current.length; a++){
         for (int b = 0; b < current.length; b++){
            if(current[a][b] == 1){ fitnessVal++; }
         }
      } 
      return fitnessVal;   
   }
   
   public static int[][] conwaysMethod (int[][] beacon){
      int[][] result = new int [32][32];
      for(int x = 1; x < beacon.length-1; x++){
         for(int i = 1; i < beacon.length-1; i++){
            int aliveCt = 0;

            if(beacon[x-1][i+1] == 1){ aliveCt++; }
            if(beacon[x-1][i] == 1){ aliveCt++; }
            if(beacon[x-1][i-1] == 1){ aliveCt++; }
            if(beacon[x][i-1] == 1){ aliveCt++; }
            if(beacon[x+1][i] == 1){ aliveCt++; }
            if(beacon[x+1][i+1] == 1){ aliveCt++; }
            if(beacon[x][i+1] == 1){ aliveCt++; }
            if(beacon[x+1][i-1] == 1){ aliveCt++; }

            if(beacon[x][i] == 1 && (aliveCt == 2 || aliveCt == 3)){ result[x][i] = 1; }
            else if(beacon[x][i] == 0 && aliveCt == 3){ result [x][i] = 1; }
            else{ result[x][i] = 0; }     
         }
      }
      return result;
   } 
   
   public static void printGrid(int[][] grid){
      for(int x = 0; x < grid.length; x++){
         System.out.println();
         for(int i = 0; i < grid.length; i++){  
            if(grid[x][i] == 0){ System.out.print("."); }
            else{ System.out.print("*"); }
         }
      }
   }
}
