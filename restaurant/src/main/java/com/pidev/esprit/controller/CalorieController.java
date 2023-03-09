package com.pidev.esprit.controller;

import com.pidev.esprit.model.Menu;
import com.pidev.esprit.service.CalorieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calories")
public class CalorieController {
    private final CalorieService calorieService;

    public CalorieController(CalorieService calorieService) {
        this.calorieService = calorieService;
    }


    @GetMapping("/{mealName}")
    public ResponseEntity<String> getCaloriePercentage(@PathVariable String mealName,
                                                       @RequestParam double height,
                                                       @RequestParam double weight,
                                                       @RequestParam int age,
                                                       @RequestParam boolean isMale) {
        String percentage = calorieService.getCaloriePercentage(mealName, height, weight, age, isMale);
        return ResponseEntity.ok(percentage);
    }
}
