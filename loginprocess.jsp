<%@page import="bean.LoginDao"%>
<jsp:useBean id="obj" class="bean.LoginBean"/>

<jsp:setProperty property="*" name="obj"/>

<%
int status=LoginDao.validate(obj);
if(status == 0){
	session.setAttribute("session","TRUE");
	%>
	<jsp:include page="initialize.jsp"></jsp:include>
	<%
}
else if (status == 1)
{
	session.setAttribute("session","TRUE");
	%>
	<jsp:include page="Transaction.jsp"></jsp:include>
	<%
	
}
else
{
	out.print("Sorry, email or password error");
	%>
	<jsp:include page="index.jsp"></jsp:include>
	<%
}
%>