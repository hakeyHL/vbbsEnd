<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div>
   <form action="${lasturl}" method="post" >
   		<c:forEach items="${dataMap}" var="map">
   			<c:forEach items="${map.value }" var="val">
   				<input name="${map.key }" value="${val}" checked="checked" />
   			</c:forEach>
   		</c:forEach>
   </form>
	<script type="text/javascript">
	document.forms[0].submit();	
	</script>
   
</div>

