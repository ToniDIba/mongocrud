package com.example.mongocrud.controller;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.example.mongocrud.MongoCli;
import com.example.mongocrud.model.Persona;
import com.example.mongocrud.repository.MongoPersRepoImpl;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
public class PersonaController {

    @Autowired
    MongoPersRepoImpl personaRepositorio;

    //public PersonaController() throws UnknownHostException { }
    //MongoDatabase mydatabase = MongoCli.conectar();
    //MongoCollection<Document> micollection = mydatabase.getCollection("personas");
    //MongoCollection<Document> micollection;

    // http://localhost:8085/Personas/addnewpers
    @PostMapping("/Personas/addnewpers")
    public ResponseEntity<String> createPersona(@RequestBody Persona persona) {
        System.out.println("paso");
        try {
            personaRepositorio.savePersona(new Persona(persona.getId(), persona.getUsuario(), persona.getPassword(),
                    persona.getName(), persona.getSurname(), persona.getCity(),
                    persona.getCreated_date()));

            return new ResponseEntity<>("Persona añadida de forma correcta.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /*
     * Busca persona por nombre:  http://localhost:8085/Personas/pornombre/Carla
     * */
    @GetMapping("/Personas/pornombre/{nombre}")
    //@RequestMapping(value = { "/Personas/pornombre/{nombre}","/Personas/pornombre/" })
    public ResponseEntity<List<Persona>> findByName(@PathVariable("nombre") Optional<String> nombre) {

        System.out.println("Entrado: " + nombre);
        List<Persona> personaList = new ArrayList<Persona>();

        try {

            if (nombre.isEmpty()) {
                //personaRepositorio.getAllPersona().forEach(personaList::add);
                personaList = personaRepositorio.findAll();
            } else {
                Persona unaPersona = personaRepositorio.findOneByName(nombre.get());
                personaList.add(unaPersona);
            }

            if (personaList.get(0) == null) {
                Persona empty = new Persona();
                empty.setUsuario("No encuentro personas con nombre = " + nombre.get());
                personaList.add(empty);
                //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
     * Busca personas cuyo apellido contenga una cadena pasada por parámetro "substring"
     * Por ejemplo, para: http://localhost:8085/Personas/apellidocontiene/?substring=Mart
     * ...retornará todos los "Martín", "Martinez", "Martillo"... que existan en la tabla.
     *
     * Si el parámetro "substring" llega vacío:
     * http://localhost:8085/Personas/apellidocontiene/
     * ...se retornarán todas las filas de la tabla
     * */

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @GetMapping("/Personas/apellidocontiene/{substring}")
    public ResponseEntity<List<Persona>> getAllPersonas(@RequestParam(required = false) String substring) {

        List<Persona> personaList = new ArrayList<Persona>();

        try {

            if (substring == null)
                personaRepositorio.getAllPersona().forEach(personaList::add);
            else
                personaRepositorio.findBySurName(substring).forEach(personaList::add);

            if (personaList.isEmpty()) {
                Persona empty = new Persona();
                empty.setUsuario("No encuentro apellidos que contengan = " + substring);
                personaList.add(empty);
                //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
     * Hace un update de columnas "name", "surname" y "city" de un persona con el "id" entrado por parámetro:
     * Por ejemplo: http://localhost:8085/Personas/modif/1
     * Si la persona no existe, retorna: "No encuentro persona con id = nnn"
     *
     * {
     *
     *   "name": "Tomas",
     *   "surname": "Delhort",
     *   "city": "Sanabria"
     *
     * }
     * */

    @PutMapping("/Personas/modif/{id_personpers}")
    public ResponseEntity<String> updatePersona(@PathVariable("id_personpers") int id_personpers, @RequestBody Persona Persona) {

        Persona perso = personaRepositorio.findOneById(id_personpers);

        if (perso == null) {
            return new ResponseEntity<>("No encuentro persona con ID = " + id_personpers, HttpStatus.OK);
        }

        perso.setName(Persona.getName());
        perso.setSurname(Persona.getSurname());
        perso.setCity(Persona.getCity());

        Persona persRepo = personaRepositorio.updateOnePersona(perso);
        return new ResponseEntity<>("Persona modificada de forma correcta.", HttpStatus.OK);

    }


    /*
     * Borra por número de id: http://localhost:8085/Personas/deletebyid/8
     * */
    @DeleteMapping("/Personas/deletebyid/{id_personpers}")
    public ResponseEntity<String> deletePersona(@PathVariable("id_personpers") int id_personpers) {
        try {

            Persona perso = personaRepositorio.findOneById(id_personpers);

            if (perso == null) {
                return new ResponseEntity<>("No encuentro para borrar persona con ID = " + id_personpers, HttpStatus.OK);
            }

            personaRepositorio.deletePersona(perso);
            int result = 4;

            if (result == 0) {
                return new ResponseEntity<>("No encuentro para borrar persona con id = " + id_personpers, HttpStatus.OK);
            }
            return new ResponseEntity<>("Persona borrada de forma satisfactoria.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Persona con id = " + id_personpers + ": " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}