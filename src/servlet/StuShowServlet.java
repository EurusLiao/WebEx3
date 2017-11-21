package servlet;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.sql.Statement;

import java.sql.*;
import javax.sql.*;
import javabean.StudentBean;

import java.text.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Servlet implementation class StuShowServlet
 */
public class StuShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StuShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("aaa");
		String operation = ""; 
		operation = (String)request.getParameter("operation"); 
		String sql=null;
		
		InitialContext ctx = null;
		DataSource ds = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
		ServletContext sc=getServletContext();
		RequestDispatcher rd=null;
		ctx = new InitialContext();
		ds = (DataSource) ctx.lookup("java:comp/env/jdbc/students");
		conn = ds.getConnection();
		
		if("del".equals(operation)){
			String[] del = request.getParameterValues("myck");
			for(int i = 0 ; i < del.length ; i ++)
			{
			    sql = "delete from studentDB where S_no = '"+del[i].trim()+"'";
			    stmt = conn.prepareStatement(sql);
				stmt.executeUpdate(sql);
			}
		}
		
		int PageSize=2;//页显示记录数
		int RowCount=0;//总的记录数
		int PageCount=0;//总页数
		int ShowPage=1;//待显页码
		String search_txt="";
		String ToPage=request.getParameter("ToPage");
		if (request.getParameter("search_txt")!=null && !(request.getParameter("search_txt").equals("")))
		   {
		  search_txt=new String(request.getParameter("search_txt").trim().getBytes("8859_1"));
		   }
		if (search_txt!=null && !(search_txt.equals("")))
		   {
		   sql="select * from studentDB where S_no  like'%"+search_txt+"%'";
		   }
		else
		   {
		    sql="select * from studentDB order by S_no";
		   }
		 stmt = conn.prepareStatement(sql);
		 rs=stmt.executeQuery(sql);
		 rs.last();
		 RowCount=rs.getRow();
		 PageCount=((RowCount % PageSize)==0?(RowCount/PageSize):(RowCount/PageSize)+1);
		 if(ToPage!=null)
		 {
		 ShowPage=Integer.parseInt(ToPage);
		 if(ShowPage>PageCount) 
		 {
		  ShowPage=PageCount;
		 }
		 else if(ShowPage<=0)
		 {
		  ShowPage=1;
		 }
		 }
		rs.absolute((ShowPage-1)*PageSize+1);
		List<StudentBean> vList = new ArrayList<StudentBean>();
		for(int i=1;i<=PageSize;i++)
		{
		StudentBean student=new StudentBean();
		student.setSno(rs.getString("S_no"));
		student.setName(rs.getString("Name"));
		student.setSex(rs.getString("Sex"));
		student.setPolitic(rs.getString("Politic"));
		student.setDepartment(rs.getString("Department"));
		student.setCome(rs.getString("Come"));
		SimpleDateFormat df=new SimpleDateFormat("yyyy-mm-dd");
		student.setBirth(df.format(rs.getDate("Birth")));
		student.setMemo(rs.getString("Memo"));
		vList.add(student);
		if(!rs.next()) break;
		}
		rs.close();
		stmt.close();
		conn.close();
		request.setAttribute("student",vList);
		request.setAttribute("pagesize",PageSize);
		request.setAttribute("rowcount",RowCount);
		request.setAttribute("pagecount",PageCount);
		request.setAttribute("showpage",ShowPage);
		rd=sc.getRequestDispatcher("/student.jsp");
		rd.forward(request,response); 
		}
		catch(Exception e)
		{
		 e.printStackTrace();
		}finally{
			try{if(stmt!=null) stmt.close();
			}catch(Exception e){}
			try{if(conn!=null) conn.close();
			}catch(Exception e){}
		}
	}
	}