import java.util.Vector;

public class SJFScheduler {
    int contextSwitching;
    Vector<Process> processes = new Vector<Process>();
    public SJFScheduler(Vector<Process> processes, int contextSwitching){
        this.contextSwitching = contextSwitching;
        this.processes = processes;
    }

    public void schedule(){
        int time = 0;
        int waitingTime = 0;
        int processesSize = processes.size();
        Vector<Process> processesv = new Vector<Process>(processes);

        processesv.sort((p1,p2)->p1.arrivalTime - p2.arrivalTime);
        Process process = processesv.get(0);
        processesv.remove(0);
        System.out.println("Process "+process.name+" is running at time: " + time );
        time += process.burstTime;
        System.out.println("Process "+process.name+" is finished at time: " + time );
        while (processesv.size() > 0) {
            int tempBrustTime = 999999999;
            int index = 0;
            for (int i = 0; i < processesv.size(); i++) {
                if(processesv.get(i).arrivalTime <= time && processesv.get(i).burstTime < tempBrustTime){
                    tempBrustTime = processesv.get(i).burstTime;
                    index = i;
                }
            }
            time += contextSwitching;
            process = processesv.get(index);
            process.waitingTime = time - process.arrivalTime;
            waitingTime += process.waitingTime;
            System.out.println("Process "+process.name+" is running at time: " + time );
            time += process.burstTime;
            System.out.println("Process "+process.name+" is finished at time: " + time );
            processesv.remove(index);
        }
        for (Process p : processes) {
            System.out.println("SJF Process "+ p.name +" waiting time: " + p.waitingTime);
        }
        System.out.println("SJF Average Waiting Time: " + (float)waitingTime/(float)processesSize);
    }

}
