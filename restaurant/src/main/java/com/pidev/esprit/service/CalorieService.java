package com.pidev.esprit.service;

import com.pidev.esprit.model.Menu;
import com.pidev.esprit.repository.MenuRepository;
import org.springframework.stereotype.Service;

@Service
public class CalorieService {
    private final MenuRepository menuRepository;

    public CalorieService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public String getCaloriePercentage(String mealName, double height, double weight, int age, boolean isMale) {
        Menu meal = menuRepository.findByName(mealName);
        double dailyCalories = calculateDailyCalories(height, weight, age, isMale);
        double mealCalories = meal.getCalories();
        return calculateMealPercentage(mealCalories, dailyCalories);
    }

    private double calculateDailyCalories(double height, double weight, int age, boolean isMale) {
        double activityFactor = 1.5;
        double bmr;
        if (isMale) {
            //Harris-Benedict equation
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
        return bmr * activityFactor;
    }

    private String calculateMealPercentage(double mealCalories, double dailyCalories) {
        double percentage = (mealCalories / dailyCalories) * 100;
        return String.format("This meal will consume %.0f%% of your daily calories", percentage);
    }
}

