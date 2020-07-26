package SchedulingAlgorithm;

public class Node {// 任务进程节点
    String name;  // 任务进程名称
    int priority; // 任务进程优先级
    int runtime; // 任务进程运行时间--就是服务时间--serviceTime  例如 runtime = 100
    int arrivaltime; // 任务进程到达时间 ，例如   arrivaltime = 3
    int starttime; // 开始时间            例如   starttime = 102
    int endtime;  //结束时间 = starttime + runtime = 102 + 100 = 202
    int turntime;  //周转时间 = endtime - arrivaltime = 202 - 3 = 199
    double dturntime;  //带权周转时间 = turntime / runtime = 199 / 100 = 1.99

    Node nextnode;// 下一个任务进程节点
    int statu;// 任务进程运行状态
    int newarrival;
    int newruntime;

    public Node(String name, int priority, int runtime, int arrivaltime, int starttime, int endtime, int turntime, double dturntime) {
        this.name = name;
        this.priority = priority;
        this.runtime = runtime;
        this.arrivaltime = arrivaltime;
        this.nextnode = null;
        this.starttime = starttime;
        this.endtime = endtime;
        this.turntime = turntime;
        this.dturntime = dturntime;
        this.newarrival = arrivaltime;
        this.newruntime = runtime;
    }

}