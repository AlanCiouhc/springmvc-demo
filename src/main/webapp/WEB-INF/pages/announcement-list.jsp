<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告列表</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>

</head>
<body>
<nav class="navbar navbar-light bg-light">
  <div class="container-fluid">
    <h6>瀏覽公布事項</h6>
  </div>
</nav>
  <table class="table">
  <thead>
    <tr>
      <th scope="col">標題</th>
      <th scope="col">發佈日期</th>
      <th scope="col">截止日期</th>   
      <th scope="col"></th> 
      <th scope="col"></th>           
    </tr>
  </thead>
  
  <tbody>
  <c:forEach var="announcement" items="${announcements}">
    <tr>
      <th scope="row">${announcement.title}</th>
      <td>${announcement.publishDate}</td>
      <td>${announcement.endDate}</td>
      <td>
      <div class="d-flex justify-content-center gap-2">
      <form action="announcement/edit" method="post">
                <input type="hidden" name="id" value="${announcement.id}" />
                <button class="btn btn-primary" type="submit">修改</button>
            </form>
      
      <form action="announcement/delete" method="post">
                <input type="hidden" name="id" value="${announcement.id}" />
                <button class="btn btn-danger" type="submit">刪除</button>
            </form>
            </div>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<!-- 分頁導航 -->
<div class="d-flex justify-content-center gap-2">
  <c:if test="${totalPages > 1}">
    <!-- 上一頁 -->
    <c:if test="${currentPage > 0}">
      <a href="?page=${currentPage - 1}&size=5" class="btn btn-secondary">上一頁</a>
    </c:if>

    <!-- 頁碼導航 -->
    <c:forEach var="i" begin="0" end="${totalPages - 1}">
      <c:choose>
        <c:when test="${i == currentPage}">
          <strong>${i + 1}</strong> <!-- 當前頁顯示為加粗 -->
        </c:when>
        <c:otherwise>
          <a href="?page=${i}&size=5" class="btn btn-light">${i + 1}</a> <!-- 其他頁顯示超鏈接 -->
        </c:otherwise>
      </c:choose>
    </c:forEach>

    <!-- 下一頁 -->
    <c:if test="${currentPage + 1 < totalPages}">
      <a href="?page=${currentPage + 1}&size=5" class="btn btn-secondary">下一頁</a>
    </c:if>
  </c:if>
</div>

<!-- 新增公告按鈕 -->
<div class="d-flex justify-content-center gap-2 mt-3">
  <form action="announcement/edit" method="post">
    <button class="btn btn-primary" type="submit">新增公告</button>
  </form>
</div>

</body>
</html>
