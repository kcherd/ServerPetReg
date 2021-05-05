package com.PetReg.Server.dao;

import com.PetReg.Server.models.Pet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

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
                    "select pets.id, pets.name, pets.birth, owners.fio, owners.address, owners.tel from pets join owners on pets.owner_id = owners.id where pets.id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                pet.setId(resultSet.getInt("id"));
                pet.setName(resultSet.getString("name"));
                pet.setBirth(resultSet.getInt("birth"));
                pet.setFio(resultSet.getString("fio"));
                pet.setAddress(resultSet.getString("address"));
                pet.setTel(resultSet.getString("tel"));
            }


            //информация о прививках
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "select name, date from vaccinations where id_pet = ?");
            preparedStatement1.setLong(1, id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            ArrayList<Pet.Vaccination> vaccinations = new ArrayList<>();

            while (resultSet1.next()){
                Pet.Vaccination vaccination = new Pet.Vaccination();
                vaccination.setName(resultSet1.getString("name"));
                vaccination.setDate(resultSet1.getDate("date"));
                vaccinations.add(vaccination);
            }

            pet.setVaccinations(vaccinations);

            result.append(gson.toJson(pet));
        }catch (SQLException e){
            e.printStackTrace();
        }

        return result.toString();
    }

    public long insertPet(String stringPet){
        Gson gson = new Gson();
        Pet pet = gson.fromJson(stringPet, Pet.class);

        long idPet = -1;
        try{
            //ищем хозяина в базе
            long idOwner = getOwnerId(pet);

            //если хозяина в базе нет, то добавляем его
            if(idOwner == -1){
                //получаем id хозина
                idOwner = countOwners() + 1;

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into owners values (?, ?, ?, ?)");
                preparedStatement.setLong(1, idOwner);
                preparedStatement.setString(2, pet.getFio());
                preparedStatement.setString(3, pet.getAddress());
                preparedStatement.setString(4, pet.getTel());

                preparedStatement.executeUpdate();
            }

            //добавляем данные о животном в базу
            idPet = countPets() + 1;

            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "insert into pets values (?, ?, ?, ?)");
            preparedStatement1.setLong(1, idPet);
            preparedStatement1.setString(2,pet.getName());
            preparedStatement1.setInt(3, pet.getBirth());
            preparedStatement1.setLong(4, idOwner);

            preparedStatement1.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return idPet;
    }

    public long getOwnerId(Pet pet){
        long idOwner = -1;
        try {
            //ищем хозяина в базе
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select id from owners where fio = ? and address = ? and tel = ?");
            preparedStatement.setString(1, pet.getFio());
            preparedStatement.setString(2, pet.getAddress());
            preparedStatement.setString(3, pet.getTel());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                idOwner = resultSet.getLong("id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return idOwner;
    }

    public long getPetId(Pet pet, long ownerId){
        long idPet = -1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select id from pets where name = ? and birth = ? and owner_id = ?");
            preparedStatement.setString(1, pet.getName());
            preparedStatement.setInt(2, pet.getBirth());
            preparedStatement.setLong(3, ownerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                idPet = resultSet.getLong("id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return idPet;
    }

    public long countOwners(){
        long countOw = -1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select count(*) from owners");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                countOw = resultSet.getLong("count");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return countOw;
    }

    public long countPets(){
        long countP = -1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select count(*) from pets");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                countP = resultSet.getLong("count");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return countP;
    }

}
