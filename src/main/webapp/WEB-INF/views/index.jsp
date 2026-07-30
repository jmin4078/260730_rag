<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>RAG</title>
</head>
<body>
<h1>RAG</h1>
<section>
    <%--  search  --%>
    <form method="post" action="/search">
        <input name="query" placeholder="유사도 검색">
        <button>검색</button>
    </form>
    <c:if test="${not empty search}">
        <p>${search}</p>
    </c:if>
</section>
<section>
    <%--  document  --%>
    <form method="post" action="/document">
        <input name="content" placeholder="Document Content">
        <input name="category" placeholder="Document Category">
        <button>전송</button>
    </form>
</section>
<section>
    <%--  embed  --%>
    <form method="post">
        <input name="text" placeholder="임베딩할 텍스트(문장) 입력">
        <button>전송</button>
    </form>
    <c:if test="${not empty embed}">
        <p>${embed}</p>
    </c:if>
</section>
<%--<section>--%>
<%--    <section id="raw" style="visibility: hidden">--%>
<%--        - 코딩을 잘하는 방법을 알려드립니다--%>
<%--        - 실은 저도 잘 모릅니다--%>
<%--    </section>--%>
<%--    <p id="result"></p>--%>
<%--</section>--%>
<script src="https://cdn.jsdelivr.net/npm/marked/lib/marked.umd.js"></script>
<script>
    const raw = document.querySelector('#raw');
    if (raw) {
        document.querySelector('#result').innerHTML = marked.parse(raw.textContent);
    }
</script>
</body>
</html>