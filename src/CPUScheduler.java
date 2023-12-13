import java.util.*;

class CPUScheduler{
    int contextSwitching;
    public static Vector<Process> processes = new Vector<Process>();
    

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of processes: ");
        int n = sc.nextInt();
        System.out.println("Enter the Round Robin Time Quantum: ");
        int tq = sc.nextInt();
        System.out.println("Enter the context switching: ");
        int contextSwitching = sc.nextInt();
        for(int i=0;i<n;i++){
            System.out.println("Process Number "+(i+1)+": ");
            System.out.println("Enter Process Name: ");
            String name = sc.next();
            System.out.println("Enter Arrival Time: ");
            int at = sc.nextInt();
            System.out.println("Enter Burst Time: ");
            int bt = sc.nextInt();
            System.out.println("Enter Priority: ");
            int p = sc.nextInt();
            Process process = new Process(name,at,bt,p);
            processes.add(process);
        }
        // SJFScheduler sjf = new SJFScheduler(processes,contextSwitching);
        // sjf.schedule();
        // SRTFScheduler srtf = new SRTFScheduler(processes);
        // srtf.schedule();
        PriorityScheduler priority = new PriorityScheduler(processes);
        priority.schedule();
    }
}