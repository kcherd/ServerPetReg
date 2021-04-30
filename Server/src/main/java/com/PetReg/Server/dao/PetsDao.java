package com.PetReg.Server.dao;

import com.PetReg.Server.models.Pet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class PetsDao {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/petreg";
    private static final String USER = "postgres";
    private static final String PASS = "";

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getPet(long id){
        Pet pet = new Pet();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        StringBuilder result = new StringBuilder();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select pets.id, pets.name, pets.age, owners.fio, owners.address, owners.tel from pets join owners on pets.owner_id = owners.id where pets.id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                pet.setId(resultSet.getInt("id"));
                pet.setName(resultSet.getString("name"));
                pet.setAge(resultSet.getInt("age"));
                pet.setFio(resultSet.getString("fio"));
                pet.setAddress(resultSet.getString("address"));
                pet.setTel(resultSet.getString("tel"));
            }

            result.append(gson.toJson(pet));
        }catch (SQLException e){
            e.printStackTrace();
        }

        return result.toString();
    }

}
