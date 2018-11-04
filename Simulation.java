/*
 * Kurt Higa
 * kuhiga
 * 2-26-18
 * cmps-12b-2
 * 
 * The purpose of this program is to Simulate processors working a job.
 */

import java.io.*;
import java.util.Scanner;
public class Simulation {
	
	/*
	 * edge case:
	 * two jobs finish at same time
	 * one job arrives as another finishes
	 */
	    // -----------------------------------------------------------------------------
		//
		// The following function may be of use in assembling the initial backup and/or
		// storage queues. You may use it as is, alter it as you see fit, or delete it
		// altogether.
		//
		// -----------------------------------------------------------------------------

		public static Job getJob(Scanner in) {
			String[] s = in.nextLine().split(" ");
			int arrivalTime = Integer.parseInt(s[0]);
			int duration = Integer.parseInt(s[1]);
			return new Job(arrivalTime, duration);
		}

		// -----------------------------------------------------------------------------
		//
		// The following code assigns a job to the smallest queue. If there are two
		// equally small queue, the job is assigned to the nearest one (smaller index).
		//
		// -----------------------------------------------------------------------------
/*
 * method to determine which queue is the shortest.
 */
		public static int whichQueueShortest(Queue[] list) {
			int shortest = 100;
			int shortestQueueIndex = 0;
			for (int i = 1; i < list.length; i++) {
				if ((list[i]).length() < shortest) {
					shortest  = (list[i]).length();
					shortestQueueIndex = i;
				}
			}
			return shortestQueueIndex;
		}
		
		public static void main (String [] args) throws IOException{


			if (args.length < 1) {
				System.out.println("Usage: Simulation input_file");
				System.exit(1);
			}
			
			//
			// 2. open files for reading and writing
			//
			Scanner in = new Scanner(new File(args[0]));
			

			// 3. read in m jobs from input file

			int m; // m jobs
			Job j = null;
			m = Integer.parseInt(in.nextLine());
			PrintWriter trace = new PrintWriter(new FileWriter(args[0] + ".trc"));
			trace.println("Trace file: " + args[0] + ".trc");
			
			
			PrintWriter report = new PrintWriter(new FileWriter(args[0] + ".rpt"));
			report.println("Report file: " + args[0] + ".rpt");
			
			Queue backUp = new Queue();
			while (in.hasNext()) { //fill backUp queue
				j = getJob(in);
				backUp.enqueue(j);
			}


			for (int n = 1; n <m; n++) { //run simulation with n processors for n=1 to n=m-1
				
				int time = 0; //keeps track of time
				int totalWaitTime = 0; 
				int waitTime = 0;
				int maxWaitTime = 0;
				double averageWaitTime = 0; 
				boolean update = false; //if true prints update to trace
				int jobsLeft = m; //counter for processed jobs; every time job finishes processing, count goes up
				
				//queueList is the array that holds all the processors plus the storage queue
				Queue queueList [] = new Queue[n+1]; //creates an array of Queues including the number of processors plus 1 storage queue
				queueList[0] = new Queue();
				for(int i = 0; i <m; i++) { //copies backUp queue to storage queue (queueList[0])
					Job temp = (Job) backUp.dequeue();
					queueList[0].enqueue(temp);
					backUp.enqueue(temp);

				}
				
				for(int i = 1; i< queueList.length; i++) { //declares the queues
					queueList[i] = new Queue();
				}
				
				if(n==1) { //prints to file only once at the beginning
					trace.println(m + " Jobs:");
					trace.println(queueList[0].toString()); // (2, 2, *) (3, 4, *) (5, 6, *)
					trace.println();
					trace.println("*****************************");
					report.println(m + " Jobs:");
					report.println(queueList[0].toString()); // (2, 2, *) (3, 4, *) (5, 6, *)
					report.println();
					report.println("***********************************************************");
				}
				
				if(n==1){ 
					trace.println(n + " processor:");
				}else {
					trace.println("*****************************");

					trace.println(n + " processors:");
				}
				trace.println("*****************************");
								
				/*
				 * first finishes current job
				 * then adds job to shortest aueue 
				 */
				
				/*
				 * There are two scenarios in which the job's finish time can be computed
				 * 1. when a job in a certain queue is finished, the new front of that queue's job's finish time is computed
				 * 2. when a job's arrival time makes that job move from the storage (queueList[0]) to the shortest Queue, 
				 * 		and after moving the job to the shorest queue, the shortest queue is length 1,
				 * 		then that means that the job is already at the front of the queue, so compute finish time.
				 */
				
				while(jobsLeft>0) { //while number of processed jobs is less than total job count
					/*
					 * block of code that checks if job is done then adds the job back to storage queue
					 */
					for (int i = 1; i <queueList.length; i++) { //loop through all processor queues
			
						if(!(((queueList[i])).isEmpty())) { //if the i-th queuelist is not empty, which means there are still jobs in the processor

							if (((Job) queueList[i].peek()).getFinish() == time) { //if queuelist's i-th queue's front job's finish time is equal to current time
																
								waitTime = (((Job) queueList[i].peek()).getFinish()  //calculate finish time
										- (((Job) queueList[i].peek()).getArrival())
										- ((Job) queueList[i].peek()).getDuration());
								
								totalWaitTime += waitTime;							//add wait time to total wait time
								
								if (waitTime > maxWaitTime) {						//if wait time is greater than max wait time replace max wait time 
									maxWaitTime = waitTime;
								}
								
								queueList[0].enqueue(queueList[i].dequeue());		//put processed job back into original queue
								if(!queueList[i].isEmpty())
								((Job)queueList[i].peek()).computeFinishTime(time); //computes finish time SCENARIO 1
								
								jobsLeft--;   //job processed
								
								update = true;							//something happened so print to trace
							
							}
						}
					}	
								
				 int shortestQueue;	//position of shortest queue

				/*
				 * block of code that puts jobs in their respected queue	 
				 */
				 if(jobsLeft > 0) {  //if processing is not done yet
					 if(!queueList[0].isEmpty())
					 while (((Job)(queueList[0].peek())).getArrival() == time) {
						 shortestQueue = whichQueueShortest(queueList);
						 (queueList[shortestQueue]).enqueue(queueList[0].dequeue()); // assigns job to shortest queue
						 update = true;
						 
						 if(queueList[shortestQueue].length()==1) {
							((Job) queueList[shortestQueue].peek()).computeFinishTime(time);	//computes finish time SCENARIO 2
						 }
						 if(queueList[0].isEmpty()) {
							 break;
						 }
					 }
				 }
				
				/*
				 * whenever these is a change to a queue the update boolean variable changes into true which means that theres should be a
				 * new print to the trace file. 
				 * If time is 0, always print something.
				 */
				if (update || (time==0)) {	//if there is update print to trace
					trace.println("time=" + time);
					
					for (int i = 0; i < queueList.length; i++) {
						trace.println(i + ": " + queueList[i].toString());
					}		
					trace.println();
					update = false; //change update back to false
				}
				
				time++;
				}

				averageWaitTime = (double) totalWaitTime / m;

				if(n==1) {
				report.println(n + " processor: totalWait=" + totalWaitTime + ", maxWait=" + maxWaitTime + ", averageWait="
								+ String.format("%.2f", averageWaitTime));
				}else {
				report.println(n + " processors: totalWait=" + totalWaitTime + ", maxWait=" + maxWaitTime + ", averageWait="
							+ String.format("%.2f", averageWaitTime));
				}

		
			/*
			 * reseting all finish time in backUp queue
			 */
				for(int i = 0; i <m; i++) {
					Job temp = (Job) backUp.dequeue();
					temp.resetFinishTime();
					backUp.enqueue(temp);

				}
			
				
			}
			
		in.close();
		trace.close();
		report.close();
		}
}