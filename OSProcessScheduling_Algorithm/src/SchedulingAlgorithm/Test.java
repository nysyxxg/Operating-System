package SchedulingAlgorithm;

import java.util.Scanner;

public class Test {
    public static void dispatchMenu(Node head) {

        Scanner sc = new Scanner(System.in);
        Algorithm al = new Algorithm();
        int count = 1;
        while (count == 1) {
            System.out.println("请选择调度算法:");
            System.out.println("1.先来先服务算法");
            System.out.println("2.短作业优先算法");
            System.out.println("3.高优先级优先算法");
            System.out.println("4.高响应比优先算法");
            System.out.println("5.时间片轮转算法");
            System.out.println("0.退出");
            int num = sc.nextInt();
            switch (num) {
                case 1:
                    al.Fcfs(head);
                    break;
                case 2:
                    al.ShortProcess(head);
                    break;
                case 3:
                    al.Priority(head);
                    break;
                case 4:
                    al.Hreponse(head);
                    break;
                case 5:
                    al.Roundrobin(head, 1);
                    break;
                case 0:
                    count = 0;
                    break;
                default:
                    System.out.println("输入错误请重新输入");
            }
        }
    }

    public static void mainMenu() {
        Create create = new Create();
        Node head = null;
        Scanner sc = new Scanner(System.in);
        int count1 = 1;
        while (count1 == 1) {
            System.out.println("请选择你需要的服务:");
            System.out.println("1.添加新进程");
            System.out.println("2.使用调度算法进行排序");
            System.out.println("3.查看当前进程信息");
            System.out.println("0.退出");
            int num = sc.nextInt();
            switch (num) {
                case 1:
                    String name;
                    int priority;
                    int runtime;
                    int arrivaltime;
                    System.out.println("请输入进程名字:");
                    name = sc.next();
                    System.out.println("请输入进程优先级:");
                    priority = sc.nextInt();
                    System.out.println("请输入进行运行时间:");
                    runtime = sc.nextInt();
                    System.out.println("请输入进程到达时间:");
                    arrivaltime = sc.nextInt();
                    head = create.createNode(head, name, priority, runtime, arrivaltime, 0, 0, 0, 0);
                    break;
                case 2:
                    Test.dispatchMenu(head);
                    break;
                case 3:
                    create.check(head);
                    break;
                case 0:
                    count1 = 0;
                    break;
                default:
                    System.out.println("输入错误请重新输入");
            }
        }
    }

    public static void main(String[] args) {
        Test.mainMenu();
    }
}

