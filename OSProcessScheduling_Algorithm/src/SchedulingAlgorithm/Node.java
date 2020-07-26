package SchedulingAlgorithm;

public class Node {// 任务节点
    String name;  // 任务名称
    int priority; // 任务优先级
    int runtime; // 运行时间
    int arrivaltime;
    int starttime; // 开始时间
    int endtime;  //结束时间
    int turntime;  //周转时间
    double dturntime;  //带权周转时间
    Node nextnode;
    int statu;// 运行状态
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