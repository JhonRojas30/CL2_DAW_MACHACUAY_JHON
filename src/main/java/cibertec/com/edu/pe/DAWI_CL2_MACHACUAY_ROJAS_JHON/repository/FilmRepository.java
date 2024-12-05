package cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.repository;

import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.entity.Film;
import jakarta.persistence.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.repository.CrudRepository;

public interface FilmRepository  extends CrudRepository<Film, Integer> {



    @Override
    @CacheEvict(value = "filmsCache", allEntries = true)
    <S extends Film> S save(S entity);

    @Override
    @CacheEvict(value = "filmsCache", allEntries = true)
    void deleteById(Integer id);


}
