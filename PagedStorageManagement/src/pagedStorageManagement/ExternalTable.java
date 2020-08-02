package pagedStorageManagement;

/**
 * 这个对象表示---- 外表 --- 对应外存
 */
public class ExternalTable {// 这个对象表示---- 外表
    private int pageNumber;//页号
    private int data;//数据
    private int length;

    public ExternalTable() {
    }


    public ExternalTable(int pageNumber, int data) {
        this.pageNumber = pageNumber;
        this.data = data;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void printExternalTable(int i) {
        System.out.printf("外表：");
        System.out.println(i + "\t" + this.pageNumber + "\t" + this.data + "\n");
    }

}
