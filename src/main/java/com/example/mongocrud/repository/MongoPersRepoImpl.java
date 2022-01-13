package com.example.mongocrud.repository;

import com.example.mongocrud.model.Persona;
import com.mongodb.client.result.DeleteResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class MongoPersRepoImpl implements IPersonaRepositorio {


    private final MongoTemplate mongoTemplate;

    @Autowired
    public MongoPersRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }




    @Override
    public Persona savePersona(Persona persona) {
        mongoTemplate.save(persona);
        return persona;
    }


    @Override
    public Persona findOneByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Persona.class);
    }


    @Override
    public Persona findOneById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Persona.class);
    }


    @Override
    public Persona findOneBySurName(String surname) {
        Query query = new Query();
        query.addCriteria(Criteria.where("surname").is(surname));
        Persona personahallada = mongoTemplate.findOne(query, Persona.class);

        if (personahallada == null) {
            Persona noEncontrada = new Persona();
            noEncontrada.setUsuario("No encuentro apellido = " + surname);
            return noEncontrada;
        }

        return personahallada;

    }


    @Override
    public Persona updateOnePersona(Persona persona) {
        Persona personaModificada = new Persona();
        personaModificada = mongoTemplate.save(persona);
        return personaModificada;
    }


    @Override
    public DeleteResult deletePersona(Persona persona) {
        return mongoTemplate.remove(persona);
    }














    //------------------------------------------------------------------------------------------------------------------
    // No puedo declarar la clase "Abstracta" para eliminar estos métodos heredados que no uso.
    // Si lo hago, SpringBoot da error (no puede crear "Beans" de clases abstractas).

    @Override
    public List<Persona> findAll() {
        return mongoTemplate.findAll(Persona.class);
    }

    @Override
    public Iterable<Persona> findAllById(Iterable<ObjectId> objectIds) {
        return null;
    }


    @Override
    public <S extends Persona> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Persona> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Persona> findById(ObjectId objectId) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(ObjectId objectId) {
        return false;
    }


    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(ObjectId objectId) {

    }


    @Override
    public void delete(Persona entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends ObjectId> objectIds) {

    }


    @Override
    public void deleteAll(Iterable<? extends Persona> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Persona> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Persona> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Persona> S insert(S s) {
        return null;
    }

    @Override
    public <S extends Persona> List<S> insert(Iterable<S> iterable) {
        return null;
    }

    @Override
    public <S extends Persona> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Persona> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Persona> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Persona> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Persona> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Persona> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Persona, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

}
