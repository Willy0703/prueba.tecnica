package com.prueba.prueba.tecnica.service;

import com.prueba.prueba.tecnica.model.Persona;
import com.prueba.prueba.tecnica.repository.PersonaRepository;
import org.apache.coyote.BadRequestException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.security.auth.login.LoginException;
import java.util.Date;
import java.util.Optional;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;


    private final Log logger = LogFactory.getLog(this.getClass());

    public Persona savePersona(Persona persona) throws Exception {
        logger.info("Inicia servicio persona metodo save"); // logger que informa

        // validacion datos nulos
        if (persona.getNombre().isEmpty()) {
            throw new NullPointerException( "Nombre: no puede tener un valor nulo,");
        }
        if (persona.getApellidoPaterno().isEmpty()) {
            throw new NullPointerException ("Apellido paterno: no puede tener un valor nulo,");
        }
        if (persona.getApellidoMaterno().isEmpty()) {
            throw new NullPointerException ( "Apellido materno: no puede tener un valor nulo,");
        }
        // datos en un estandar uppercase para evitar conflictos en la busqueda de un usuario por nombre ignore case
        persona.setNombre(persona.getNombre().toUpperCase());
        persona.setApellidoPaterno(persona.getApellidoPaterno().toUpperCase());
        persona.setApellidoMaterno(persona.getApellidoMaterno().toUpperCase());
        // valida que no exista un usuario con el mismo nombre
        if( personaRepository.findPersonaByName(persona.getNombre(), persona.getApellidoPaterno(), persona.getApellidoMaterno()).isPresent()){
            throw new BadRequestException("YA EXISTE UNA PERSONA CON NOMBRE: "+persona.getNombreCompleto());
        }
        if (persona.getCorreo().isEmpty()) {
            throw new NullPointerException ( "correo: no puede tener un valor nulo,");
        }
        if (persona.getSexo().isEmpty()) {
            throw new NullPointerException ("Sexo: no puede tener un valor nulo,");
        }
        if (persona.getTelefono().isEmpty()) {
            throw new NullPointerException ( "telefono: no puede tener un valor nulo.");
        }

        persona.setCreateDate(new Date());
        persona.setUpdateDate(null);


        persona =personaRepository.save(persona);

        logger.info("PERSONA GUARDAD CON ID: "+persona.getId()); //loger informa termino

        return persona;
    }
    public Persona updatePersona(Persona persona) throws Exception {
        logger.info("Inicia servicio persona metodo update"); // logger que informa
        Optional<Persona> busquedaPersona = personaRepository.findById(persona.getId());
        if (busquedaPersona.isEmpty()){
            throw new NotFoundException("NO HAY UN REGISTRO DE PERSONA CON EL ID: "+persona.getId());
        }
        // validacion datos nulos
        if (persona.getNombre().isEmpty()) {
            throw new NullPointerException( "Nombre: no puede tener un valor nulo,");
        }
        if (persona.getApellidoPaterno().isEmpty()) {
            throw new NullPointerException ("Apellido paterno: no puede tener un valor nulo,");
        }
        if (persona.getApellidoMaterno().isEmpty()) {
            throw new NullPointerException ( "Apellido materno: no puede tener un valor nulo,");
        }
        // datos en un estandar uppercase para evitar conflictos en la busqueda de un usuario por nombre ignore case
        persona.setNombre(persona.getNombre().toUpperCase());
        persona.setApellidoPaterno(persona.getApellidoPaterno().toUpperCase());
        persona.setApellidoMaterno(persona.getApellidoMaterno().toUpperCase());
        // valida que no exista un usuario con el mismo nombre y id ya que el regitro con el mismo id le pertenece el nombre
        Optional<Persona> person = personaRepository.findPersonaByName(persona.getNombre(), persona.getApellidoPaterno(), persona.getApellidoMaterno());
        if( person.isPresent() && person.get().getId() != busquedaPersona.get().getId()){
            throw new BadRequestException("YA EXISTE UNA PERSONA CON NOMBRE: "+persona.getNombreCompleto());
        }
        if (persona.getCorreo().isEmpty()) {
            throw new NullPointerException ( "correo: no puede tener un valor nulo,");
        }
        if (persona.getSexo().isEmpty()) {
            throw new NullPointerException ("Sexo: no puede tener un valor nulo,");
        }
        if (persona.getTelefono().isEmpty()) {
            throw new NullPointerException ( "telefono: no puede tener un valor nulo.");
        }

        persona.setCreateDate(busquedaPersona.get().getCreateDate()); // conserva su dia de creacion
        persona.setUpdateDate(new Date());

        persona =personaRepository.save(persona);

        logger.info("PERSONA ACTUALIZADA CON ID: "+persona.getId()); //loger informa termino

        return persona;
    }

    public Persona getPersonaById(Long id){
        Optional<Persona> myPersona = null;

            myPersona = personaRepository.findById(id);
            if (myPersona.isEmpty()){
                throw new NotFoundException("NO SE ENCONTRO UNA PERSONA CON EL ID: "+id);
            }


        return myPersona.get();
    }


}
