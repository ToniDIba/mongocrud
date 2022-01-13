package com.example.mongocrud.repository;

import com.example.mongocrud.model.Persona;
import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IPersonaRepositorio extends MongoRepository<Persona, ObjectId> {

    Persona savePersona(Persona persona);
    Persona findOneByName(String name);
    Persona findOneBySurName(String surname);
    Persona findOneById(String id);
    Persona updateOnePersona(Persona persona);
    DeleteResult deletePersona(Persona persona);

}
