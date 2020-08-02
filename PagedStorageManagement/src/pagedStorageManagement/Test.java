package pagedStorageManagement;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int i, number = -10, pageNumber, k2, result;
		int k3 = 0;//当前存储的内存地址
		int t;//页表中访问次数最小的索引
		int memoryData[] = new int[10];//内存中存储的数据
		String ch;
		int sLength, pLength, tLength, data;
		System.out.println("请输入外存大小长度：");
		sLength = scanner.nextInt();
		System.out.println("请输入页表大小长度：");
		pLength = scanner.nextInt();
		System.out.print("请输入快表大小长度:");
		tLength = scanner.nextInt();
		//定义页表,快表，外存(外表)
		PageTable pageinnerTable = new PageTable(pLength);//页表
		ExternalTable externalTable[] = new ExternalTable[sLength];//外表
		TLBuffer tlb = new TLBuffer(tLength);//快表
		System.out.println("产生一个随机序列作为外存数据！");
		//录入外存地址和数据
		for (i = 0; i < sLength; i++) {
			data = (int) (100 * Math.random());
			System.out.print(data + "\t");
			externalTable[i] = new ExternalTable(i, data);// 分配pageNumber 并将数据和pageNumber 对应
		}
		System.out.println("\n外存设置成功");
		//请求页表
		do {
			tlb.printQuickTable();//打印当前快表的情况
			pageinnerTable.printPageTable();//打印当前页表的情况
			System.out.println("请输入一个页面的页号(0-19):");
			pageNumber = scanner.nextInt();
			if (pageNumber >= 20 || pageNumber < 0) {
				System.out.println("输入数据业号有错，将退出程序!");
				System.exit(0);
			}
			//检测快表,快表存储当前的页表项，即当快表满时采用最近最久未被使用算法置换快表
			System.out.println("进入快表检测");
			if (tlb.getCurrent() > 0) {
				number = tlb.searchPageByPageNumber(pageNumber);// 从【快表】中， 根据输入的页面的PageNumber进行查找
				if (number != -1 && number != -2) {
					result = memoryData[tlb.quickTable[number].getPhysicsNumber()];// 获取物理页号
					System.out.println("在快表中找到，结果为：" + result);
					//找出该页号在页表中的位置并修改访问字段
					number = tlb.quickTable[number].getCRTAddress(); // 获取 外存地址
					pageinnerTable.pageTable[number].setVisitCount(pageinnerTable.pageTable[number].getVisitCount() + 1);
				}
			}
			if (tlb.getCurrent() <= 0 || number == -1) {
				System.out.println("在快表中找不到！" + "进入内存检测：");
				//在快表中找不到,去内存区的页表找
				if (pageinnerTable.current > 0) {
					number = pageinnerTable.searchPage(pageNumber);//从【页表】中，开始根据页号pageNumber，进行搜索所在的下标
					if (number != -1 && number != -2) {
						result = memoryData[pageinnerTable.pageTable[number].getPhysicsNumber()];
						System.out.println("在页表中找到,结果为:" + result);
						//修改访问字段和状态位
						pageinnerTable.pageTable[number].setVisitCount(pageinnerTable.pageTable[number].getVisitCount() + 1);
						//修改快表
						tlb.changeKShell(pageinnerTable, number);
					}
				}
				if (pageinnerTable.current <= 0 || number == -1) {
					System.out.println("在内存中找不到！");
					System.out.println("从外存中调入内存：");
					//在页表找不到，去外存区找
					for (i = 0; i < sLength; i++) {
						if (pageNumber == externalTable[i].getPageNumber()) {//在外存找到了缺页
							k2 = pageinnerTable.isOver();
							if (k2 == 1) {//内存已满
								t = pageinnerTable.minVisitCount();
								System.out.println("内存已满!即将调出页号 " + pageinnerTable.pageTable[t].getPageNumber());
							} else {
								t = pageinnerTable.current;
								pageinnerTable.setCurrent(pageinnerTable.getCurrent() + 1);
							}
							//判断是否修改了内存的数据
							if (pageinnerTable.isChange(t) == 1) {
								externalTable[pageinnerTable.pageTable[t].getCRTAddress()].setData(memoryData[pageinnerTable.pageTable[t].getPhysicsNumber()]);
							}
							//调入内存
							pageinnerTable.pageTable[t].setPageNumber(pageNumber);
							if (k2 == 1) {
								memoryData[pageinnerTable.pageTable[t].getPhysicsNumber()] = externalTable[i].getData();
							} else {
								pageinnerTable.pageTable[t].setPhysicsNumber(k3);//未满则设置物理块号,满了只改变其他5个字段
								memoryData[k3] = externalTable[i].getData();
								k3++;//物理块号
							}
							pageinnerTable.pageTable[t].setState(true);
							pageinnerTable.pageTable[t].setVisitCount(1);
							pageinnerTable.pageTable[t].setChange(false);
							pageinnerTable.pageTable[t].setCRTAddress(i);
							System.out.println("调入内存成功！");
							//修改快表
							tlb.changeKShell(pageinnerTable, t);
							System.out.println("修改快表成功！");
							System.out.println("结果为：" + memoryData[k3 - 1]);
							break;
						}
					}
				}
			}
			do {
				pageinnerTable.programFunction();
				System.out.println("请输入一个整数(1-6)：");
				i = scanner.nextInt();
				while (i < 1 || i > 6) {
					System.out.println("输入有误，请重新输入（1-6）：");
					i = scanner.nextInt();
				}
				pageinnerTable.dealFunction(i, tlb, externalTable, memoryData);
			} while (i != 5);
		} while (i == 5);
		System.out.println("退出程序!");
	}
}
