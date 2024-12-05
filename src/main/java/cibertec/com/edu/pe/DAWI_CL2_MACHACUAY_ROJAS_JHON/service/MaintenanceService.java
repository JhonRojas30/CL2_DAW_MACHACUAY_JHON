package cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.service;

import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.dto.FilmDetailDto;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.dto.FilmDto;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.dto.FilmEditDto;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.dto.FilmInsertDto;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.entity.Language;

import java.util.List;

public interface MaintenanceService {

    List<FilmDto> findAllFilms();
    FilmDetailDto findFilmById(int id);
    Boolean updateFilm(FilmDetailDto filmDetailDto);
    void registerFilm(FilmInsertDto filmInsertDto);
    Boolean deleteFilm(int id);
    List<Language> getAllLanguages();
}
