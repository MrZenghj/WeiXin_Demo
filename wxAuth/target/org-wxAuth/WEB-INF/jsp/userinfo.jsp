<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<body>
    <h2>用户信息</h2>
    <img src="${userInfo.headimgurl}" alt="图片"/><br />
    昵称：${userInfo.nickname}<br />
    省份：${userInfo.province}<br />
    国家：${userInfo.country}<br />
    <c:if test="${t.SEX eq '1'}">
        性别：男
    </c:if>
    <c:else>
        性别：男
    </c:else>


</body>
</html>
