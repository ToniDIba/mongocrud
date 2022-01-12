package com.example.mongocrud.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;



@Data
@Document(collection = "personas")
public class Persona {

    // OJO: Este "_id" te obliga MongoDB a declararlo.
    // Si no lo haces, al intentar un insert, verás: 'No property "_id" found for type Persona'

    //public ObjectId _id;
    //private long _id;
    @Id
    public String id;
    public String usuario;
    public String password;
    public String name;
    public String surname;
    public String city;
    public String created_date;




    public Persona() { }

    public Persona(String id_personpers, String usuario, String password, String name, String surname, String city, String created_date) {
    //public Persona(int id_personpers, String usuario, String password, String name, String surname, String city, Date created_date) {
        this.id = id_personpers;
        this.usuario = usuario;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.created_date = created_date;

    }


    @Override
    public String toString() {
        return String.format("Persona{id_personpers='%d', usuario='%s',  password=%s," +
                        "                                 name='%s',     surname=%s," +
                        "                                 city='%s',     created_date=%s}\n",
                id, usuario, password, name, surname, city, created_date  );
    }


}

