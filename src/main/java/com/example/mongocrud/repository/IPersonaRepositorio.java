package com.example.mongocrud.repository;

import com.example.mongocrud.model.Persona;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IPersonaRepositorio extends MongoRepository<Persona, ObjectId> {

    Persona savePersona(Persona persona);
    List<Persona> getAllPersona();
    List<Persona> findBySurName(String name); //Varias personas con el mismo apellido
    Persona updateOnePersona(Persona persona);
    Persona findOneByName(String name);
    Persona findOneById(int id);
    void deletePersona(Persona persona);


    // List<Persona> findByBirthDateAfter(Date date);
    // List<Persona> findByAgeRange(int lowerBound, int upperBound);
    // List<Persona> findByFavoriteBooks(String favoriteBook);
    // void updateMultiplePersonaAge();
    // List<Persona> getAllPersonaPaginated(int pageNumber, int pageSize);




}
