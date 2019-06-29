package ru.itpark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itpark.dto.EventDto;
import ru.itpark.service.EventService;

@Controller
@RequestMapping("/")
public class EventController {
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("items", service.getAll());
        return "all";
    }





    @GetMapping("/{id}")
    public String getById(@PathVariable int id, Model model) {
        model.addAttribute("item", service.getById(id));

        return "view";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("item", service.getByIdOrEmpty(id));
        return "edit";
    }


    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable int id,
            @ModelAttribute EventDto dto
    ) {
        service.save(dto);

        return "redirect:/";
    }

    @GetMapping("/{id}/remove")
    public String remove(
            @PathVariable int id,
            Model model
    ) {
        model.addAttribute("item", service.getById(id));
        return "remove";
    }

    @PostMapping("/{id}/remove")
    public String remove(
            @PathVariable int id
    ) {
        service.removeById(id);
        return "redirect:/";
    }

    @PostMapping("/{id}/like")
    public String like(
            @PathVariable int id
    ) {
        service.likeById(id);
        return "redirect:/" + id;
    }

    @PostMapping("/{id}/dislike")
    public String dislike(
            @PathVariable int id
    ) {
        service.dislikeById(id);
        return "redirect:/" + id;
    }

    @GetMapping(value = "/search", params = "name")
    public String search(@RequestParam String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("items", service.findByName(name));
        return "all";
    }


}
