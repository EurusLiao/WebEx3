package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Servlet implementation class SelStuShowServlet
 */
public class SelStuShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelStuShowServlet() {
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
		String Name ="";
		String Sex ="";
		String Politic ="";
		String Department ="";
		String Come ="";
		String Birth ="";
		String Memo = "";
		String S_no=(String)request.getParameter("myck");
		if(S_no==null) S_no="";
		String operation=(String)request.getParameter("operation");
		ServletContext sc=getServletContext();
		RequestDispatcher rd=null;
		
		InitialContext ctx = null;
		DataSource ds = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		if("modify".equals(operation)){
			try{
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/students");
			conn = ds.getConnection();	
			String sql="select * from studentDB where S_no ='"+S_no+"'";
			stmt = conn.prepareStatement(sql);
			rs=stmt.executeQuery(sql);
			
			if(rs.next()){
			Name = rs.getString("Name");
			Sex = rs.getString("Sex");
			Politic = rs.getString("Politic");
			Department =rs.getString("Department");
			Come = rs.getString("Come");
			java.sql.Date birth=rs.getDate("Birth");
			if(birth!=null){
				SimpleDateFormat df=new SimpleDateFormat("yyyy-mm-dd");
				Birth = df.format(birth);
			}
			Memo =rs.getString("Memo");	
			}
			request.setAttribute("sno",S_no);
			request.setAttribute("name",Name);
			request.setAttribute("sex",Sex);
			request.setAttribute("politic",Politic);
			request.setAttribute("department",Department);
			request.setAttribute("come",Come);
			request.setAttribute("birth",Birth);
			request.setAttribute("memo",Memo);
			request.setAttribute("operation",operation);
			rs.close();
			stmt.close();
			conn.close();
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
		rd=sc.getRequestDispatcher("/update.jsp");
		rd.forward(request,response);
	}

}
