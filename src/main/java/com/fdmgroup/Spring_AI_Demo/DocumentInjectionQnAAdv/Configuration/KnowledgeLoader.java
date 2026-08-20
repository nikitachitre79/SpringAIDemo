package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Configuration;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
//creating chunks of document and adding to vector database
@Configuration
public class KnowledgeLoader {

    @Bean
    CommandLineRunner loadKnowledge(
            VectorStore vectorStore,
            ResourceLoader resourceLoader) {

        return args -> {

            var resource = resourceLoader
                    .getResource("classpath:Doc/Company.txt");

            var reader = new TextReader(resource);

            List<Document> documents = reader.get();

            TokenTextSplitter splitter = TokenTextSplitter.builder()
                    .withChunkSize(500)
                    .withMinChunkSizeChars(100)
                    .withMinChunkLengthToEmbed(5)
                    .withMaxNumChunks(10000)
                    .withKeepSeparator(true)
                    .build();
            List<Document> chunks =
                    splitter.apply(documents);

            vectorStore.add(chunks);

            System.out.println(
                    "Loaded " + chunks.size() + " chunks");
        };
    }
}