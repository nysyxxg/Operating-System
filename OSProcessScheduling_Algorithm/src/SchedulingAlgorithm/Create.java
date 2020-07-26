package SchedulingAlgorithm;

public class Create {

    public Node createNode(Node head, String name, int priority, int runtime,
                           int arrivaltime, int starttime, int endtime, int turntime, double dturntime) {

        if (head == null) {
            Node node = new Node(name, priority, runtime, arrivaltime, starttime, endtime, turntime, dturntime);
            head = node;
            return head;
        }
        Node cur = head;
        while (cur.nextnode != null) {
            cur = cur.nextnode;
        }
        Node node = new Node(name, priority, runtime, arrivaltime, starttime, endtime, turntime, dturntime);
        cur.nextnode = node;
        return head;
    }

    public void check(Node head) {
        if (head == null) {
            System.out.println("当前没有节点信息");
            return;
        }
        Node cur = head;
        while (cur != null) {
            System.out.println("名字:" + cur.name + "、优先级:" + cur.priority + "、运行时间:" + cur.runtime + "、到达时间" + cur.arrivaltime);
            cur = cur.nextnode;
        }
    }
}