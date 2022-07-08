package Banker;
/**
 * A factory class that creates (1) the bank and (2) each customer at the bank.
 *
 * Usage:
 *	java Factory <one or more resources>
 *
 * I.e.
 *	java Factory 10 5 7
 */

import java.io.*;
import java.util.*;

public class Factory
{
	public static int numOfResources = 3; 

    	public static int allocArr[][] = new int[Customer.COUNT][numOfResources]; 

   	public static int[] maxDemand = new int[numOfResources]; 

   	public static int maxArr[][] = new int[Customer.COUNT][numOfResources]; 
    	
	public static Thread[] workers = new Thread[Customer.COUNT]; 

   	public static Bank theBank = null; 

	public static void main(String[] args) {
	
	Scanner sc = new Scanner(System.in);  

        int[] resources;                 

        if(args.length == 0){ 

            resources = new int[]{10, 5, 7}; 

        }else{ 

            numOfResources = args.length; 

            resources = new int[numOfResources]; 

            for (int i = 0; i < numOfResources; i++) 

                 resources[i] = Integer.parseInt(args[i].trim()); 

        } 

        theBank = new BankImpl(resources); 

                /* Allocation Array. */ 

        for (int i = 0; i < Customer.COUNT; i++) { 

            for (int j = 0; j < numOfResources; j++) { 

                /* Enter allocation of instances of resources. */ 

                System.out.println("enter allocation instances of resources: " + j + " for process p" + i); 

                /* Enter allocation of instances of resources. */ 

                allocArr[i][j] = sc.nextInt(); 

            } 

        } 

        // start all the customers 

        System.out.println("FACTORY: created threads"); 

        // read initial values for maximum array  

        System.out.println("enter maximum of resources: "); 

        for (int i = 0; i < Customer.COUNT; i++) { 

            for (int j = 0; j < numOfResources; j++) { 

                System.out.println("enter max instances of resources:" + j + "for process p" + i); 

                /* Enter max instances of resources for a given resource. */ 

                int number = sc.nextInt(); 

                while(number > resources[j]){ 

                    System.out.println("Invalid input as it greater than the maximum " + resources[j]); 

                    number = sc.nextInt(); 
               } 

                maxArr[i][j] = number; 

                maxDemand[j] = number; 

            } 

            theBank.addCustomer(i, maxDemand); 
        } 

        System.out.println("FACTORY: started threads"); 

        for (int i = 0; i < Customer.COUNT; i++) { 

            workers[i].start(); 
        } 

        try { 
		Thread.sleep(10000); 
	} catch(InterruptedException ie) { } 

             
        System.out.println("FACTORY: interrupting threads"); 

        for (int i = 0; i < Customer.COUNT; i++) 
            workers[i].interrupt(); 

} 
                 
              
}
