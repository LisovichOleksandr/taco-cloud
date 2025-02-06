package ua.lisovich.tacos.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.lisovich.tacos.entity.Ingredient;
import ua.lisovich.tacos.entity.TypeIngredient;
import ua.lisovich.tacos.entity.Taco;
import ua.lisovich.tacos.entity.TacoOrder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    @ModelAttribute
    public void addIngredientToModel(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", TypeIngredient.WRAP),
                new Ingredient("COTO", "Corn Tortilla", TypeIngredient.WRAP),
                new Ingredient("GRBF", "Ground Beef", TypeIngredient.PROTEIN),
                new Ingredient("CARN", "Carnitas", TypeIngredient.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", TypeIngredient.VEGGIES),
                new Ingredient("LETC", "Lettuce", TypeIngredient.VEGGIES),
                new Ingredient("CHED", "Cheddar", TypeIngredient.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", TypeIngredient.CHEESE),
                new Ingredient("SLSA", "Salsa", TypeIngredient.SAUCE),
                new Ingredient("SRCR", "Sour Cream", TypeIngredient.SAUCE)
        );

        TypeIngredient[] types = TypeIngredient.values();
        for (TypeIngredient type: types){
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(){
        return "design";
    }

    // Вспомогательний метод для фильтрации по типу
    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, TypeIngredient type){
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processTaco(Taco taco,
                              @ModelAttribute TacoOrder tacoOrder,
                              Model model){
        tacoOrder.addTaco(taco);
//        model.addAttribute("tacoOrder", tacoOrder);
        log.info("Processing taco: {}", taco);
        log.info("Processing taco order: {}", tacoOrder);
        return "redirect:/orders/current";
    }

}
