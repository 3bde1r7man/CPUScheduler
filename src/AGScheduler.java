import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class AGScheduler {
    
    int timeQuantum;
    Vector<Process> processes;
    public AGScheduler(Vector<Process> processes, int timeQuantum){
        this.timeQuantum = timeQuantum;
        this.processes = processes;
        for(Process process : processes){
            int AGFactor = RandomNumber(0, 20);
            if(AGFactor < 10){
                process.AGFactor = AGFactor + process.arrivalTime + process.burstTime;
            }else if(AGFactor > 10){
                process.AGFactor = 10 + process.arrivalTime + process.burstTime;
            }else{
                process.AGFactor = process.priority + process.arrivalTime + process.burstTime;
            }
        }
    }

    public void schedule(){
        int time = 0;
        Queue<Process> readyProcesses = new LinkedList<Process>();
        int QuantumMean = 0;
        
    }

    private int RandomNumber(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
