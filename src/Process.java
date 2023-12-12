
public class Process {
    public String name;
    public int arrivalTime;
    public int burstTime;
    public int priority;
    public int remainingTime;
    public int waitingTime;
    public Process(String name, int arrivalTime, int burstTime, int priority){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.waitingTime = 0;
    }
}
