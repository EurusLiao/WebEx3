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

import javax.sql.*;

import java.sql.*;
import java.text.*;

/**
 * Servlet implementation class StuUpdateServlet
 */
public class StuUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StuUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String operation = ""; 
		String S_no = "";
		String Name = "";
		String Sex = "";
		String Politic = "";
		String Department = "";
		String Come = "";
		String Birth = "";
		String Memo = "";
		operation = (String)request.getParameter("operation"); 
		S_no=request.getParameter("S_no").trim();
		Name=request.getParameter("Name").trim();
		Sex=request.getParameter("Sex").trim();
		Politic=request.getParameter("Politic").trim();
		Department=request.getParameter("Department").trim();
		Come=request.getParameter("Come").trim();
		Birth=request.getParameter("Birth").trim();
		Memo=request.getParameter("Memo").trim();

		String sql=null;

		String error="false";
		
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
			if("add".equals(operation))
			{
				sql="select * from studentDB where S_no =?";
				stmt=conn.prepareStatement(sql);
				stmt.setString(1,S_no);
				rs=stmt.executeQuery(sql);
				Date birth=null;
				if(rs.next()==false){
					if(Birth.equals("")==true){
						Birth="1900-01-01";
					}
				    SimpleDateFormat format  =   new   SimpleDateFormat("yyyy-MM-dd"); 
					birth=new Date(format.parse(Birth).getTime());
					sql="insert into studentDB values(?,?,?,?,?,?,?,?)";
					stmt=conn.prepareStatement(sql);
				
					stmt.setString(1,S_no);
					stmt.setString(2,Name);
					stmt.setString(3,Sex);
					stmt.setString(4,Politic);
					stmt.setString(5,Department);
					stmt.setString(6,Come);
					stmt.setDate(7,birth );
					stmt.setString(8,Memo);
					stmt.execute();
					conn.commit();

					rs.close();
					rd=sc.getRequestDispatcher("/student.jsp");
				}
				else 
				{
					error="true";
					request.setAttribute("error",error);
					request.setAttribute("operation",operation);
					rd=sc.getRequestDispatcher("/update.jsp");
				}
				
			}
			if("modify".equals(operation)){
				Date birth=null;
				if(Birth.equals("")==true){
					Birth="1900-01-01";
				}
				SimpleDateFormat format  =   new   SimpleDateFormat("yyyy-MM-dd"); 
				birth=new Date(format.parse(Birth).getTime());
			    sql = "update studentDB set Name=?,Sex=?,Politic=?,Department=?,Come=?,Birth=?,Memo=? where S_no=?";
				stmt=conn.prepareStatement(sql);
				stmt.setString(1,Name);
				stmt.setString(2,Sex);
				stmt.setString(3,Politic);
				stmt.setString(4,Department);
				stmt.setString(5,Come);
				stmt.setDate(6,birth);
				stmt.setString(7,Memo);
				stmt.setString(8,S_no);
				stmt.execute();
				conn.commit();
			    
			
			    rs.close();
			    rd=sc.getRequestDispatcher("/student.jsp");
			
			}
			stmt.close();
			conn.close();
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
