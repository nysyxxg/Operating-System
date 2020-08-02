package pagedStorageManagement;

import java.util.Scanner;

/**
 * 这个对象表示 -- 页表 -- 对应内存
 * java实现页式存储管理
 * https://blog.csdn.net/pudongqi/article/details/78740095
 */
public class PageTable {// 这个对象表示 -- 页表
	protected Page pageTable[];
	protected int current;
	private int length;

	public PageTable() {
	}

	public PageTable(int length) {
		this.length = length;
		this.current = 0;
		pageTable = new Page[length];
		for (int i = 0; i < length; i++) {
			this.pageTable[i] = new Page();
		}
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getCurrent() {
		return this.current;
	}

	public int searchPage(int pageNumber) {
		int i = 0;
		if (this.current == 0) {
			return -2;
		} else {
			while (i < this.current) {
				if (this.pageTable[i].getPageNumber() == pageNumber) {
					return i;
				}
				i++;
			}
			return -1;
		}
	}

	public void inChange(int b[], String ch, int number) {
		Scanner a = new Scanner(System.in);
		switch (ch) {
			case "yes": {
				System.out.println("请输入一个新的数据");
				b[this.pageTable[number].getPhysicsNumber()] = a.nextInt();
				this.pageTable[number].setChange(true);
				System.out.println("修改成功");
				break;
			}
			case "no": {
				break;
			}
			default: {
				System.out.println("输入字符有误，将退出程序！");
				System.exit(0);
			}
		}
	}

	public int isOver() {
		if (this.current >= this.length) {
			return 1;
		} else
			return 0;
	}

	public int minVisitCount() {
		int i, t = 0;
		for (i = 1; i < this.current; i++) {
			if (this.pageTable[i].getVisitCount() < this.pageTable[t].getVisitCount()) {
				t = i;
			}
		}
		return t;
	}

	public int isChange(int number) {
		if (this.pageTable[number].getChange() == true) {
			return 1;
		} else
			return 0;
	}

	public void printPageTable() {
		System.out.println("页表:");
		System.out.println("索引\t" + "页号\t" + "物理块号\t" + "状态\t" + "访问次数\t" + "修改\t" + "外存地址\t");
		for (int i = 0; i < this.length; i++) {
			System.out.println(i + "\t" + this.pageTable[i].getPageNumber() + "\t" + this.pageTable[i].getPhysicsNumber() + "\t" + this.pageTable[i].getState() + "\t" + this.pageTable[i].getVisitCount() + "\t" + this.pageTable[i].getChange() + "\t" + this.pageTable[i].getCRTAddress());
		}
	}

	public void programFunction() {
		System.out.println("***********************请求分页存储系统***********************");
		System.out.println("功能:");
		System.out.println("\t 1.查看页表");
		System.out.println("\t 2.查看快表");
		System.out.println("\t 3.查看外存");
		System.out.println("\t 4.在内存修改数据");
		System.out.println("\t 5.继续访问页面");
		System.out.println("\t 6.退出程序");
	}

	public void dealFunction(int i, TLBuffer TLB, ExternalTable s[], int b[]) {
		if (i == 1) {
			this.printPageTable();
		} else if (i == 2) {
			TLB.printQuickTable();
		} else if (i == 3) {
			System.out.println("外存：");
			System.out.println("外存地址\t" + "页号\t" + "数据\n");
			for (int k = 0; k < 20; k++) {
				s[k].printExternalTable(k);
			}
		} else if (i == 4) {
			String ch = "yes";
			int pageNumber;
			Scanner a = new Scanner(System.in);
			System.out.println("请输入一个页号:");
			pageNumber = a.nextInt();
			int number = this.searchPage(pageNumber);
			if (number < 0) {
				System.out.println("内存中没有此页号");
			} else {
				this.inChange(b, ch, number);
			}
		} else if (i == 6) {
			System.out.println("结束程序");
			System.exit(0);
		}
	}

}
