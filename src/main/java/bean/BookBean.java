package bean;

public class BookBean {
	/**
	 * 图书的数据表的bean
	 */
	private int bid;//id
	private String name;//图书名称
	private String card;//图书号
	private String autho;//作者
	private int currentnum;//当前库存数量
	private String type;//图书的分类
	private String press;//出版社
    private String borrowUser;  //该书借用人,默认"无"
    private String borrowTime;  //该书借用时间,默认为null

	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getAutho() {
		return autho;
	}
	public void setAutho(String autho) {
		this.autho = autho;
	}
	public int getcurrentNum() {
		return currentnum;
	}
	public void setcurrentNum(int currentnum) {
		this.currentnum = currentnum;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
    public String getBorrowUser() {
        return borrowUser;
    }
    public void setBorrowUser(String borrowUser) {
        this.borrowUser = borrowUser;
    }
    public String getBorrowTime() {
        return borrowTime;
    }
    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }
}
