<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ page import="java.util.*,java.sql.*,java.text.*"%>
<title>Insert title here</title>
</head>
<body>
<%
String error=(String)request.getAttribute("error");
String operation=(String)request.getAttribute("operation"); 
String S_no=(String)request.getAttribute("sno");
String Name =(String)request.getAttribute("name");
String Sex =(String)request.getAttribute("sex");
String Politic =(String)request.getAttribute("politic");
String Department =(String)request.getAttribute("department");
String Come =(String)request.getAttribute("come");
String Birth =(String)request.getAttribute("birth");
String Memo =(String)request.getAttribute("memo");
%>
<form name="update" method="post" action="studentupdate">
<center><h2>请填写或修改的学生信息</h2></center>
<%if("true".equals(error))
	{
%>
<center><font color="red">您要添加的学生学号已存在，请核对后重新填入</font></center>
<%}%>
<table border="0"  width="100%">
  <tr> 
  <td width="30%" ><div align="right">学号：</div></td>
    <td width="70%"><input type="text" size="60" name="S_no"
  	 maxlength="10"></td>
  </tr>
  <tr>
    <td><div align="right">姓名：</div></td>
    <td><input type="text" size="60" name="Name" maxlength="8"></td>
  </tr>
  <tr>
    <td><div align="right">性别：</div></td> 
  <td><input type="text"  size="60" name="Sex" maxlength="2"></td>
  </tr>
  <tr>
    <td><div align="right">政治面貌：</div></td>
    <td><input type="text"  size="60" name="Politic" maxlength="8"></td>
  </tr>
  <tr>
    <td><div align="right">所在系别：</div></td>
    <td><input type="text"  size="60" name="Department"></td>
  </tr>
<tr> 
   <td><div align="right">学生籍贯：</div></td>
   <td><input type="text"  size="60" name="Come"></td>
</tr>
<tr>
   <td><div align="right">出生日期：</div></td>
   <td><input type="text"  size="60" name="Birth" maxlength="10"></td>
 </tr>
 <tr> 
  <td><div align="right">相关备注：</div></td>
    <td><input type="text"  size="60" name="Memo"></td>
   </tr>
</table>
<br>
<center><input type="button" value="确定" onClick="check()"></center>
<input type="hidden" name="operation" value=<%=operation%>>
</form>
<script language="javascript" type="text/javascript">
var form=document.forms[0];
form.S_no.value="<%=S_no%>";
form.Name.value="<%=Name%>";
form.Sex.value="<%=Sex%>";
form.Politic.value="<%=Politic%>";
form.Department.value="<%=Department%>";
form.Come.value="<%=Come%>";
form.Birth.value="<%=Birth%>";
form.Memo.value="<%=Memo%>";

function check()
{
	var form=document.forms[0];
	if(form.S_no.value=="")
		alert("学号不能为空！");
	else form.submit();
}
</script>
</body>
</html>