package com.PetReg.Server.controllers;

import com.PetReg.Server.dao.PetsDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PetController {
    private final PetsDao petsDao;

    public PetController(PetsDao petsDao){
        this.petsDao = petsDao;
    }

    @GetMapping(value = "/pet", produces = "application/json")
    @ResponseBody
    public String getPet(@RequestParam("id") int id, Model model){
        return petsDao.getPet(id);
    }

    @PostMapping(value = "/pet", produces = "application/json")
    @ResponseBody
    public long insertUser(@RequestParam String pet){
       return petsDao.insertPet(pet);
    }

    @GetMapping("/")
    public String testPostRequest(Model model){
        return "pet";
    }

}