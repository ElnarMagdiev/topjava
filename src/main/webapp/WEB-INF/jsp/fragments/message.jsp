<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
    const i18n = {};
    i18n["addTitle"] = '<spring:message code="${param.paramName}.add"/>';
    i18n["editTitle"] = '<spring:message code="${param.paramName}.edit"/>';

    <c:forEach var="key" items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm"}%>'>
        i18n["${key}"] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>