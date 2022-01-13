package com.example.mongocrud.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "personas")
public class Persona {

    @Id
    public String id;
    public String usuario;
    public String password;
    public String name;
    public String surname;
    public String city;
    public String created_date;




    public Persona() { }

    public Persona(String id, String usuario, String password, String name, String surname, String city, String created_date) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.created_date = created_date;

    }


    @Override
    public String toString() {
        return String.format("Persona{id='%s',   usuario='%s',  password=%s," +
                        "             name='%s', surname=%s," +
                        "             city='%s', created_date=%s }\n",
                id, usuario, password, name, surname, city, created_date  );
    }

}

