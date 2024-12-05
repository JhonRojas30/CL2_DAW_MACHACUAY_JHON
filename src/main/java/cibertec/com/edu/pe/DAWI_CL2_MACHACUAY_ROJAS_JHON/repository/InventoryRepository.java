package cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.repository;

import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryRepository extends CrudRepository<Inventory, Integer> {
    List<Inventory> findByFilm_FilmId(int filmId);
}
