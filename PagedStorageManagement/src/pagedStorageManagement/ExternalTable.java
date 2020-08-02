package pagedStorageManagement;

/**
 * 这个对象表示---- 外表 --- 对应外存
 */
public class ExternalTable {// 这个对象表示---- 外表
    private int pageNumber;//页号
    private int sts;//数据
    private int length;

    public ExternalTable() {
    }


    public ExternalTable(int pageNumber, int sts) {
        this.pageNumber = pageNumber;
        this.sts = sts;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setSts(int sts) {
        this.sts = sts;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getSts() {
        return sts;
    }

    public void printSource(int i) {
        System.out.println(i + "\t" + this.pageNumber + "\t" + this.sts + "\n");
    }

}
