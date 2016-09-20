<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
	 window.onload=function(){
		var screenHigth=window.screen.height;
		var node=document.getElementById("tar");
		var divHeight=node.offsetHeight;
		var temp=(screenHigth-divHeight)/2;
		node.style.marginTop=temp;
	} 
</script>

<div id="tar" style="text-align:center">
	
	<font size="7" color="red">授权失败</font>
	

</div>
