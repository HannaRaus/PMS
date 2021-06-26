<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>PMS</title>
        <script src="https://kit.fontawesome.com/1121c369ff.js" crossorigin="anonymous"></script>
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/table.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/style.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/tooltip.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/button.css" TITLE="style" />
    </head>
    <body>
        <c:import url="/view/header.jsp"/>
        <div class="container">
        	<table>
        		<thead>
        			<tr>
        				<th>id</th>
        				<th>name</th>
        				<th>industry</th>
        				<th>companies</th>
        				<th>projects</th>
        				<th colspan="3" align="center"></th>
        			</tr>
        		</thead>
        		<tbody>
                 <c:forEach var="customer" items="${entities}">
                     <tr>
                         <td>${customer.id}</td>
                         <td>${customer.name}</td>
                         <td>${customer.industry}</td>
                         <td>
                             <div class="tooltip">${customer.companies.size()}
                                <span class="tooltiptext">${customer.companies}</span>
                             </div>
                         </td>
                         <td>
                             <div class="tooltip">${customer.projects.size()}
                                <span class="tooltiptext">${customer.projects}</span>
                             </div>
                         </td>
                         <td> <a href="/customers/details?id=${customer.id}"><button>Details</button></a></td>
                         <td> <a href="/customers/edit?id=${customer.id}"><button>Update</button></a></td>
                         <td> <a href="/customers/delete?id=${customer.id}"><button>Delete</button></a></td>
                     </tr>
                 </c:forEach>
        		</tbody>
        	</table>
        </div>
    </body>
</html>
