import java.util.*;

class CPUScheduler{

    public static void main(String[] args){
        int contextSwitching;
        Vector<Process> processes = new Vector<Process>();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of processes: ");
        int n = sc.nextInt();
        System.out.println("Enter the Round Robin Time Quantum: ");
        int tq = sc.nextInt();
        System.out.println("Enter the context switching: ");
        contextSwitching = sc.nextInt();
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
            Process process = new Process(name,at,bt,p, tq);
            processes.add(process);
        }
        Vector<Process> processesSJF = new Vector<Process>();
        Vector<Process> processesSRTF = new Vector<Process>();
        Vector<Process> processesPriority = new Vector<Process>();

        for (Process process : processes) {
            processesSJF.add(new Process(process.name,process.arrivalTime,process.burstTime,process.priority,process.timeQuantum));
            processesSRTF.add(new Process(process.name,process.arrivalTime,process.burstTime,process.priority,process.timeQuantum));
            processesPriority.add(new Process(process.name,process.arrivalTime,process.burstTime,process.priority,process.timeQuantum));
        }
        SJFScheduler sjf = new SJFScheduler(processesSJF,contextSwitching);
        sjf.schedule();
        SRTFScheduler srtf = new SRTFScheduler(processesSRTF);
        srtf.schedule();
        PriorityScheduler priority = new PriorityScheduler(processesPriority);
        priority.schedule();
    }
}