package org.example.rag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
//                        .similarityThreshold(0.7) // 최소 유사도
                        .similarityThreshold(0.3) // 최소 유사도
                        .build()
        );
    }

    // import org.springframework.beans.factory.annotation.Value;
    // resources/ (prefix)
    @Value("classpath:docs/sample.txt")
    // import org.springframework.core.io.Resource;
    private Resource resource;

    // 적재 - ingest (ETL)
    public int ingest(int chunkSize) { // 하나의 chunk 사이즈가 얼마나 되는가
        // 1. Extract
        List<Document> docs = new TextReader(resource).get(); // Document 1개
        // 2. Transform - 청킹(chunking)
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(chunkSize)
                .build();
        List<Document> chunks = splitter
                .apply(docs)
                .stream()
                .map(c -> Document.builder()
                        .id(UUID.nameUUIDFromBytes( // 내용을 바탕으로 같은 내용의 청킹이 있다면 id를 UUID로 해싱해서 만들어서 해당 내용을 덮어씌우기
                                ("sample.txt:" + c.getText()).getBytes(StandardCharsets.UTF_8)).toString())
                        .text(c.getText())
                        .metadata(Map.of("source", "sample.txt", "chunkSize", chunkSize))
                        .build()
                ).toList(); // Document N개 (chunkSize로 분할된 텍스트들)
        // 3. Load <- DB 입장에서 로딩
        vectorStore.add(chunks);
//        vectorStore.delete("source == 'sample.txt'");
        return chunks.size(); // 토큰화되어서 분할된 Document N개
    }

    private final ChatClient chatClient; // ragChatClient

    public String chat(String question) {
        return chatClient.prompt().user(question).call().content();
    }
}