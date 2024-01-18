# Collaborators

- [Abdelrhman Mostafa](https://github.com/3bde1r7man)
- [Marwan Tarik](https://github.com/MarwanTarik)
- [Ahmed Hanfy](https://github.com/ahanfybekheet)
# CPU Scheduling Algorithms

| Algorithm                                    | Description                                                                                                                     |
|----------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|
| Non-Preemptive Shortest Job First (SJF)      | - Uses context switching.<br> - Selects the process with the shortest burst time first.                                        |
| Shortest-Remaining-Time-First (SRTF)         | - Solves the starvation problem.<br> - Preemptive version of SJF, selects the process with the shortest remaining burst time.   |
| Non-Preemptive Priority Scheduling            | - Solves the starvation problem.<br> - Assigns priority to each process and schedules the one with the highest priority.       |
| AG Scheduling                                | - Combines Round Robin (RR) with a new AG factor.<br> - AG factor considers priority, random function, arrival time, and burst time.|
|                                              |   - **Round Robin (RR):** Fair scheduling algorithm with equal time quantum.                                                   |
|                                              |   - **AG Factor Equation:** AG-Factor = (Priority or 10 or random_function(0,20)) + Arrival Time + Burst Time                 |

