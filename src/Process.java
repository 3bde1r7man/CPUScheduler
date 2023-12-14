
public class Process {
    public String name;
    public int arrivalTime;
    public int burstTime;
    public int priority;
    public int remainingTime;
    public int waitingTime;
    public int turnaroundTime;
    public int timeQuantum;
    public int AGFactor;
    public Process(String name, int arrivalTime, int burstTime, int priority, int timeQuantum){
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.timeQuantum = timeQuantum;
        this.AGFactor = 0;
    }
}
