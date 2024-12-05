package cibertec.com.edu.pe.DAWI_CL2_MACHACUAY_ROJAS_JHON.dto;

import java.util.Date;

public record FilmInsertDto(
                            String title,
                            String description,
                            Integer releaseYear,
                            Integer languageId,
                            String languageName,
                            Integer rentalDuration,
                            Double rentalRate,
                            Integer length,
                            Double replacementCost,
                            String rating,
                            String specialFeatures,
                            Date lastUpdate) {
}

