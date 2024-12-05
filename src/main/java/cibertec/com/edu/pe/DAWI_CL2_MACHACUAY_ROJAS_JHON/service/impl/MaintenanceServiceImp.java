package cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.service.impl;


import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.dto.FilmDetailDto;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.dto.FilmDto;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.dto.FilmInsertDto;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.entity.Film;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.entity.Inventory;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.entity.Language;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.entity.Rental;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.repository.FilmRepository;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.repository.InventoryRepository;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.repository.LanguageRepository;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.repository.RentalRepository;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.service.MaintenanceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImp implements MaintenanceService {

    @Autowired
    FilmRepository filmRepository;
    @Autowired
   LanguageRepository languageRepository;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    RentalRepository rentalRepository;

    @Override
    public List<FilmDto> findAllFilms() {
        List<FilmDto> films = new ArrayList<FilmDto>();
        Iterable<Film> iterable = filmRepository.findAll();
        iterable.forEach(film -> {
            FilmDto filmDto = new FilmDto(film.getFilmId(),
                    film.getTitle(),
                    film.getLanguage().getName(),
                    film.getRentalDuration(),
                    film.getRentalRate());
            films.add(filmDto);
        });
        return films;
    }

    @Override
    public FilmDetailDto findFilmById(int id) {
        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(film -> new FilmDetailDto(film.getFilmId(),
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getLanguage().getLanguageId(),
                film.getLanguage().getName(),
                film.getRentalDuration(),
                film.getRentalRate(),
                film.getLength(),
                film.getReplacementCost(),
                film.getRating(),
                film.getSpecialFeatures(),
                film.getLastUpdate())
        ).orElse(null);


    }

    @Override
    public Boolean updateFilm(FilmDetailDto filmDetailDto) {
        Optional<Film> optional = filmRepository.findById(filmDetailDto.filmId());
        Language language = languageRepository.findById(filmDetailDto.languageId())
                .orElseThrow(() -> new RuntimeException("Language not found"));
        return optional.map(
                film -> {
                    film.setTitle(filmDetailDto.title());
                    film.setDescription(filmDetailDto.description());
                    film.setReleaseYear(filmDetailDto.releaseYear());
                    film.setLanguage(language);
                    film.setRentalDuration(filmDetailDto.rentalDuration());
                    film.setRentalRate(filmDetailDto.rentalRate());
                    film.setLength(filmDetailDto.length());
                    film.setReplacementCost(filmDetailDto.replacementCost());
                    film.setRating(filmDetailDto.rating());
                    film.setSpecialFeatures(filmDetailDto.specialFeatures());
                    film.setLastUpdate(new Date());
                    filmRepository.save(film);
                    return true;
                }
        ).orElse(false);
    }



    @Override
    public void registerFilm(FilmInsertDto filmInsertDto) {
        Film film = new Film();
        film.setTitle(filmInsertDto.title());
        film.setDescription(filmInsertDto.description());
        film.setReleaseYear(filmInsertDto.releaseYear());
        film.setRentalDuration(filmInsertDto.rentalDuration());
        film.setRentalRate(filmInsertDto.rentalRate());
        film.setLength(filmInsertDto.length());
        film.setReplacementCost(filmInsertDto.replacementCost());
        film.setRating(filmInsertDto.rating());
        film.setSpecialFeatures(filmInsertDto.specialFeatures());

        Language language = languageRepository.findById(filmInsertDto.languageId())
                .orElseThrow(() -> new IllegalArgumentException("Idioma no válido"));
        film.setLanguage(language);
        film.setLastUpdate(new Date());

        filmRepository.save(film);
    }


    @Override
    @Transactional
    public Boolean deleteFilm(int id) {
        System.out.println("Attempting to delete film with id: " + id);

        // Verificar si el film existe
        if (!filmRepository.existsById(id)) {
            return false; // Indica que el film no fue encontrado
        }
        // Primero, buscamos los Inventory asociados al Film
        List<Inventory> inventories = inventoryRepository.findByFilm_FilmId(id);
        // Eliminar todos los Rentals asociados a los Inventory encontrados
        for (Inventory inventory : inventories) {
            List<Rental> rentals = rentalRepository.findByInventory(inventory);
            rentalRepository.deleteAll(rentals); // Eliminar todos los rentals de una vez
        }
        // Ahora eliminar los Inventory
        inventoryRepository.deleteAll(inventories); // Eliminar todos los inventory de una vez
        // Finalmente, eliminar el Film
        filmRepository.deleteById(id);
        return true; // Indica que la eliminación fue exitosa
    }
    @Override
    public List<Language> getAllLanguages() {
        return (List<Language>) languageRepository.findAll();
    }
}
