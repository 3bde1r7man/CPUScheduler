import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class AGScheduler {
    
    int timeQuantum;
    int completedProcesses = 0;
    Vector<Process> processes;
    HashMap<String, Vector<Integer>> quntaums = new HashMap<String, Vector<Integer>>();

    public AGScheduler(Vector<Process> processes, int timeQuantum){
        this.timeQuantum = timeQuantum;
        this.processes = processes;
        for(Process process : processes){
            quntaums.put(process.name, new Vector<Integer>());
            quntaums.get(process.name).add(process.timeQuantum);
            int AGFactor = RandomNumber(0, 20);
            
            if(AGFactor < 10){
                process.AGFactor = AGFactor + process.arrivalTime + process.burstTime;
            }else if(AGFactor > 10){
                process.AGFactor = 10 + process.arrivalTime + process.burstTime;
            }else{
                process.AGFactor = process.priority + process.arrivalTime + process.burstTime;
            }
            System.out.println(process.AGFactor); 
        }
    }

    public void schedule(){
        int time = 0;
        List<Process> readyProcesses = new LinkedList<Process>();

        int processesSize = processes.size();
        while (completedProcesses < processesSize) {
            List<Process> arraivedProcesses = getReadyProcesses(time);
            if(!arraivedProcesses.isEmpty()){
                arraivedProcesses.sort((p1, p2) -> p1.AGFactor - p2.AGFactor);
                Process currentProcess = arraivedProcesses.get(0);
                int processTimeQuantum = currentProcess.timeQuantum;
                time = nonPreemptive(arraivedProcesses, currentProcess , time);
                if(currentProcess.remainingTime == 0){
                    for(int i = 0; i < readyProcesses.size(); i++){
                        if(readyProcesses.get(i) == currentProcess){
                            readyProcesses.remove(i);
                            i--;
                        }
                    }
                }
                //-----------------------------------------------------------------------
                if(!readyProcesses.isEmpty() && currentProcess.remainingTime == 0){
                    Process process = readyProcesses.get(0);
                    int processQuantum = process.timeQuantum;
                    time = nonPreemptive(arraivedProcesses, process, time);
                    readyProcesses.remove(0);
                    currentProcess = process;
                    processTimeQuantum = processQuantum;
                }
                int tq = currentProcess.timeQuantum;
                for(int i = 0; i < tq; i++){
                    arraivedProcesses = getReadyProcesses(time);
                    arraivedProcesses.sort((p1, p2) -> p1.AGFactor - p2.AGFactor);
                    if(arraivedProcesses.get(0) != currentProcess){
                        readyProcesses.add(currentProcess);
                        currentProcess.timeQuantum += processTimeQuantum;
                        quntaums.get(currentProcess.name).add(currentProcess.timeQuantum);
                        break;
                    }else if(arraivedProcesses.get(0) == currentProcess){
                        preemptive(currentProcess, time);
                        time++;
                        if(currentProcess.remainingTime == 0){
                            for(int j = 0; j < readyProcesses.size(); j++){
                                if(readyProcesses.get(j) == currentProcess){
                                    readyProcesses.remove(j);
                                    j--;
                                }
                            }
                            break;
                        }
                    }
                }

                if(currentProcess.timeQuantum == 0 && currentProcess.remainingTime > 0){
                    currentProcess.timeQuantum = processTimeQuantum;
                    currentProcess.timeQuantum += (int) Math.ceil(0.1 * (double)calcQuantumMean(arraivedProcesses));
                    quntaums.get(currentProcess.name).add(currentProcess.timeQuantum);
                    readyProcesses.add(currentProcess);
                    Process process = readyProcesses.get(0);
                    int processQuantum = process.timeQuantum;
                    time = nonPreemptive(arraivedProcesses, process, time);
                    readyProcesses.remove(0);
                    if(process.remainingTime > 0){
                        process.timeQuantum += processQuantum;
                        quntaums.get(process.name).add(process.timeQuantum);
                        readyProcesses.add(process);
                    }else if(process.remainingTime == 0){
                        for(int j = 0; j < readyProcesses.size(); j++){
                            if(readyProcesses.get(j) == process){
                                readyProcesses.remove(j);
                                j--;
                            }
                        }
                    }
                }
                
            }else{
                time++;
            }
            
        }
        System.out.println(String.format("%-10s%-20s%-20s", "Process", "Turnaround Time", "Waiting Time"));
            int totalTurnaroundTime = 0;
            int waitingTime = 0;
            for (Process process : processes) {
                System.out.println(String.format("%-10s%-20s%-20s", process.name, process.turnaroundTime, process.waitingTime));
                waitingTime += process.waitingTime;
                totalTurnaroundTime += process.turnaroundTime;
            }
            for(Process process : processes){
                System.out.println("Process " + process.name + " Quantums: " + quntaums.get(process.name));
                System.out.println("process " + process.name + " AGFactor: " + process.AGFactor);
            }

            System.out.println("Priority Average Waiting Time: " + (float)waitingTime/(float)processesSize);
            System.out.println("Priority Average Turnaround Time: " + (float)totalTurnaroundTime/(float)processesSize);

    }

    private int nonPreemptive(List<Process> arraived ,Process process, int currentTime) {
        int halfOfQuantum = (int) Math.ceil((double)process.timeQuantum / 2);
        for (int i = 0; i < halfOfQuantum; i++) {
            System.out.println("Time " + currentTime + ": Executing process " + process.name);
            process.remainingTime--;
            process.timeQuantum--;
            currentTime++;
            if(process.remainingTime == 0){
                completedProcesses++;
                for (Process p : arraived) {
                    if (p == process) {
                        arraived.remove(p);
                        process.timeQuantum = 0;
                        quntaums.get(process.name).add(process.timeQuantum);
                        process.turnaroundTime = currentTime - process.arrivalTime;
                        process.waitingTime = process.turnaroundTime - process.burstTime;
                        break;
                    }
                }
                break;
            }
        }
        return currentTime;
    }

    private void preemptive(Process process, int currentTime) {
        System.out.println("Time " + currentTime + ": Executing process " + process.name);
        process.remainingTime--;
        process.timeQuantum--;
        if(process.remainingTime == 0){
            completedProcesses++;
            process.turnaroundTime = (currentTime + 1) - process.arrivalTime;
            process.waitingTime = process.turnaroundTime - process.burstTime;
            process.timeQuantum = 0;
            quntaums.get(process.name).add(process.timeQuantum);
        }
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

    private int calcQuantumMean(List<Process> readyProcesses) {
        int quantumMean = 0;
        for(Process process : readyProcesses){
            quantumMean += process.timeQuantum;
        }
        return quantumMean / readyProcesses.size();
    }

    private int RandomNumber(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
