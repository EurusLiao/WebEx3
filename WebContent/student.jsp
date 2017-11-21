<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*,java.text.*,java.util.*,javabean.StudentBean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无标题文档</title>
<script language="javascript" type="text/javascript">
//判断所做的操作，执行相应的操作
function check(operation){
var form=document.forms[0];
var len = form.myck.length;
var count=0;
for (i=0; i<len; i++)
{    
	if(form.myck[i].checked)
      count++;                                
}
if(operation=="新增"){
	 //设置operation表单的值为"add"
	  form.operation.value = "add";
        //设置form的action动作为carryout.jsp页面 
  		form.action="selstudentshow";
            //设置form的提交模式为post
		form.method = "post";
            //检验各表单数据是否符合要求 m  
        form.submit();
}
if(operation=="修改"){
	if(count!=1)
		alert("请选择一个要修改的学生信息！");
	else{
		form.operation.value = "modify";	
		form.action="selstudentshow";
		form.method = "post";	
		form.submit();
	}
}
if(operation=="删除"){ 
	if(count==0)
		alert("请选择要删除的学生！");
	else{
	confirm("确定要删除吗？");
    form.operation.value = "del";	
	form.action="studentshow";
	form.method = "post";
	form.submit();
	}
}
}
</script>
</head>
<%String search_txt="";
  int PageSize,RowCount,PageCount,ShowPage;
  PageSize=((Integer)request.getAttribute("pagesize")).intValue();
  RowCount=((Integer)request.getAttribute("rowcount")).intValue();
  PageCount=((Integer)request.getAttribute("pagecount")).intValue();
  ShowPage=((Integer)request.getAttribute("showpage")).intValue();  
  int p,m,n;
%>
<body>
<form name="form1" action="studentshow">
<center><font size="6">学生信息表</font></center><br>
<center>学号搜索：
<input type="text" name="search_txt" size="17" value="<%=search_txt%>">
<input type="submit" value="搜索" name="B1"></center><br>
<table align=center border=1  width=900>
<tr align=center>
<td>选择</td>
<td>学生学号</td>
<td>学生姓名</td>
<td>学生性别</td>
<td>政治面貌</td>
<td>所在系别</td>
<td>学生籍贯</td>
<td>出生日期</td>
<td>相关备注</td>
</tr>
<%
try{
List<StudentBean> vList=(List<StudentBean>)request.getAttribute("student");
for(int i=1;i<=PageSize;i++)
{
	StudentBean student=vList.get(i-1);
%>
<tr align=center>
<td><input type='checkbox' name='myck' id='myck' value='<%=student.getSno()%>'/></td>
<td><%=student.getSno()%></td>
<td><%=student.getName()%></td>
<td><%=student.getSex()%></td>
<td><%=student.getPolitic()%></td>
<td><%=student.getDepartment()%></td>
<td><%=student.getCome()%></td>
<td><%=student.getBirth()%></td>
<td><%=student.getMemo()%></td>
</tr>
<%
}
}catch(Exception e)
{
	e.printStackTrace();	
}
%>
</table><br>
<table cellspacing=1 width="100%">
 <tr>
 <td align=right>
<!--点击新增按钮执行向数据表新增记录的操作-->
      <input type="button" value="新增" name="add"  onClick="check(this.value)">
    </td>
    <td align=center>
<!--点击修改按钮执行向修改数据表记录的操作--> 
  <input type="button" value="修改" name="modify" onClick="check(this.value)">
    </td>
    <td align=left>
<!--点击删除按钮执行删除数据表记录的操作-->
    <input type="button" value="删除" name="delete" 	onClick="check(this.value)">
    </td>
  </tr>
</table>
<!--隐藏operation表单属性-->
<input type="hidden" name="operation">
<br><br>
<table border="0" width="900">
<tr valign="baseline" align="center">
<td width=600>
<%
 if (search_txt!=null && !search_txt.equals(""))
{
 %>
 以下是包含关键字<font size="6" color="#FF0000"><%=search_txt%></font>的所有信息
 <%
} 
else
{
%>
以下所有信息 
<%
}
%>
 
共<font color="#FF0000"><%=RowCount %></font>条 
共<font color="#FF0000"><%=PageCount%></font>页 
当前页为第<font color="#FF0000"><%=ShowPage%></font>页
<%
if(PageCount>1)
{
 if(ShowPage-3>0)
 {
  m=ShowPage-3;
 }
 else
 {
  m=1;
 }
  if(ShowPage+3>PageCount)
 {
  n=ShowPage+3;
 }
 else
 {
  n=PageCount;
 }
 %>
 转到页码：[
 <%
 for(p=m;p<=n;p++)
 {
  if(ShowPage==p)
  {
   %>
   <font color="#0000FF"><b><%=p%></b></font>
   <% 
  }
  else
  {
  %>
  <a
  <%
   if(ShowPage==p)
   {
   %>
    style="color:red"
   <% 
   }
   %>
   href="studentshow?ToPage=<%=p%>&search_txt<%=search_txt%>"><%=p%></a>
   <% 
  }
 }
 %>
 ]
 <%
}
 %>
 </td>
<%
if(ShowPage!=1)
{
 %>
 <td width="50"><a href="studentshow?ToPage=<%=1 %>&search_txt<%=search_txt%>">第一页 </a></td>
 <td><a href="studentshow?ToPage=<%=ShowPage-1 %>&search_txt<%=search_txt%>">上一页</a></td>
 <%
 
}
if(ShowPage!=PageCount)
{
 %>
 <td width="50"><a href="studentshow?ToPage=<%=ShowPage+1 %>&search_txt<%=search_txt%>">下一页</a></td>
 <td><a href="studentshow?ToPage=<%=PageCount %>&search_txt<%=search_txt%>">最后页</a></td>
 <%
}
%>
</tr>
</table>
</form>
</body>
</html> 