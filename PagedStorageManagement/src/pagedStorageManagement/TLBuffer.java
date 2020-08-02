package pagedStorageManagement;

/**
 * TLB --
 * 这个对象表示-- 快表
 */
public class TLBuffer {
    protected  Page quickTable[];
    private int current;
    private int length;
    private int changeNumber;//修改快表的次数

    public TLBuffer() {
    }

    public TLBuffer(int length) {
        this.length = length;
        this.current = 0;
        this.changeNumber = 0;
        quickTable = new Page[length];
        for (int i = 0; i < length; i++) {
            this.quickTable[i] = new Page();
        }
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setChangeNumber(int changeNumber) {
        this.changeNumber = changeNumber;
    }

    public int getCurrent() {
        return current;
    }

    public int getChangeNumber() {
        return changeNumber;
    }

    public int getLength() {
        return length;
    }

    public int searchPageByPageNumber(int pageNumber) {
        int i = 0;
        if (this.changeNumber == 0 && this.current == 0) {
            return -2;
        } else if (this.changeNumber < this.length) {
            while (i < this.current) {
                if (this.quickTable[i].getPageNumber() == pageNumber) {
                    return i;
                }
                i++;
            }
            return -1;
        } else {
            while (i < this.length) {
                if (this.quickTable[i].getPageNumber() == pageNumber) {
                    return i;
                }
                i++;
            }
            return -1;
        }
    }

    public void changeKShell(PageTable pageTable, int number) {
        if (this.getChangeNumber() >= this.getLength()) {
            if (this.getCurrent() == this.getLength()) {
                this.setCurrent(0);
            }
            System.out.println("快表已满，快表中即将调出页号" + this.quickTable[this.current].getPageNumber());
        }
        if (this.getCurrent() < this.getLength()) {
            this.quickTable[this.getCurrent()].setCRTAddress(number);
            this.quickTable[this.getCurrent()].setPageNumber(pageTable.pageTable[number].getPageNumber());
            this.quickTable[this.getCurrent()].setPhysicsNumber(pageTable.pageTable[number].getPhysicsNumber());
            this.setCurrent(this.getCurrent() + 1);
            this.setChangeNumber(this.getChangeNumber() + 1);
        }
    }

    public void printQuickTable() {
        System.out.println("快表：");
        System.out.println("索引\t" + "页号\t" + "物理块号\t" + "在页表下的索引");
        for (int i = 0; i < this.length; i++) {
            System.out.println(i + "\t" + this.quickTable[i].getPageNumber() + "\t" + this.quickTable[i].getPhysicsNumber() + "\t" + this.quickTable[i].getCRTAddress());
        }
    }
}