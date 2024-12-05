package cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmCategoryPk implements Serializable {
    private Integer filmId;
    private Integer categoryId;
}