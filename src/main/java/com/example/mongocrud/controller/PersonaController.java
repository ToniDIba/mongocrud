package com.example.mongocrud.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.example.mongocrud.model.Persona;
import com.example.mongocrud.repository.MongoPersRepoImpl;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class PersonaController {

    @Autowired
    MongoPersRepoImpl personaRepositorio;


    /*
     * Añade personas a la lista:  http://localhost:8085/Personas/addnewpers
     * {
     *   "id": "17",
     *   "usuario": "usuario2",
     *   "password": "pass2",
     *   "name": "Paco",
     *   "surname": "Maravillas",
     *   "city": "Jaén",
     *   "created_date": "2002-02-02"
     * }
     * */


    @PostMapping("/Personas/addnewpers")
    public ResponseEntity<String> createPersona(@RequestBody Persona persona) {
        System.out.println("paso: " + persona.get_id());
        try {
            personaRepositorio.savePersona(new Persona(persona.get_id(), persona.getUsuario(), persona.getPassword(),
                    persona.getName(), persona.getSurname(), persona.getCity(),
                    persona.getCreated_date()));

            return new ResponseEntity<>("Persona añadida de forma correcta.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    /*
     * Busca persona por nombre:  http://localhost:8085/Personas/pornombre/Carla
     *
     * Si existe, lo muestra
     * Si no existe, se queja
     * Si no llega valor, muestra todos
     *
     * */
    @RequestMapping(value = {"/Personas/pornombre/{nombre}", "/Personas/pornombre/"})
    public ResponseEntity<List<Persona>> findByName(@PathVariable("nombre") Optional<String> nombre) {

        List<Persona> personaList = new ArrayList<Persona>();

        try {

            if (nombre.isEmpty()) {
                personaList = personaRepositorio.findAll();
            } else {
                Persona unaPersona = personaRepositorio.findOneByName(nombre.get());
                personaList.add(unaPersona);
            }

            if (personaList.get(0) == null) {
                Persona empty = new Persona();
                empty.setUsuario("No encuentro personas con nombre = " + nombre.get());
                personaList.add(empty);
                return new ResponseEntity<>(personaList, HttpStatus.OK);
            }

            return new ResponseEntity<>(personaList, HttpStatus.OK);

        } catch (Exception e) {
            Persona empty = new Persona();
            empty.setUsuario(e.getMessage());
            personaList.add(empty);
            return new ResponseEntity<>(personaList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    /*
     * Busca persona por apellido:  http://localhost:8085/Personas/porapellido/Gonzalez
     *
     * Si existe, lo muestra
     * Si no existe, se queja
     * Si no llega valor, se queja
     *
     * */
    @RequestMapping(value = {"/Personas/porapellido/{surname}", "/Personas/porapellido/"})
    public ResponseEntity<Persona> findBySurName(@PathVariable("surname") Optional<String> surname) {

        Persona unaPersona = new Persona();

        unaPersona.setUsuario("manauel");

        try {
            if (surname.isEmpty()) {
                unaPersona.setUsuario("Debe informar un apellido por el que buscar...");
            } else {
                unaPersona = personaRepositorio.findOneBySurName(surname.get());
            }

            return new ResponseEntity<>(unaPersona, HttpStatus.OK);

        } catch (Exception e) {
            unaPersona.setUsuario(e.getMessage());
            return new ResponseEntity<>(unaPersona, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    /*
     * Hace un update de columnas "name", "surname" y "city" de un persona con el "id" entrado por parámetro:
     * Por ejemplo: http://localhost:8085/Personas/modif/17
     * Si la persona no existe, retorna error
     *
     * {
     *   "name": "Tomas",
     *   "surname": "Delhort",
     *   "city": "Sanabria"
     * }
     * */

    @PutMapping("/Personas/modif/{id}")
    public ResponseEntity<String> updatePersona(@PathVariable("id") String id, @RequestBody Persona personaReq) {

        Persona unaPersona = personaRepositorio.findOneById(id);
        if (unaPersona == null) return new ResponseEntity<>("No encuentro para modificar persona con ID = " + id, HttpStatus.OK);

        //Modifico "name", "surname" y "city" con los valores informados en "RequestBody"
        try {
            unaPersona.setName(personaReq.getName());
            unaPersona.setSurname(personaReq.getSurname());
            unaPersona.setCity(personaReq.getCity());

            Persona persRepo = personaRepositorio.updateOnePersona(unaPersona);
            return new ResponseEntity<>("Persona modificada de forma correcta. Nueva ciudad: " + persRepo.getCity(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error en update: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    /*
     * Borra por número de id: http://localhost:8085/Personas/deletebyid/8
     * */
    @DeleteMapping("/Personas/deletebyid/{id}")
    public ResponseEntity<String> deletePersona(@PathVariable("id") String id) {

        try {

            Persona perso = personaRepositorio.findOneById(id);

            if (perso == null) return new ResponseEntity<>("No encuentro para borrar persona con ID = " + id, HttpStatus.OK);

            DeleteResult deleteResult;
            deleteResult = personaRepositorio.deletePersona(perso);

            if (deleteResult.getDeletedCount() == 0)
            {
                return new ResponseEntity<>("No encuentro para borrar persona con id = " + id, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Persona borrada de forma satisfactoria.", HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(" Al borrar persona con id = " + id + ": " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}