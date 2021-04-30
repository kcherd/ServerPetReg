package com.PetReg.Server.controllers;

import com.PetReg.Server.dao.PetsDao;
import com.PetReg.Server.models.Pet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PetController {
    private final PetsDao petsDao;

    public PetController(PetsDao petsDao){
        this.petsDao = petsDao;
    }

    @GetMapping(value = "/pet", produces = "application/json")
    @ResponseBody
    public String getPet(@RequestParam("id") int id, Model model){
        //model.addAttribute("pet", petsDao.getPet(id));
        return petsDao.getPet(id);
    }

}
