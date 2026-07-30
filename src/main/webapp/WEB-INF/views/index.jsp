<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>RAG</title>
    <script src="https://cdn.jsdelivr.net/npm/marked/lib/marked.umd.js"></script>
</head>
<body>
<h1>RAG</h1>
<section>
    <%--  chat  --%>
    <form method="post" action="/chat">
        <input name="question" placeholder="RAG로 질문">
        <button>검색</button>
    </form>
    <c:if test="${not empty chat}">
        <p id="rawChat" style="visibility: hidden">${chat}</p>
        <p id="chat"></p>
        <script>
            const raw = document.querySelector('#rawChat');
            if (raw) {
                document.querySelector('#chat').innerHTML = marked.parse(raw.textContent);
            }
        </script>
    </c:if>
</section>
<section>
    <%--  ingest  --%>
    <form method="post" action="/ingest">
        <select name="chunkSize">
            <option value="200">200 (작게)</option>
            <option value="1000">1000 (크게)</option>
        </select>
        <button>주입</button>
    </form>
    <c:if test="${not empty chunks}">
        <p>결과 사이즈 : ${chunks}</p>
    </c:if>
</section>
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
</body>
</html>