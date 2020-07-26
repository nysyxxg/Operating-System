package SchedulingAlgorithm;

class Node{
    String name;
    int priority;
    int runtime;
    int arrivaltime;
    int starttime;
    int endtime;
    int turntime;  //周转时间
    double dturntime;  //带权周转时间
    Node nextnode;
    int statu;
    int newarrival;
    int newruntime;

    public Node(String name,int priority,int runtime,int arrivaltime, int starttime, int endtime, int turntime, double dturntime){
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