//package com.fdmgroup.Spring_AI_Demo.Configuration;
//
//import java.util.List;
//
//import org.springframework.ai.document.Document;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class KnowledgeBaseLoader {
//
//    @Bean
//    CommandLineRunner loadKnowledge(
//            VectorStore vectorStore) {
//
//        return args -> {
//
//            List<Document> docs = List.of(
//                    new Document("""
//                         Skills Lab is Microsoft's learning organization.
//                         """),
//
//                    new Document("""
//                         Nikita Chitre is a Skills Lab Coach.
//                         """),
//
//                    new Document("""
//                         Spring AI integrates LLMs with Spring applications.
//                         """)
//            );
//
//            vectorStore.add(docs);
//
//            System.out.println("Knowledge Base Loaded");
//        };
//    }
//}
