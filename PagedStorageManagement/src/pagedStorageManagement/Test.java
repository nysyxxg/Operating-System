package pagedStorageManagement;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		Scanner a = new Scanner(System.in);
		int i, number = -10, k1, k2, result;
		int k3 = 0;//当前存储的内存地址
		int t;//页表中访问次数最小的索引
		int b[] = new int[10];//内存中存储的数据
		String ch;
		int sLength, pLength, tLength, data;
		System.out.println("请输入外存大小：");
		sLength = a.nextInt();
		System.out.println("请输入页表大小：");
		pLength = a.nextInt();
		System.out.print("请输入快表大小:");
		tLength = a.nextInt();
		//定义页表,快表，外存
		InnerTable pageinnerTable = new InnerTable(pLength);//页表
		ExternalTable s[] = new ExternalTable[sLength];//外表
		TLBuffer TLB = new TLBuffer(tLength);//快表
		System.out.println("产生一个随机序列作为外存数据！");
		//录入外存地址和数据
		for (i = 0; i < sLength; i++) {
			data = (int) (100 * Math.random());
			System.out.print(data + "\t");
			s[i] = new ExternalTable(i, data);
		}
		System.out.println("\n外存设置成功");
		//请求页表
		do {
			//TLB.printKinnerTable();//打印当前快表的情况
			//pageinnerTable.printPageinnerTable();//打印当前页表的情况
			System.out.println("请输入一个页面的页号(0-19):");
			k1 = a.nextInt();
			if (k1 >= 20 || k1 < 0) {
				System.out.println("输入数据有错，将退出程序!");
				System.exit(0);
			}
			//检测快表,快表存储当前的页表项，即当快表满时采用最近最久未被使用算法置换快表
			System.out.println("进入快表检测");
			if (TLB.getCurrent() > 0) {
				number = TLB.searchPage(k1);
				if (number != -1 && number != -2) {
					result = b[TLB.quickTable[number].getPhysicsNumber()];
					System.out.println("在快表中找到，结果为：" + result);
					//找出该页号在页表中的位置并修改访问字段
					number = TLB.quickTable[number].getCRTAddress();
					pageinnerTable.innerTable[number].setVisitCount(pageinnerTable.innerTable[number].getVisitCount() + 1);
				}
			}
			if (TLB.getCurrent() <= 0 || number == -1) {
				System.out.println("在快表中找不到！" + "进入内存检测：");
				//在快表中找不到,去内存区的页表找
				if (pageinnerTable.current > 0) {
					number = pageinnerTable.searchPage(k1);//页号k1所在的下标
					if (number != -1 && number != -2) {
						result = b[pageinnerTable.innerTable[number].getPhysicsNumber()];
						System.out.println("在页表中找到,结果为:" + result);
						//修改访问字段和状态位
						pageinnerTable.innerTable[number].setVisitCount(pageinnerTable.innerTable[number].getVisitCount() + 1);
						//修改快表
						TLB.changeKShell(pageinnerTable, number);

					}
				}
				if (pageinnerTable.current <= 0 || number == -1) {
					System.out.println("在内存中找不到！");
					System.out.println("从外存中调入内存：");
					//在页表找不到，去外存区找
					for (i = 0; i < sLength; i++) {
						if (k1 == s[i].getPageNumber())//在外存找到了缺页
						{
							k2 = pageinnerTable.isOver();
							if (k2 == 1)//内存已满
							{
								t = pageinnerTable.minVisitCount();
								System.out.println("内存已满!即将调出页号 " + pageinnerTable.innerTable[t].getPageNumber());
							} else {
								t = pageinnerTable.current;
								pageinnerTable.setCurrent(pageinnerTable.getCurrent() + 1);
							}
							//判断是否修改了内存的数据
							if (pageinnerTable.isChange(t) == 1) {
								s[pageinnerTable.innerTable[t].getCRTAddress()].setSts(b[pageinnerTable.innerTable[t].getPhysicsNumber()]);
							}
							//调入内存
							pageinnerTable.innerTable[t].setPageNumber(k1);
							if (k2 == 1) {
								b[pageinnerTable.innerTable[t].getPhysicsNumber()] = s[i].getSts();
							} else {
								pageinnerTable.innerTable[t].setPhysicsNumber(k3);//未满则设置物理块号,满了只改变其他5个字段
								b[k3] = s[i].getSts();
								k3++;//物理块号
							}
							pageinnerTable.innerTable[t].setState(true);
							pageinnerTable.innerTable[t].setVisitCount(1);
							pageinnerTable.innerTable[t].setChange(false);
							pageinnerTable.innerTable[t].setCRTAddress(i);
							System.out.println("调入内存成功！");
							//修改快表
							TLB.changeKShell(pageinnerTable, t);
							System.out.println("修改快表成功！");
							System.out.println("结果为：" + b[k3 - 1]);
							break;
						}
					}
				}
			}
			do {
				pageinnerTable.programFunction();
				System.out.println("请输入一个整数(1-6)：");
				i = a.nextInt();
				while (i < 1 || i > 6) {
					System.out.println("输入有误，请重新输入（1-6）：");
					i = a.nextInt();
				}
				pageinnerTable.dealFunction(i, TLB, s, b);
			} while (i != 5);
		} while (i == 5);
		System.out.println("退出程序!");
	}
}
