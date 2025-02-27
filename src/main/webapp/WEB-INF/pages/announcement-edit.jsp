<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告管理</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/suneditor@2.41.3/dist/css/suneditor.min.css">
</head>
<body>
<div class="container mt-4" style="max-width: 600px; margin-left: 0;">
    
    <form action="${pageContext.request.contextPath}/announcement/save" method="post" enctype="multipart/form-data">
    
        <input type="hidden" name="id" value="${announcement.id}" />

        <div class="mb-3 row">
            <label for="title" class="col-sm-3 col-form-label text-start">標題</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="title" name="title" 
                    value="${announcement.title}" required>
            </div>
        </div>

        <div class="mb-3 row">
            <label for="publishDate" class="col-sm-3 col-form-label text-start">發佈日期</label>
            <div class="col-sm-9">
                <input type="date" class="form-control" id="publishDate" name="publishDate" 
                    value="${announcement.publishDate}" required>
            </div>
        </div>

        <div class="mb-3 row">
            <label for="endDate" class="col-sm-3 col-form-label text-start">截止日期</label>
            <div class="col-sm-9">
                <input type="date" class="form-control" id="endDate" name="endDate" 
                    value="${announcement.endDate}" required>
            </div>
        </div>

        <div class="mb-3 row">
            <label for="publisher" class="col-sm-3 col-form-label text-start">公佈者</label>
            <div class="col-sm-9">
                
                        
        		 <input type="text" class="form-control" id="publisher" value="${announcement.publisher.username}" readonly>
                 <input type="hidden" name="publisher.id" value="${announcement.publisher.id}">
            </div>
        </div>

        <div class="mb-3 row">
            <label for="editor" class="col-sm-3 col-form-label text-start">內容</label>
            <div class="col-sm-9">
                <textarea id="editor" name="content">${announcement.content}</textarea>
            </div>
        </div>

        <div class="mb-3 row">
            <label for="fileUpload" class="col-sm-3 col-form-label text-start">上傳檔案</label>
            <div class="col-sm-9">
                <input type="file" class="form-control" id="fileUpload" name="fileUpload">
            </div>
        </div>
        
        <div>
        <label for="fileUpload">檔案：</label>
        <c:if test="${not empty announcement.attachment}">
            <!-- 顯示檔案下載連結 -->
            <a href="${pageContext.request.contextPath}/announcement/download/${announcement.id}" target="_blank">下載檔案</a>
        </c:if>
        <c:if test="${empty announcement.attachment}">
            <!-- 沒有檔案 -->
            <span>沒有檔案</span>
        </c:if>
    </div>

        <div class="mb-3 d-flex justify-content-center">
            <button type="submit" class="btn btn-primary">儲存</button>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/suneditor@2.41.3/dist/suneditor.min.js"></script>

<script>
document.addEventListener("DOMContentLoaded", function() {
    const editor = SUNEDITOR.create(document.getElementById('editor'), {
        width: '100%',
        height: 300,
        buttonList: [
            ['undo', 'redo', 'font', 'fontSize', 'bold', 'underline', 'italic', 'align']
        ]
    });

    // 手動同步 content
    editor.onChange = function() {
        document.getElementById('editor').value = editor.getContents();  // 更新 textarea 的值
    };
});
    
    
</script>
</body>
</html>