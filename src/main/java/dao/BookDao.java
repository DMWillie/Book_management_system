package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import bean.BookBean;
import util.DBUtil;
/**
 * 关于图书连接数据库的所有操作的类
 */
public class BookDao {

	/**
	 * 添加图书信息，传入所有的信息
	 * @param card
	 * @param name
	 * @param type
	 * @param autho
	 * @param press
	 * @param num
	 * @param borrowUser
	 * @param borrowTime
	 */
	public void addBook(String card, String name, String type, String autho, String press, int num,
						String borrowUser,String borrowTime) {
		// TODO Auto-generated method stub
		Connection conn = DBUtil.getConnectDb();
		String sql = "insert into book(card,name,type,autho,press,num,borrow_user," +
				"borrow_time) values(?,?,?,?,?,?,?,?)";
		int rs = 0;
		PreparedStatement stm = null;
		try {
			stm = conn.prepareStatement(sql);
			stm.setString(1, card);
			stm.setString(2, name);
			stm.setString(3, type);
			stm.setString(4, autho);
			stm.setString(5, press);
			stm.setInt(6, num);
			stm.setString(7,borrowUser);
			stm.setString(8,borrowTime);
			rs = stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取所有的图书信息，返回的是ArrayList数组形式
	 * @return
	 */
	public ArrayList<BookBean> get_ListInfo(){
		ArrayList<BookBean> tag_Array = new ArrayList<BookBean>();
		Connection conn = DBUtil.getConnectDb();
		String sql = "select * from book";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.prepareStatement(sql);
			rs = stm.executeQuery();
			while(rs.next()){
				BookBean tag = new BookBean();
				tag.setBid(rs.getInt("bid"));
				tag.setName(rs.getString("name"));
				tag.setCard(rs.getString("card"));
				tag.setType(rs.getString("type"));
				tag.setAutho(rs.getString("autho"));
				tag.setPress(rs.getString("press"));
				tag.setcurrentNum(rs.getInt("num"));
				tag.setBorrowUser(rs.getString("borrow_user"));
				tag.setBorrowTime(rs.getString("borrow_time"));
				tag_Array.add(tag);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.CloseDB(rs, stm, conn);
		}
		return tag_Array;
	}

	/**
	 * 获取单个图书的信息，根据传入的bid来查找，返回一个BookBean数据类型
	 * @param bid
	 * @return
	 */
	public BookBean get_BookInfo(int bid){
		BookBean tag = new BookBean();
		Connection conn = DBUtil.getConnectDb();
		String sql = "select * from book where bid='"+bid+"'";
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.prepareStatement(sql);
			rs = stm.executeQuery();
			while(rs.next()){
				tag.setBid(rs.getInt("bid"));
				tag.setName(rs.getString("name"));
				tag.setCard(rs.getString("card"));
				tag.setType(rs.getString("type"));
				tag.setAutho(rs.getString("autho"));
				tag.setPress(rs.getString("press"));
				tag.setcurrentNum(rs.getInt("num"));
				tag.setBorrowUser(rs.getString("borrow_user"));
				tag.setBorrowTime(rs.getString("borrow_time"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.CloseDB(rs, stm, conn);
		}
		return tag;
	}
	/**
	 * 修改图书的信息，bid作为条件，
	 */
	public void updateBook(int bid, String card, String name, String type, String autho,
						   String press, int num,String borrowUser,String borrowTime) {
		// TODO Auto-generated method stub
		Connection conn = DBUtil.getConnectDb();
		String sql = "update book set name=?,card=?,type=?,autho=?,press=?,num=?," +
				"borrow_user=?,borrow_time=? where bid=?";
		PreparedStatement stm = null;
		try {
			stm = conn.prepareStatement(sql);
			stm.setString(1, name);
			stm.setString(2, card);
			stm.setString(3, type);
			stm.setString(4, autho);
			stm.setString(5, press);
			stm.setInt(6, num);
			stm.setString(7,borrowUser);
			stm.setString(8,borrowTime);
			stm.setInt(9, bid);
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 删除图书信息，根据传入的bid作为条件
	 * @param bid
	 */
	public void deleteBook(int bid) {
		// TODO Auto-generated method stub
		Connection conn = DBUtil.getConnectDb();
		String sql = "delete from book where bid=?";
		PreparedStatement stm = null;
		try {
			stm = conn.prepareStatement(sql);
			stm.setInt(1, bid);
			stm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(uid);
		
	}
	/**
	 * 用户查找图书，根据输入的名称和图书号来进行查询，然后返回一个ArrayList数组类型
	 * @param name
	 * @param card
	 * @return
	 */
	public ArrayList<BookBean> getBookList(String name,String card) {
		// TODO Auto-generated method stub
		ArrayList<BookBean> tag_Array = new ArrayList<BookBean>();
		Connection conn = DBUtil.getConnectDb();
		//首先进行判断,用户是只输入了图书名称,还是只输入了图书号或者是两者都输入了
		String sql = null;
		if((!name.equals(""))&&(!card.equals(""))){
			sql = "select * from book where name='"+name+"' and card='"+card+"'";
		}else if(!name.equals("")){
			sql = "select * from book where name='"+name+"'";
		}else if(!card.equals("")){
			sql = "select * from book where card='"+card+"'";
		}else{	//如果都不输入则返回空集合
			return tag_Array;
		}
		PreparedStatement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.prepareStatement(sql);
			rs = stm.executeQuery();
			while(rs.next()){
				BookBean tag = new BookBean();
				tag.setBid(rs.getInt("bid"));
				tag.setName(rs.getString("name"));
				tag.setCard(rs.getString("card"));
				tag.setType(rs.getString("type"));
				tag.setAutho(rs.getString("autho"));
				tag.setPress(rs.getString("press"));
				tag.setcurrentNum(rs.getInt("num"));
				tag.setBorrowUser(rs.getString("borrow_user"));
				tag.setBorrowTime(rs.getString("borrow_time"));
				tag_Array.add(tag);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.CloseDB(rs, stm, conn);
		}
		return tag_Array;
	}

	/**更新book表中的借用人和借用时间信息,以及库存数量(k)
	 * 根据bid作为条件
	 */
	public void updateBookBorrowInfo(int bid,int k,String borrowUser,String borrowTime){
		Connection conn = DBUtil.getConnectDb();
		String sql = "update book set borrow_user=?,borrow_time=?,num=? where bid=?";
		PreparedStatement stm = null;
		try{
			stm = conn.prepareStatement(sql);
			stm.setString(1,borrowUser);
			stm.setString(2,borrowTime);
			stm.setInt(3,k);
			stm.setInt(4,bid);
			stm.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
