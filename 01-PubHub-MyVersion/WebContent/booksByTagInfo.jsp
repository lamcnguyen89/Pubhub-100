<!-- Header -->
<jsp:include page="header.jsp" />

<!-- JSTL Includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <!-- The core tag provides variable support, URL management, flow control, etc..  -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> <!-- The formatting tag provides support for message formatting, number and date formatting, etc.. -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <!-- The functions tag provides support for string manipulation and string length. -->

<!-- Just some stuff you need -->
<header>
	<div class="container">
	
	<c:choose>
		<c:when test="${not empty message}">
			<p class="alert ${messageClass}">${message}</p>
			<% 
				session.setAttribute("message", null);
				session.setAttribute("messageClass", null);
			%>
		</c:when>
	</c:choose>
	
	
<h1>PUBHUB<small>Search Results For Books With <c:out value="${tag.tag }" /> Tag</small></h1>
	<hr class="book-primary">
	
	<table class="table table-striped table-hover table-responsive pubhub-datatable">
	
		<thead>
			<tr>
				<td>ISBN-13:</td>
				<td>Title:</td>
				<td>Author:</td>
				<td>Publish Date:</td>
				<td></td>
				<td></td>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach var="book" items="${books}">
				<tr>
					<td><c:out value="${book.isbn13}"/></td>
					<td><c:out value="${book.title}"/></td>
					<td><c:out value="${book.author}"/></td>
					<td><c:out value="${book.publishDate}"/></td>
					<td><fmt:formatNumber value="${book.price}" type="CURRENCY"/></td>
					
					<td>
						<form action="ViewBookDetails?isbn=${book.isbn13}" method="get">
							<input type="hidden" name="isbn13" value="${book.isbn13}">
							<button class="btn btn-primary">Details</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	
	</table>	 
	
	
	</div>
</header>