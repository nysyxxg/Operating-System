package Process;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于生成随机的进程列表，并测试.
 * Process.java是测试类，用于生成进程列表和测试两种不同的调度策略。
 * 常用的调度算法：
 * 1：先来来服务算法
 * 2：短作业优先(SJF)调度算法
 * 3.优先级调度算法
 * 4：高响应比优先调度算法
 * 5： 时间片开始轮转
 * 6： 多级反馈队列调度算法
 *
 * 评价调度算法的指标：
 * 1：响应时间：提交任务开始,直到产生结果----评价分时系统的性能
 * 2：周转时间：作业提交给系统开始，到作业完成为止--评价批处理系统的性能
 * 3：截止时间 ：必须开始的时间，和结束的时间，---评价实时系统的性能
 * 3：吞吐量
 * 4：处理机利用率
 * 5：资源平衡：CPU 和 IO的平衡
 * 6: 公平性
 * 7：优先级
 */
public class Process {

    public static List<double[]> task_info = new ArrayList<>();//进程列表
    public static int task_num = 8;//进程数

    //初始化进程列表
    public static void init_task() {
        for (int i = 0; i < task_num; i++) {
            double[] t = new double[4];
            t[0] = i;//进程号
            t[1] = 0;//到达时间
            t[2] = 0;//响应比
            t[3] = (int) (Math.random() * 100) % 20 + 1;//需要运行时间
            task_info.add(t);
        }
    }

    public static void main(String arg[]) {
        Process.init_task();//初始化进程列表

        System.out.println("\n\n最高优先级算法开始运行:");
        HPF.init_task(task_info, task_num);
        HPF.HRRN();//最高优先级

        System.out.println("\n\n时间片开始轮转：");
        RR.init_task(task_info, task_num);
        RR.CircleTime();//时间片轮转

    }
}
