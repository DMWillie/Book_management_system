package servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.AdminBean;
import bean.BookBean;
import bean.HistoryBean;
import dao.AdminDao;
import dao.BookDao;
import dao.HistoryDao;
import dao.ReviewDao;

/**
 * Servlet implementation class borrowServlet
 */
@WebServlet("/borrowServlet")
public class borrowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public borrowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		//设置编码类型
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		//为了区分借书和还书的功能,设置tip,tip为1，表示借书
		int tip = Integer.parseInt(request.getParameter("tip"));
		if(tip==1){
			//获取图书id
			int bid = Integer.parseInt(request.getParameter("bid"));
			HttpSession session = request.getSession();
			AdminBean admin = new AdminBean();
			//获取到存入session的aid读者id
			String aid = (String)session.getAttribute("aid");
			AdminDao admindao = new AdminDao();
			BookDao bookDao = new BookDao();
			//通过aid获取到读者的信息
			admin = admindao.get_AidInfo2(aid);
			BookBean bookBean = bookDao.get_BookInfo(bid);
			//向review表中插入一条记录
			ReviewDao reviewDao = new ReviewDao();
			//生成当前日期
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			//System.out.println(month);
			int day = c.get(Calendar.DATE);
			//生成借阅申请日期
			String begintime = ""+year+"-"+(month+1)+"-"+day;
			reviewDao.addReview(bid,Integer.parseInt(aid),bookBean.getCard(),bookBean.getName(),
					admin.getUsername(),admin.getName(),begintime);
			//判断管理员审核是否通过,审核通过才将借阅记录存入数据表
			//同时将book表对应bid图书的库存数量num-1,借用人borrow_user设为当前的adminbean,借用日期
			//设为当前日期
			/***
			 * 这里有一个bug,就是当读者发起借书时才会执行isPass的判断,而每次借书时其实管理员都是
			 * 待审核状态,因为当借书时而管理员并没有操作的时候,status一定等于0
			 * 所以在执行这个判断的时候isPass永远为false,所以永远不会向借阅表中插入记录
			 * 解决办法是在管理员审核通过后在ReviewServlet中直接执行该操作
			 */
//			if(reviewDao.isPass(bid,admin)){//该书申请审核通过
//				HistoryDao historyDao = new HistoryDao();
//				//获取原先的图书库存数量
//				int bookNum = bookBean.getcurrentNum();
//				//更新该图书的库存数量,借用人以及借阅日期信息
//				bookDao.updateBookBorrowInfo(bid,bookNum-1,admin.getName(),begintime);
//				//向借阅记录表中插入一条记录
//				historyDao.borrowBook(bid,admin);
//				//将该读者的当前借阅数量+1
//				admindao.updateUser(Integer.parseInt(aid),admin.getUsername(),admin.getPassword(),
//						admin.getName(),admin.getEmail(),admin.getPhone(),
//						admin.getLend_num()+1,admin.getMax_num());
//			}
			response.sendRedirect("/books/select.jsp");
		}else{
			//还书功能，获取借阅记录的hid
			int hid = Integer.parseInt(request.getParameter("hid"));
			//根据hid来获取bid和aid,并更新book表中的借用人和借用时间以及库存数量
			HistoryDao historyDao = new HistoryDao();
			HistoryBean historyBean = historyDao.getHistoryInfo(hid);
			int bid = historyBean.getBid();
			AdminDao adminDao = new AdminDao();
			HttpSession session = request.getSession();
			//获取到存入session的aid读者id
			String aid = (String)session.getAttribute("aid");
			AdminBean admin = adminDao.get_AidInfo2(aid);
			BookDao bookDao = new BookDao();
			BookBean bookBean = bookDao.get_BookInfo(bid);
			//还书后,则该书借用人和借用时间都为"无",同时库存数量+1
			bookDao.updateBookBorrowInfo(bid,bookBean.getcurrentNum()+1,
					"无","无");
			//调用还书功能,改变借阅记录表history中status的状态(变为2)
			historyDao.borrowBook2(hid);
			//将该读者的当前借阅数量-1
			adminDao.updateUser(Integer.parseInt(aid),admin.getUsername(),admin.getPassword(),
					admin.getName(),admin.getEmail(),admin.getPhone(),
					admin.getLend_num()-1,admin.getMax_num());
			response.sendRedirect("/books/borrow.jsp");
//			/**
//			 * 还书在管理员和读者界面都有，为了区分，设置了show字段，show为1表示读者界面
//			 */
//			int show = Integer.parseInt(request.getParameter("show"));
//			//调用还书函数，改变status字段
//			bookdao.borrowBook2(hid);
//			if(show==1){
//				response.sendRedirect("/books/borrow.jsp");
//			}else{
//				response.sendRedirect("/books/admin_borrow.jsp");
//			}
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
