import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;

public class AGScheduler {
    
    int timeQuantum;
    Vector<Process> processes;
    Queue<Process> readyQueue;
    Vector<Process> dieList;

    public AGScheduler(Vector<Process> processes, int timeQuantum){
        this.timeQuantum = timeQuantum;
        this.processes = processes;
        this.readyQueue = new LinkedList<Process>();
        dieList = new Vector<Process>();

        for(Process process : processes){
          int randomNumber = RandomNumber(0, 20);
        
          if(randomNumber < 10){
              process.AGFactor = randomNumber + process.arrivalTime + process.burstTime;
          }else if(randomNumber > 10){
              process.AGFactor = 10 + process.arrivalTime + process.burstTime;
          }else{
              process.AGFactor = process.priority + process.arrivalTime + process.burstTime;
          }
        }
    }

    public void schedule(){
      int time = 0;
      Process currentExcuted = null;
      int counter = 0;

      while (dieList.size() == processes.size()) {
        // get the new arrived process if exist
        Process arrivedProcess = null;
      
        for (Process process : processes) {
          if (time >= process.arrivalTime) {
            if (arrivedProcess == null) {
                arrivedProcess = process;
            }

            // if there is multiple processes arrived together
            // take the smallest and put the others in the ready queue
            else {
              if (process.AGFactor < arrivedProcess.AGFactor) {
                readyQueue.add(arrivedProcess);
                arrivedProcess = process;
              }
              else {
                readyQueue.add(process);
              }
            }
          }
        }

        // excute the process in non-preemptive order first if exist new process 
        if (arrivedProcess != null) {
          excuteNonPreemptive(arrivedProcess, time);
        } else {
          excuteNonPreemptive(currentExcuted, time);
        }
        
        excutePreemptive(arrivedProcess, currentExcuted, time, counter);
        time += 1;
        counter += 1;
      }
    }

    private int RandomNumber(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }

    private void IncreaseQuantumTime(Process currProcess, int time) {
      double sumOfQuantumTime = 0;
      for(Process process : processes) {
        if (process.arrivalTime <= time) {
          sumOfQuantumTime += process.timeQuantum;
        }
      }
      currProcess.timeQuantum += Math.ceil((10/100) * (sumOfQuantumTime / processes.size()));
    }

    private void excuteNonPreemptive(Process arrivedProcess, int time) {
      int halfOfQuantumTime = (int) Math.ceil((double) arrivedProcess.timeQuantum / 2);

      // remaining time = 1 ==> quantum time = 2
      if (arrivedProcess.remainingTime - halfOfQuantumTime <= 0) {
        // calculate turn around time
        arrivedProcess.turnaroundTime = (arrivedProcess.remainingTime + time) - arrivedProcess.arrivalTime;

        // calculate waiting time 
        arrivedProcess.waitingTime = arrivedProcess.turnaroundTime -  arrivedProcess.burstTime;
        time += arrivedProcess.remainingTime ;
        return;
      }
      
      arrivedProcess.remainingTime -= halfOfQuantumTime;
      time += halfOfQuantumTime;
    }

    private void excutePreemptive(Process arrivedProcess, Process currentExcuted, int time, int count) {
      // there is new process arrived 
      if (arrivedProcess != null) {
        // compare its AG with the current excuted
        if (arrivedProcess.AGFactor < currentExcuted.AGFactor) {
          // add the remaining unused quantum time
          currentExcuted.timeQuantum += currentExcuted.timeQuantum - count;
          // update the burst time
          currentExcuted.remainingTime -= count;
          // add the process to the ready queue
          readyQueue.add(currentExcuted);
          // excute the arrived process
          currentExcuted = arrivedProcess;
          count = 0;
        }
        // put the arrived process in the ready queue
        else {
          readyQueue.add(arrivedProcess);
        }
      }

      // current process finishes its remaining time
      else if (currentExcuted.remainingTime - count <= 0) {
        // calculate the turnaround time for the finished process
        currentExcuted.turnaroundTime = time - currentExcuted.arrivalTime;
        // calculate waiting time for the finished proces
        currentExcuted.waitingTime = currentExcuted.turnaroundTime - currentExcuted.burstTime;
        // update the time quantum by 0
        currentExcuted.timeQuantum = 0;
        // update the remaining time
        currentExcuted.remainingTime -= count;
        // add the current excuted process to the die list (list that contains all finished processes)
        dieList.add(currentExcuted);
        // get new process from the ready queue
        currentExcuted = readyQueue.poll();
        // reset the count
        count = 0;
      }

      // if currrent exuted process finishes its quantum time
      else if (currentExcuted.timeQuantum == count + Math.ceil((double)currentExcuted.timeQuantum / 2 )) {
        // increase quantum time by 10% of the mean quantum time
        IncreaseQuantumTime(currentExcuted, time);

        // decrease quantum time
        currentExcuted.remainingTime -= count;

        // add the excuted process to the read queue
        readyQueue.add(currentExcuted);

        // update current ecuted process by process from the ready queue
        currentExcuted = readyQueue.poll();
      }      
    }
}
