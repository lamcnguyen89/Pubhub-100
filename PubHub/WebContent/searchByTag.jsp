<!-- Header -->
<jsp:include page="header.jsp" />

<!-- Import JSTL which is a set of tags to simplify JSP development -->
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
		
		<h1>PUBHUB <small>Search Books by Tag</small></h1>
			<hr class="book-primary">
			
			<form action="BooksbyTag" method="get" class="form-horizontal">
				<div class="form-group">
					<label for="title" class="col-sm-4 control-label">
						Tag
					</label>
					<div class="col-sm-5">
						<input 
							type="text" 
							class="form-control"  
							name="tagSearch"
							required="required"
							placeholder="Enter Tag Here"
							
						/>
						
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-4 col-sm-1">
						<input type="submit" value="Search">
					</div>
				</div>
			</form>	
			
		    <table class="table table-striped table-hover table-responsive">
				<thead>
					<tr>
						<td> </td>
						<td>ISBN-13:</td>
						<td>Title:</td>
						<td>Author:</td>
						<td>Publish Date:</td>
						<td>Price:</td>
						<td></td>
						<td></td>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="book" items="${bookList}">
						<tr>
							<td></td>
							<td><c:out value="${book.isbn13}"/></td>
							<td><c:out value="${book.title}"/></td>
							<td><c:out value="${book.author}"/></td>
							<td><c:out value="${book.publishDate}"/></td>
							<td><fmt:formatNumber value="${book.price}" type="CURRENCY"/></td>
							<td></td>
							<td></td>
							

						</tr>
					</c:forEach>
				</tbody>
			</table>
			<table class="table table-striped table-hover table-responsive">
				<thead>
					<tr>
						<td> </td>
						<td>ISBN-13:</td>
						<td>Tag</td>
						<td></td>
						<td></td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="tag" items="${tag}">
						<td><c:out value="${tag.isbn13}"/></td>
						<td><c:out value="${tag.tag}" /></td>
					</c:forEach>
				
				</tbody>
			</table>
	
	</div>

</header>

<!-- Footer -->
<jsp:include page="footer.jsp" />