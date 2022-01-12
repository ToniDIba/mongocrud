package com.example.mongocrud;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


import java.net.UnknownHostException;

public class MongoCli {

    public static MongoDatabase conectar () throws UnknownHostException {

        //Conexión con mi BB.DD. MongoDb "ejercicios".
        try {

            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://127.0.0.1:27017"));
            MongoDatabase mydatabase = mongoClient.getDatabase("ejercicios");
            MongoCollection<Document> micollection = mydatabase.getCollection("personas");

            System.out.println("MongoClient asignado correctamente.");
            return mydatabase;

        } catch (MongoException e) {
            e.printStackTrace();
        }

        return null;
    }

}
