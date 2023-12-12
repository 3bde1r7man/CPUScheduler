import java.util.List;
import java.util.Vector;

public class SRTFScheduler {
    Vector<Process> processes = new Vector<Process>();
    public SRTFScheduler(Vector<Process> processes){
        this.processes = processes;
    }
    // implement the schedule method for Shortest Remaining Time First Scheduling Algorithm 
    public void schedule(){
        int time = 0;
        int waitingTime = 0;
        int processesSize = processes.size();
        int completedProcesses = 0;
        while (completedProcesses < processes.size()) {
            List<Process> readyProcesses = getReadyProcesses(time);
            if (!readyProcesses.isEmpty()) {
                // Sort the ready processes by remaining time
                readyProcesses.sort((p1, p2) -> p1.remainingTime - p2.remainingTime);
                
                // Execute the process with the shortest remaining time
                Process currentProcess = readyProcesses.get(0);
                executeProcess(currentProcess, time);
                time++;
                //waitingTime += time - currentProcess.arrivalTime;
                // Update completedProcesses count
                if (currentProcess.remainingTime == 0) {
                    completedProcesses++;
                }
                for (Process process : processes) {
                    if (process != currentProcess && process.remainingTime > 0 && process.arrivalTime <= time - 1) {
                        process.waitingTime++;
                    }
                }
            }
        }
        for (Process process : processes) {
            System.out.println("SRTF Process "+ process.name +" waiting time: " + process.waitingTime);
            waitingTime += process.waitingTime;
        }
        System.out.println("SRTF Average Waiting Time: " + (float)waitingTime/(float)processesSize);
    }
    private void executeProcess(Process process, int currentTime) {
        System.out.println("Time " + currentTime + ": Executing process " + process.name);
        process.remainingTime--;
    }
    private Vector<Process> getReadyProcesses(int currentTime) {
        Vector<Process> readyProcesses = new Vector<>();
        for (Process process : processes) {
            if (process.arrivalTime <= currentTime && process.remainingTime > 0) {
                readyProcesses.add(process);
            }
        }
        return readyProcesses;
    }
    
}

