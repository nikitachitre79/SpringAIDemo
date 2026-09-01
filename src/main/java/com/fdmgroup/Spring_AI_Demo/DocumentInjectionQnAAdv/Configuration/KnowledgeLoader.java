package com.fdmgroup.Spring_AI_Demo.DocumentInjectionQnAAdv.Configuration;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//creating chunks of document and adding to vector database
@Configuration
public class KnowledgeLoader {

    @Bean
        CommandLineRunner loadKnowledge(
                        @Qualifier("vectorStore") VectorStore vectorStore,
                        @Qualifier("hrVectorStore") VectorStore hrVectorStore,
                        @Qualifier("itVectorStore") VectorStore itVectorStore,
                        @Qualifier("policyVectorStore") VectorStore policyVectorStore) {

        return args -> {

            PathMatchingResourcePatternResolver resolver =
                    new PathMatchingResourcePatternResolver();

            TokenTextSplitter splitter = TokenTextSplitter.builder()
                    .withChunkSize(500)
                    .withMinChunkSizeChars(100)
                    .withMinChunkLengthToEmbed(5)
                    .withMaxNumChunks(10000)
                    .withKeepSeparator(true)
                    .build();

            int totalChunks = 0;

            totalChunks += loadIntoStore(resolver,
                    "classpath:Doc/Company.txt",
                    vectorStore,
                    splitter);

            totalChunks += loadIntoStore(resolver,
                    "classpath:Doc/hr.txt",
                    hrVectorStore,
                    splitter);

            totalChunks += loadIntoStore(resolver,
                    "classpath:Doc/it.txt",
                    itVectorStore,
                    splitter);

            totalChunks += loadIntoStore(resolver,
                    "classpath:Doc/policy.txt",
                    policyVectorStore,
                    splitter);

            System.out.println(
                    "Total Chunks Loaded: " +
                            totalChunks);
        };
    }

    private int loadIntoStore(
            PathMatchingResourcePatternResolver resolver,
            String path,
            VectorStore targetStore,
            TokenTextSplitter splitter) throws Exception {

        Resource[] resources = resolver.getResources(path);
        int chunksLoaded = 0;

        for (Resource resource : resources) {

                TextReader reader = new TextReader(resource);

                List<Document> documents = reader.get();

                List<Document> chunks =
                        splitter.apply(documents);

                targetStore.add(chunks);

                chunksLoaded += chunks.size();

                System.out.println(
                        "Loaded " +
                                resource.getFilename() +
                                " => " +
                                chunks.size() +
                                " chunks");
            }

        return chunksLoaded;
    }
}