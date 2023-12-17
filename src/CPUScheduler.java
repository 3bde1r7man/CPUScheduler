import java.util.Scanner;
import java.util.Vector;

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
        
        while (true) {
            Vector<Process> processesAlgo = new Vector<Process>();
            for(Process process : processes){
                processesAlgo.add(new Process(process.name, process.arrivalTime, process.burstTime, process.priority, tq));
            }
            System.out.println("Choose between the following scheduling algorithms: ");
            System.out.println("1. SJF");
            System.out.println("2. SRTF");
            System.out.println("3. Priority");
            System.out.println("4. AG");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                    SJFScheduler sjf = new SJFScheduler(processesAlgo,contextSwitching);
                    sjf.schedule();
                    break;
                case 2:
                    SRTFScheduler srtf = new SRTFScheduler(processesAlgo);
                    srtf.schedule();
                    break;
                case 3:
                    PriorityScheduler priority = new PriorityScheduler(processesAlgo);
                    priority.schedule();
                    break;
                case 4:
                    AGScheduler ag = new AGScheduler(processesAlgo, tq);
                    ag.schedule();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
        
        
    }
}

