import java.util.Vector;

public class PriorityScheduler {
    Vector<Process> processes = new Vector<Process>();
    public PriorityScheduler(Vector<Process> processes){
        this.processes = processes;
    }
    public void schedule(){
        int time = 0;
        int waitingTime = 0;
        int processesSize = processes.size();
        int completedProcesses = 0;
        while (completedProcesses < processes.size()) {
            Vector<Process> readyProcesses = getReadyProcesses(time);
            if (!readyProcesses.isEmpty()) {
                // Sort the ready processes by priority
                readyProcesses.sort((p1, p2) -> p1.priority - p2.priority);
                
                // Execute the process with the highest priority
                Process currentProcess = readyProcesses.get(0);
                executeProcess(currentProcess, time);
                time++;
                
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
            System.out.println("Priority Process "+ process.name +" waiting time: " + process.waitingTime);
            waitingTime += process.waitingTime;
        }
        System.out.println("Priority Average Waiting Time: " + (float)waitingTime/(float)processesSize);
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