<%@page import="org.sandyhookproject.entity.GunTrace"%>
<%@page import="org.sandyhookproject.tableLoader.TracingTable"%>
<%@page import="org.sandyhookproject.dataLoader.DataLoader"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
<%

	TracingTable tracingTable = new TracingTable();

	while (tracingTable.hasNext()) {
		GunTrace trace = tracingTable.next();
		out.write("<tr>");
		out.write("<td>" + Integer.toString(trace.getTraceYear()) + "</td>");
		out.write("<td>" + trace.getTracedTo().getName() + "</td>");
		out.write("<td>" + trace.getStateTracedFrom().getName() + "</td>");
		out.write("<td>" + Long.toString(trace.getGunsTraced()) + "</td>");
		out.write("</tr>");
	}

%>
</table>
</body>
</html>