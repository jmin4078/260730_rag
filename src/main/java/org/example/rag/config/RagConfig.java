package org.example.rag.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // <- Scan
public class RagConfig {
    // Bean
    @Bean
    public ChatClient ragChatClient(
            ChatClient.Builder builder, // groq? <- 자동으로 chatmodel을 넣어줌
            VectorStore vectorStore
    ) {
        return builder
                .defaultSystem("""
                        주어진 컨텍스트를 기반으로 할 것.
                        컨텍스트에 없는 내용은 지어내지 말고 '찾을 수 없다'라고 답할 것.
                        답변은 마크다운으로 작성
                        """)
                .defaultAdvisors(
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(SearchRequest
                                        .builder()
                                        .topK(4)
                                        .similarityThreshold(0.5)
                                        .build()).build()
                )
                .build();
    }
}