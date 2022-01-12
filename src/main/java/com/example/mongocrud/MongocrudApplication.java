package com.example.mongocrud;


import com.mongodb.client.MongoDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.UnknownHostException;


@SpringBootApplication
public class MongocrudApplication {

    public static void main (String[] args) {

        SpringApplication.run(MongocrudApplication.class, args);
    }

}




/*
@SpringBootApplication
public class MongocrudApplication {

    public static void main (String[] args) throws UnknownHostException {

        //Conexión con mi BB.DD. MongoDb "ejercicios".
        try {

            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://127.0.0.1:27017"));
            MongoDatabase mydatabase               = mongoClient.getDatabase("ejercicios");
            MongoCollection<Document> micollection = mydatabase.getCollection("personas");

            System.out.println("MongoClient asignado correctamente.");


        } catch (MongoException e) {
            e.printStackTrace();
        }

        SpringApplication.run(MongocrudApplication.class, args);
    }

} */