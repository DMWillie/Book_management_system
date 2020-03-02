package servlet;

import bean.AdminBean;
import bean.BookBean;
import bean.ReviewBean;
import dao.AdminDao;
import dao.BookDao;
import dao.HistoryDao;
import dao.ReviewDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.getWriter().append("Served at: ").append(request.getContextPath());
        //doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理读者借阅申请,同意或者不同意
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //获取申请记录id
        int rid = Integer.parseInt(request.getParameter("rid"));
        //获取管理员操作(审核)结果
        int status = Integer.parseInt(request.getParameter("status"));
        ReviewDao reviewDao = new ReviewDao();
        //更新该申请的审核状态
        reviewDao.updateReview(rid,status);
        /***
         * 同时根据审核的结果,如果通过的话将借阅记录存入数据表
         * 同时将book表对应bid图书的库存数量num-1,借用人borrow_user设为当前的adminbean,借用日期
         * 设为当前日期
         */
        if(reviewDao.isPass(rid)){  //审核通过才做这些操作
            ReviewBean bean = reviewDao.getReviewBean(rid);
            AdminDao adminDao = new AdminDao();
            BookDao bookDao = new BookDao();
            HistoryDao historyDao = new HistoryDao();
            int aid = bean.getAid();        //获取申请者id
            int bid = bean.getBid();        //获取申请图书的id
            AdminBean admin = adminDao.get_AidInfo(aid);
            BookBean bookBean = bookDao.get_BookInfo(bid);
            //获取原先的图书库存数量
            int bookNum = bookBean.getcurrentNum();
            //生成当前日期
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DATE);
            //生成借阅日期
            String begintime = ""+year+"-"+(month+1)+"-"+day;
            //更新该图书的库存数量,借用人以及借阅日期信息
            bookDao.updateBookBorrowInfo(bid,bookNum-1,admin.getName(),begintime);
            //向借阅记录表中插入一条记录
            historyDao.borrowBook(bid,admin);
            adminDao.updateUser(aid,admin.getUsername(),admin.getPassword(),
						admin.getName(),admin.getEmail(),admin.getPhone(),
						admin.getLend_num()+1,admin.getMax_num());
        }
        response.sendRedirect("/books/admin_review.jsp");
    }
}
