<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>PMS</title>
        <script src="https://kit.fontawesome.com/1121c369ff.js" crossorigin="anonymous"></script>
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/table.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/style.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/popup.css" TITLE="style" />
        <LINK REL="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/tooltip.css" TITLE="style" />
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
        				<th></th>
        			</tr>
        		</thead>
        		<tbody>
                 <c:forEach var="customer" items="${customers}">
                     <tr>
                         <td>${customer.id}</td>
                         <td>${customer.name}</td>
                         <td>${customer.industry}</td>
                         <td>
                             <div class="popup" onclick="display(${customer.id})">${customer.companies.size()}
                               <span class="popuptext" id="myPopup${customer.id}">${customer.companies}</span>
                             </div>
                         </td>
                         <td>
                             <div class="tooltip" style=z-index: 10;>${customer.projects.size()}
                               <span class="tooltiptext">${customer.projects}</span>
                             </div>
                         </td>
                         <td> <a href="">
                                 <button>Update</button>
                              </a>
                         </td>
                     </tr>
                 </c:forEach>
        		</tbody>
        	</table>
        </div>
        <script>
            function display(row) {
              var txt= "myPopup" + row;
              var popup = document.getElementById(txt);
              popup.classList.toggle("show");
            }
        </script>
    </body>
</html>