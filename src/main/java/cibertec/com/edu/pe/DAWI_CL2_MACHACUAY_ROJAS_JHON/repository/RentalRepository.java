package cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.repository;

import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.entity.Inventory;
import cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.entity.Rental;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RentalRepository extends CrudRepository<Rental, Integer> {
    List<Rental> findByInventory(Inventory inventory);
}
