package com.prueba.prueba.tecnica.repository;

import com.prueba.prueba.tecnica.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long> {
    @Query("select persona from Persona persona where persona.nombre = upper(:nombre) and persona.apellidoPaterno = upper( :apellidoPaterno) and persona.apellidoMaterno =  upper(:apellidoMaterno)" )
    Optional<Persona> findPersonaByName(@Param("nombre") String nombre, @Param("apellidoPaterno") String apellidoPaterno, @Param("apellidoMaterno") String apellidoMaterno);


}
