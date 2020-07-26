package SchedulingAlgorithm;

class Algorithm {
    private Node pre = null;
    private Node prev = null;
    private Node min = null;
    private int num = 0;
    private int start = 0;
    private double nums = 0.0;
    private int count = 0;
    private static Create create = new Create();

    private static Node CreateHead(Node head) {
        Node head2 = null;
        Node cur = head;
        while (cur != null) {
            head2 = create.createNode(head2, cur.name, cur.priority, cur.runtime, cur.arrivaltime, cur.starttime, cur.endtime, cur.turntime, cur.dturntime);
            cur = cur.nextnode;
        }
        return head2;
    }

    private void endFun() {
        System.out.println("平均周转时间:" + (double) this.num / this.count + "平均带权周转时间:" + this.nums / this.count);
        this.start = 0;
        this.num = 0;
        this.nums = 0;
        this.count = 0;
    }

    private static Node toolMethod(Node min, Node prev, int start, Node head) {
        min.starttime = start;
        min.endtime = min.starttime + min.runtime;
        min.turntime = min.endtime - min.arrivaltime;
        min.dturntime = (double) min.turntime / (double) min.runtime;
        System.out.println("名字:" + min.name + "、优先级:" + min.priority + "、运行时间:" + min.runtime + "、到达时间" + min.arrivaltime + "、开始时间:" + min.starttime + "、结束时间:" + min.endtime + "、周转时间:" + min.turntime + "、带权周转时间:" + min.dturntime);
        if (prev == null) {
            if (min.nextnode == null) {
                return null;
            }
            return min.nextnode;
        } else {
            prev.nextnode = min.nextnode;
        }
        return head;
    }

    private static Node findMin(Node head) {
        Node cur = head;
        Node real = null;
        int mintime = cur.arrivaltime;
        while (cur != null) {
            if (cur.arrivaltime <= mintime) {
                mintime = cur.arrivaltime;
                real = cur;
            }
            cur = cur.nextnode;
        }
        return real;
    }

    //先来先服务算法
    public void Fcfs(Node head) {
        Node head2 = CreateHead(head);
        while (head2 != null) {
            min = null;
            pre = null;
            Node cur = head2;
            int mintime = cur.arrivaltime;
            while (cur != null) {
                if (cur.arrivaltime <= mintime) {
                    mintime = cur.arrivaltime;
                    prev = pre;
                    min = cur;
                }
                pre = cur;
                cur = cur.nextnode;
            }
            if (min.arrivaltime > start) {
                start = min.arrivaltime;
            }
            head2 = toolMethod(min, prev, start, head2);
            start = start + min.runtime;
            num += min.turntime;
            nums += min.dturntime;
            count++;
        }
        this.endFun();
    }

    //高优先级优先算法
    public void Priority(Node head) {
        Node head2 = CreateHead(head);
        while (head2 != null) {
            min = null;
            pre = null;
            Node cur = head2;
            int mintime = 0;
            while (cur != null) {
                if (cur.priority >= mintime && cur.arrivaltime <= start) {
                    mintime = cur.priority;
                    prev = pre;
                    min = cur;
                }
                pre = cur;
                cur = cur.nextnode;
            }
            if (min == null) {
                min = findMin(head2);
            }
            if (min.arrivaltime > start) {
                start = min.arrivaltime;
            }
            head2 = toolMethod(min, prev, start, head2);
            start = start + min.runtime;
            num += min.turntime;
            nums += min.dturntime;
            count++;
        }
        this.endFun();
    }

    //短作业优先算法
    public void ShortProcess(Node head) {
        Node head2 = CreateHead(head);
        while (head2 != null) {
            min = null;
            pre = null;
            Node cur = head2;
            int mintime = 1000;
            while (cur != null) {
                if (cur.runtime <= mintime && cur.arrivaltime <= start) {
                    mintime = cur.runtime;
                    prev = pre;
                    min = cur;
                }
                pre = cur;
                cur = cur.nextnode;
            }
            if (min == null) {
                min = findMin(head2);
            }
            if (min.arrivaltime > start) {
                start = min.arrivaltime;
            }
            head2 = toolMethod(min, prev, start, head2);
            start = start + min.runtime;
            num += min.turntime;
            nums += min.dturntime;
            count++;
        }
        this.endFun();
    }

    private static double resRatio(Node node, int start) {
        int waittime = start - node.arrivaltime;
        return (double) waittime / node.runtime;
    }

    //高响应比优先算法
    public void Hreponse(Node head) {
        Node head2 = CreateHead(head);
        while (head2 != null) {
            min = null;
            pre = null;
            Node cur = head2;
            double mintime = 0.0;
            while (cur != null) {
                double resratio = resRatio(cur, start);
                if (resratio >= mintime && cur.arrivaltime <= start) {
                    mintime = resratio;
                    prev = pre;
                    min = cur;
                }
                pre = cur;
                cur = cur.nextnode;
            }
            if (min == null) {
                min = findMin(head2);
            }
            if (min.arrivaltime > start) {
                start = min.arrivaltime;
            }
            head2 = toolMethod(min, prev, start, head2);
            start = start + min.runtime;
            num += min.turntime;
            nums += min.dturntime;
            count++;
        }
        this.endFun();
    }

    public static Node QueueHead(Node head) {
        Node cur = head;
        Node nodemin = null;
        Node head2 = null;
        int min = 1000;
        int count = 0;
        while (cur != null) {
            count++;
            cur = cur.nextnode;
        }
        while (count != 0) {
            min = 1000;
            cur = head;
            while (cur != null) {
                if (cur.arrivaltime < min && cur.statu == 0) {
                    nodemin = cur;
                    min = cur.arrivaltime;
                }
                cur = cur.nextnode;
            }
            nodemin.statu = 1;
            count--;
            head2 = create.createNode(head2, nodemin.name, nodemin.priority, nodemin.runtime, nodemin.arrivaltime, nodemin.starttime, nodemin.endtime, nodemin.turntime, nodemin.dturntime);
        }
        return head2;
    }

    public static void insert(Node head, Node min) {
        Node cur = head;
        Node pre = null;
        while (cur != null) {
            if (cur.arrivaltime > min.newarrival) {
                pre.nextnode = min;
                min.nextnode = cur;
                return;
            }
            pre = cur;
            cur = cur.nextnode;
        }
        pre.nextnode = min;
        min.nextnode = cur;
    }

    //时间片轮转算法
    public void Roundrobin(Node head, int Rtime) {
        Node newnode = null;
        Node head2 = QueueHead(head);
        create.check(head2);
        System.out.println(head2.newruntime);
        System.out.println(head2.newarrival);
        while (head2 != null) {
            min = head2;
            if (min.arrivaltime > start) {
                start = min.arrivaltime;
            }
            if (min.newruntime > Rtime) {
                min.newruntime -= Rtime;
                start += Rtime;
                min.newarrival += Rtime;
                newnode = new Node(min.name, min.priority, min.runtime, min.arrivaltime, min.starttime, min.endtime, min.turntime, min.dturntime);
                newnode.newarrival = min.newarrival;
                newnode.newruntime = min.newruntime;
                insert(head2, newnode);
                head2 = min.nextnode;
            } else {
                start += min.newruntime;
                min.endtime = start;
                min.turntime = min.endtime - min.arrivaltime;
                min.dturntime = (double) min.turntime / (double) min.runtime;
                head2 = min.nextnode;
                num += min.turntime;
                nums += min.dturntime;
                count++;
                System.out.println("名字:" + min.name + "、优先级:" + min.priority + "、运行时间:" + min.runtime + "、到达时间" + min.arrivaltime + "、开始时间:" + min.starttime + "、结束时间:" + min.endtime + "、周转时间:" + min.turntime + "、带权周转时间:" + min.dturntime);
            }
        }
        this.endFun();
    }
}