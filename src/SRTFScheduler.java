import java.util.List;
import java.util.Vector;

public class SRTFScheduler {
    Vector<Process> processes;

    public SRTFScheduler(Vector<Process> processes) {
        this.processes = processes;
    }

    // implement the schedule method for Shortest Remaining Time First Scheduling Algorithm
    public void schedule() {
        int time = 0;
        int waitingTime = 0;
        int processesSize = processes.size();
        int completedProcesses = 0;
        int starvingTime = 15;
        while (completedProcesses < processes.size()) {
            List<Process> readyProcesses = getReadyProcesses(time);
            for (Process process : readyProcesses) {
                if (process.waitingTime >= starvingTime) {
                    time = executeStravingProcess(readyProcesses, process, time);
                    completedProcesses++;
                    process.turnaroundTime = time - process.arrivalTime;
                    break;
                }
            }
            if (!readyProcesses.isEmpty()) {
                // Sort the ready processes by remaining time
                readyProcesses.sort((p1, p2) -> p1.remainingTime - p2.remainingTime);
                // Check if there is a starving process
                
                // Execute the process with the shortest remaining time
                Process currentProcess = readyProcesses.get(0);
                executeProcess(currentProcess, time);
                time++;

                if (currentProcess.remainingTime == 0) {
                    currentProcess.turnaroundTime = time - currentProcess.arrivalTime;
                    completedProcesses++;
                }

                for (Process process : processes) {
                    if (process != currentProcess && process.remainingTime > 0 && process.arrivalTime <= time - 1) {
                        process.waitingTime++;
                    }
                }
            }
        }
        System.out.println(String.format("%-10s%-20s%-20s", "Process", "Turnaround Time", "Waiting Time"));
        for (Process process : processes) {
            System.out.println(String.format("%-10s%-20s%-20s", process.name, process.turnaroundTime, process.waitingTime));
            waitingTime += process.waitingTime;
        }

        int totalTurnaroundTime = 0;
        for (Process process : processes) {
            totalTurnaroundTime += process.turnaroundTime;
        }
        System.out.println("SRTF Average Waiting Time: " + (float) waitingTime / (float) processesSize);
        System.out.println("SRTF Average Turnaround Time: " + (float) totalTurnaroundTime / (float) processesSize);
    }

    private void executeProcess(Process process, int currentTime) {
        System.out.println("Time " + currentTime + ": Executing process " + process.name);
        process.remainingTime--;
    }

    private int executeStravingProcess(List<Process> ready,Process process, int currentTime) {
        while(process.remainingTime > 0){
            System.out.println("Time " + currentTime + ": Executing process " + process.name);
            process.remainingTime--;
            currentTime++;
            for (Process p : ready) {
                if (p != process && p.remainingTime > 0) {
                    p.waitingTime++;
                }
            }
        }
        
        for (Process p : ready) {
            if (p == process) {
                ready.remove(p);
                break;
            }
        }
        return currentTime;
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
