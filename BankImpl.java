package Banker;


/**
 * The Bank
 */

import static banker.Customer.COUNT; 

import static banker.Factory.*; 

import java.util.*; 

public class BankImpl implements Bank {
	
int[][] maximum = new int[COUNT][numOfResources]; 

    int[][] need = new int[COUNT][numOfResources] ; 

    int[][] needTemp = new int[COUNT][numOfResources] ; 

    int[] available = new int[numOfResources]; 

    int[] work = new int[numOfResources]; 

     

    int[][] allocTemp = new int[COUNT][numOfResources]; 

    boolean[] finish = new boolean[COUNT]; 

BankImpl(int[] resources) { 

        // intialize for available resources  

      for (int i = 0; i < resources.length; i++) { 

            available[i] = resources[i]; 

            work[i] = available[i];   // work matrix 

        } 

     } 


    @Override
    public void addCustomer(int threadNum, int[] maxDemand) {
       
	//Create the workers array  
        workers[threadNum] =new Thread(new Customer(threadNum,maxDemand,theBank)); 
    }


 private void computeNeed(){ 
        // need matrix && need temp matrix && allocTemp 

     	 for (int i = 0; i < COUNT; i++) { 

            for (int j = 0; j < numOfResources; j++) { 

                need[i][j] = maxArr[i][j] - allocArr[i][j]; 

                needTemp[i][j] = maxArr[i][j] - allocArr[i][j]; 

                allocTemp[i][j] = allocArr[i][j]; 

            } 

      } 

    } 

    @Override
    public void getState() {
         for (int i = 0; i < finish.length; i++) { 

            finish[i] = false; 

        } 

        for (int i = 0; i < finish.length; i++) { 

            if(finish[i] == false || needTemp[threadNum][i] <= work[i]){ 

                work[i] = work[i] + allocTemp[threadNum][i]; 

                finish[i] = true; 

            } 

        } 

        for (int i = 0; i < finish.length; i++) { 

            if(finish[i] == false) 

                return false; // UnSafe state 

        } 

        return true; // safe state 
    }

    @Override
    public boolean requestResources(int threadNum, int[] request) {
       
	 computeNeed(); 

      System.out.println("Customer " + threadNum + " request " + Arrays.toString(request) + " and the available is " + Arrays.toString(available)); 

     for (int i = 0; i < request.length; i++) { 

            if(request[i] > need[threadNum][i]){ 

                System.out.println("Customer " +threadNum + " has exceeded its need claim " + 	Arrays.toString(need[threadNum]) 

                + " So the request is denied"); 

                return false; 

            } 

        } 

        for (int i = 0; i < request.length; i++) { 

            if(request[i] > available[i]){ 

                System.out.println("Customer " + threadNum + " must wait, since resources are not available "); 

                return false; 

            } 

        } 

   	 boolean state = pretend(threadNum,request); 
		
   	 if(state == true){  // Safe State 

            for (int i = 0; i < available.length; i++) { 

                available[i] = work[i]; 

            } 

            for (int i = 0; i < COUNT; i++) { 

                for (int j = 0; j < numOfResources; j++) { 

                    need[i][j] = needTemp[i][j]; 

                    allocArr[i][j] = allocTemp[i][j]; 

                } 

   	     } 

            System.out.println("The request "+ Arrays.toString(request) + " of customer " + threadNum 

                    +" will make the system in SAFE state , so it's Done"); 

            return true; 

  	 }else{  // UnSafe state 

            System.out.println("The request "+ Arrays.toString(request) + " of customer " + threadNum + 

                    " will make the System in UNSAFE state so , customer " + threadNum + " will take a nap"); 

        	return false; 

        } 
    }

	private boolean pretend(int threadNum,int[] request){ 

        	for (int i = 0; i < request.length; i++) { 

          	  work[i] = work[i] - request[i]; 

           	 allocTemp[threadNum][i] = allocTemp[threadNum][i] + request[i];  

            	needTemp[threadNum][i] = needTemp[threadNum][i] - request[i]; 

        } 

	boolean state = this.getState(threadNum);  // return the state of the system 

        return state; 

    } 

    @Override
    public void releaseResources(int threadNum, int[] release) {
        for (int i = 0; i < numOfResources; i++) { 

            available[i] = allocArr[threadNum][i] + realese[i]; 

        } 

        System.out.println("Customer " + threadNum + " realsing " + Arrays.toString(realese)  

                + " , available = " + Arrays.toString(available) + 

                " , Allocated = " + Arrays.deepToString(allocArr)); 

    	
    }

}
