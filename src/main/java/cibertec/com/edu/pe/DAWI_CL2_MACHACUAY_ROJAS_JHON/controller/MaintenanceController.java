package cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.controller;

import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.dto.FilmDetailDto;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.dto.FilmDto;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.dto.FilmInsertDto;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.entity.Language;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/maintenance")

public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/start")
    public String start(Model model) {

        List<FilmDto> films = maintenanceService.findAllFilms();
        model.addAttribute("films", films);
        return "maintenance";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findFilmById(id);
        model.addAttribute("film", filmDetailDto);
        return "maintenance_detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findFilmById(id);
        List<Language> languages = maintenanceService.getAllLanguages();

        List<String> specialFeaturesList = Arrays.asList(filmDetailDto.specialFeatures().split(",\\s*"));
        model.addAttribute("film", filmDetailDto);
        model.addAttribute("languages", languages);
        model.addAttribute("specialFeaturesList", specialFeaturesList);
        return "maintenance_edit";
    }

    @PostMapping("/edit-confirm")
    public String editConfirm(@ModelAttribute FilmDetailDto filmDetailDto, Model model) {
        maintenanceService.updateFilm(filmDetailDto);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        FilmInsertDto filmInsertDto = new FilmInsertDto(
                "",
                "",
                null,
                null,
                "",
                0,
                0.0,
                null,
                0.0,
                "",
                "",
                new Date()
        );

        List<Language> languages = maintenanceService.getAllLanguages();

        model.addAttribute("filmInsertDto", filmInsertDto);
        model.addAttribute("languages", languages);

        return "maintenance_register";

    }
        @PostMapping("/register")
        public String insertFilm (FilmInsertDto filmInsertDto){
            maintenanceService.registerFilm(filmInsertDto);
            return "redirect:/maintenance/start";
        }


    @PostMapping("/delete/{id}")
    public String deleteFilm(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        // Llama al servicio para eliminar la película
        Boolean isDeleted = maintenanceService.deleteFilm(id);

        // Verifica si la eliminación fue exitosa
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "Film deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No associated inventory found. Film not deleted.");
        }

        // Redirige a la página de inicio de mantenimiento después de la eliminación
        return "redirect:/maintenance/start";
    }
    }

