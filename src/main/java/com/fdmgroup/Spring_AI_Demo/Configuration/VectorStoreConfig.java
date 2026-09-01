package com.fdmgroup.Spring_AI_Demo.Configuration;

 
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel)
                .build();
    }
    
@Bean
VectorStore hrVectorStore(
        EmbeddingModel embeddingModel) {

    return SimpleVectorStore.builder(embeddingModel)
            .build();
}

@Bean
VectorStore itVectorStore(
        EmbeddingModel embeddingModel) {

    return SimpleVectorStore.builder(embeddingModel)
            .build();
}

@Bean
VectorStore policyVectorStore(
        EmbeddingModel embeddingModel) {

    return SimpleVectorStore.builder(embeddingModel)
            .build();
}

}