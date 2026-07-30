package org.example.rag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentService {
    // import org.springframework.ai.vectorstore.VectorStore;
    private final VectorStore vectorStore;

    // import org.springframework.ai.document.Document;
    public void save(String content, String category) {
        Document doc = new Document(content,
                Map.of(
                        "category", category,
                        "source", "manual"));
        // 여기까지는 vector가 임베딩 안 되어 있는데...
        vectorStore.add(List.of(doc)); // 알아서 임베딩
    }

    public List<Document> search(String query) {
//        return vectorStore.similaritySearch(query);
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(4) // 조회할 최대 문서 수
                        .similarityThreshold(0.7) // 최소 유사도
                        .build()
        );
    }
}