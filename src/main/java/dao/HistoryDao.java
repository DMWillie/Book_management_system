package dao;

import bean.AdminBean;
import bean.BookBean;
import bean.HistoryBean;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 关于借阅历史连接数据库所有操作的类
 */
public class HistoryDao {

    /***
     * 根据hid获取历史借阅记录
     * @param hid
     * @return
     */
    public HistoryBean getHistoryInfo(int hid){
        HistoryBean result = new HistoryBean();
        Connection conn = DBUtil.getConnectDb();
        String sql = "select * from history where hid=?";
        ResultSet rs = null;
        PreparedStatement stm = null;
        try{
            stm = conn.prepareStatement(sql);
            stm.setInt(1,hid);
            rs = stm.executeQuery();
            if(rs.next()){
                result.setHid(hid);
                result.setAid(rs.getInt("aid"));
                result.setBid(rs.getInt("bid"));
                result.setCard(rs.getString("card"));
                result.setBookname(rs.getString("bookname"));
                result.setUsername(rs.getString("username"));
                result.setAdminname(rs.getString("adminname"));
                result.setBegintime(rs.getString("begintime"));
                result.setEndtime(rs.getString("endtime"));
                result.setStatus(rs.getInt("status"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            DBUtil.CloseDB(rs,stm,conn);
        }
        return result;
    }
    /**
     * 获取借阅记录的全部信息，传入的条件有status，aid，表示搜索正在借阅的，或者已经还书的信息，aid代表当前登录用户
     * @param status
     * @return
     */
    public ArrayList<HistoryBean> get_HistoryListInfo(int status,String aid){
        ArrayList<HistoryBean> tag_Array = new ArrayList<HistoryBean>();
        Connection conn = DBUtil.getConnectDb();
        String sql = "select * from history where aid='"+aid+"' and status='"+status+"'";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            while(rs.next()){
                HistoryBean tag = new HistoryBean();
                tag.setHid(rs.getInt("hid"));
                tag.setAid(rs.getInt("aid"));
                tag.setBid(rs.getInt("bid"));
                tag.setBookname(rs.getString("bookname"));
                tag.setCard(rs.getString("card"));
                tag.setAdminname(rs.getString("adminname"));
                tag.setUsername(rs.getString("username"));
                tag.setBegintime(rs.getString("begintime"));
                tag.setEndtime(rs.getString("endtime"));
                tag.setStatus(rs.getInt("status"));
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
     * 获取借阅记录的全部信息，传入的条件有status，表示搜索正在借阅的，或者已经还书的信息
     * @param status
     * @return
     */
    public ArrayList<HistoryBean> get_HistoryListInfo2(int status){
        ArrayList<HistoryBean> tag_Array = new ArrayList<HistoryBean>();
        Connection conn = DBUtil.getConnectDb();
        String sql = "select * from history where status='"+status+"'";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            while(rs.next()){
                HistoryBean tag = new HistoryBean();
                tag.setHid(rs.getInt("hid"));
                tag.setAid(rs.getInt("aid"));
                tag.setBid(rs.getInt("bid"));
                tag.setBookname(rs.getString("bookname"));
                tag.setCard(rs.getString("card"));
                tag.setAdminname(rs.getString("adminname"));
                tag.setUsername(rs.getString("username"));
                tag.setBegintime(rs.getString("begintime"));
                tag.setEndtime(rs.getString("endtime"));
                tag.setStatus(rs.getInt("status"));
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
     * 图书借阅函数，根据传入bid图书id，adminbean当前登录用户的信息，在借阅记录数据表中新插入一条记录
     * @param bid
     * @param adminbean
     */
    public void borrowBook(int bid, AdminBean adminbean) {
        // TODO Auto-generated method stub
        BookBean bookbean = new BookBean();
        BookDao bookDao = new BookDao();
        bookbean = bookDao.get_BookInfo(bid);
        //生成日期的功能
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        //生成借阅开始日期
        String begintime = ""+year+"-"+month+"-"+day;
        month = month + 1;
        //生成截止还书日期
        String endtime = ""+year+"-"+month+"-"+day;
        Connection conn = DBUtil.getConnectDb();
        String sql = "insert into history(aid,bid,card,bookname,adminname,username,begintime,endtime,status) values(?,?,?,?,?,?,?,?,?)";
        int rs = 0;
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, adminbean.getAid());
            stm.setInt(2, bookbean.getBid());
            stm.setString(3, bookbean.getCard());
            stm.setString(4, bookbean.getName());
            stm.setString(5, adminbean.getUsername());
            stm.setString(6, adminbean.getName());
            stm.setString(7, begintime);
            stm.setString(8, endtime);
            stm.setInt(9, 1);
            rs = stm.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 还书功能的函数，根据传入的hid借阅记录id，讲status字段的值改为0，并将还书日期改变为当前日期
     * @param hid
     */
    public void borrowBook2(int hid) {
        // TODO Auto-generated method stub
        //生成日期
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DATE);
        //生成还书日期
        String endtime = ""+year+"-"+month+"-"+day;
        Connection conn = DBUtil.getConnectDb();
        String sql = "update history set endtime=?,status=? where hid=?";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setString(1, endtime);
            stm.setInt(2, 2);
            stm.setInt(3, hid);
            stm.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
