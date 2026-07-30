package org.example.rag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmbeddingService {

    @Qualifier("googleGenAiTextEmbedding")
    private final EmbeddingModel embeddingModel;

    public float[] embed(String text) {
        return embeddingModel.embed(text);
    }
}