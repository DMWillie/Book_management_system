package bean;

public class ReviewBean {
    /***
     * 读者申请借书的审核记录数据表的bean
     */
    private int rid;    //审核记录的id
    private int bid;    //申请借阅图书的id
    private int aid;    //申请读者的id
    private String card;    //申请借阅图书的图书号
    private String bookname;    //申请借阅图书的名称
    private String username;    //申请读者的账号
    private String adminname;   //申请读者的姓名
    private String application_time;    //申请借阅的时间
    private int status; //管理员审核状态,1表示待审核,2表示已通过,3表示不通过

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getApplication_time() {
        return application_time;
    }

    public void setApplication_time(String application_time) {
        this.application_time = application_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
