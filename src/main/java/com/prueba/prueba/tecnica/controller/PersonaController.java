package com.prueba.prueba.tecnica.controller;

import com.prueba.prueba.tecnica.model.Persona;
import com.prueba.prueba.tecnica.service.PersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.BadRequestException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Persona", description = "Persona APIs")
@Controller
@RequestMapping("/persona" )
public class PersonaController {
    @Autowired
    private PersonaService personaService;

    private final Log logger = LogFactory.getLog(this.getClass());
    // end point de tipo post para guardar una persona
    @PostMapping
    @Operation(summary = "Save Persona", description = "Guardar los datos de una persona") // documentacion swggaer
    public ResponseEntity<Persona> save(@RequestBody Persona persona) throws Exception {

        // manejo de excepciones
        try{
           persona = personaService.savePersona(persona);
        }catch (NullPointerException n){
            logger.error(n.getMessage());
            return ResponseEntity.badRequest().body(persona); // si un dato es nulo devolvemos una mala respuesta
        } catch (BadRequestException b){
            logger.error("DATOS REPETIDOS "+b.getMessage());
            return ResponseEntity.badRequest().body(persona); // si el nuevo registro tiene el nombre de un registro de la base de datos
        } catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build(); // errores en conexion o de otro tipo no esperados
        }
        return  ResponseEntity.ok(persona);
    }

    @PutMapping
    @Operation(summary = "Update Persona", description = "Actualiza los datos de una persona") // documentacion swggaer

    public ResponseEntity<Persona> update(@RequestBody Persona persona){
        try{
            persona = personaService.updatePersona(persona);
        }catch (NullPointerException n){
            logger.error(n.getMessage());
            return ResponseEntity.badRequest().body(persona); // si un dato es nulo devolvemos una mala respuesta
        } catch (BadRequestException b){
            logger.error("DATOS REPETIDOS "+b.getMessage());
            return ResponseEntity.badRequest().body(persona); // si el nuevo registro tiene el nombre de un registro de la base de datos
        }catch (NotFoundException nf){
            logger.error(nf.getMessage()); // si no existe un registro con ese id
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().build(); // errores en conexion o de otro tipo no esperados
        }
        return  ResponseEntity.ok(persona);
    }


    @Operation(summary = "Find Persona by id", description = "Restorna los datos de una persona realizando su busqueda por el id")
    @GetMapping("/{id}")
    public ResponseEntity<Persona> findPersonaById(@RequestParam Long id){
        try{

            return ResponseEntity.ok(personaService.getPersonaById(id));

        }catch (NotFoundException nf){
            logger.error("ERROR: "+nf.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
