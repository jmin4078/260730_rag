<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>RAG</title>
</head>
<body>
<h1>RAG</h1> <section>
    <section id="raw" style="visibility: hidden">
    - 코딩을 잘하는 방법을 알려드립니다
    - 실은 저도 잘 모릅니다
    </section>
    <p id="result"></p> </section>
<script src="https://cdn.jsdelivr.net/npm/marked/lib/marked.umd.js"></script>
<script>
    const raw = document.getElementById('raw');
    if (raw) {
        document.getElementById('result').innerHTML = marked.parse(raw.textContent);
    }
</script>
</body>
</html>