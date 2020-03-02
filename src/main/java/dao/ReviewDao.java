package dao;

import bean.AdminBean;
import bean.ReviewBean;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 关于审核记录连接数据库所有操作的类
 */
public class ReviewDao {
    /**
     * 添加审核记录，传入所有的信息
     * @param bid
     * @param aid
     * @param card
     * @param bookname
     * @param username
     * @param adminname
     * @param application_time
     * status默认为0,可以不传
     */
    public void addReview(int bid,int aid,String card,String bookname,String username,
                          String adminname,String application_time){
        Connection conn = DBUtil.getConnectDb();
        String sql = "insert into review(bid,aid,card,bookname,username,adminname,application_time)" +
                " values(?,?,?,?,?,?,?)";
        int rs = 0;
        PreparedStatement stm = null;
        try{
            stm = conn.prepareStatement(sql);
            stm.setInt(1,bid);
            stm.setInt(2,aid);
            stm.setString(3,card);
            stm.setString(4,bookname);
            stm.setString(5,username);
            stm.setString(6,adminname);
            stm.setString(7,application_time);
            rs = stm.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /***
     * 根据rid返回某个ReviewBean
     */
    public ReviewBean getReviewBean(int rid){
        ReviewBean bean = new ReviewBean();
        String sql = "select * from review where rid=?";
        Connection conn = DBUtil.getConnectDb();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try{
            stm = conn.prepareStatement(sql);
            stm.setInt(1,rid);
            rs = stm.executeQuery();
            if(rs.next()){
                bean.setRid(rid);
                bean.setAid(rs.getInt("aid"));
                bean.setBid(rs.getInt("bid"));
                bean.setCard(rs.getString("card"));
                bean.setBookname(rs.getString("bookname"));
                bean.setUsername(rs.getString("username"));
                bean.setAdminname(rs.getString("adminname"));
                bean.setApplication_time(rs.getString("application_time"));
                bean.setStatus(rs.getInt("status"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            DBUtil.CloseDB(rs,stm,conn);
        }
        return bean;
    }
    /**
     * 根据读者id获取所有的审核信息,返回的是ArrayList数组形式
     * @return
     */
    public ArrayList<ReviewBean> getReviewListInfo(int aid){
        ArrayList<ReviewBean> result = new ArrayList<>();
        String sql = "select * from review where aid=?";
        Connection conn = DBUtil.getConnectDb();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try{
            stm = conn.prepareStatement(sql);
            stm.setInt(1,aid);
            rs = stm.executeQuery();
            while(rs.next()){
                ReviewBean rb = new ReviewBean();
                rb.setRid(rs.getInt("rid"));
                rb.setAid(aid);
                rb.setBid(rs.getInt("bid"));
                rb.setCard(rs.getString("card"));
                rb.setBookname(rs.getString("bookname"));
                rb.setUsername(rs.getString("username"));
                rb.setAdminname(rs.getString("adminname"));
                rb.setApplication_time(rs.getString("application_time"));
                rb.setStatus(rs.getInt("status"));
                result.add(rb);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{   //关闭数据库连接
            DBUtil.CloseDB(rs,stm,conn);
        }
        return result;
    }
    /**
     * 根据status获取某个状态下所有的审核信息,返回的是ArrayList数组形式
     * @return
     */
    public ArrayList<ReviewBean> getReviewStatusInfo(int status){
        ArrayList<ReviewBean> result = new ArrayList<>();
        String sql = "select * from review where status=?";
        Connection conn = DBUtil.getConnectDb();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try{
            stm = conn.prepareStatement(sql);
            stm.setInt(1,status);
            rs = stm.executeQuery();
            while(rs.next()){
                ReviewBean rb = new ReviewBean();
                rb.setRid(rs.getInt("rid"));
                rb.setAid(rs.getInt("aid"));
                rb.setBid(rs.getInt("bid"));
                rb.setCard(rs.getString("card"));
                rb.setBookname(rs.getString("bookname"));
                rb.setUsername(rs.getString("username"));
                rb.setAdminname(rs.getString("adminname"));
                rb.setApplication_time(rs.getString("application_time"));
                rb.setStatus(status);
                result.add(rb);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{   //关闭数据库连接
            DBUtil.CloseDB(rs,stm,conn);
        }
        return result;
    }
    /**
     * 修改审核记录的状态,rid作为条件
     * status作为传入的修改参数
     */
    public void updateReview(int rid,int status){
        Connection conn = DBUtil.getConnectDb();
        String sql = "update review set status=? where rid=?";
        PreparedStatement stm = null;
        try{
            stm = conn.prepareStatement(sql);
            stm.setInt(1,status);
            stm.setInt(2,rid);
            stm.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /***
     * 获得某图书申请借阅的审核结果
     * 根据传入的bid和aid来查询review表中的status审核状态
     * @return: boolean(true代表审核通过,false代表审核不通过)
     */
    public boolean isPassOn(int bid, AdminBean admin){
        Connection conn = DBUtil.getConnectDb();
        int aid = admin.getAid();   //获取读者id
        String sql = "select * from review where bid=? and aid=? and status=1";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try{
            stm = conn.prepareStatement(sql);
            stm.setInt(1,bid);
            stm.setInt(2,aid);
            //System.out.println(stm);
            rs = stm.executeQuery();
            if(rs.next()){  //查询到了审核通过的记录
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    /***
     * 获得某图书申请借阅的审核结果
     * 直接根据rid来查询review表中的status审核状态
     * @return: boolean(true代表审核通过,false代表审核不通过)
     */
    public boolean isPass(int rid){
        Connection conn = DBUtil.getConnectDb();
        String sql = "select * from review where rid=? and status=1";
        PreparedStatement stm = null;
        ResultSet rs = null;
        try{
            stm = conn.prepareStatement(sql);
            stm.setInt(1,rid);
            //System.out.println(stm);
            rs = stm.executeQuery();
            if(rs.next()){  //查询到了审核通过的记录
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
