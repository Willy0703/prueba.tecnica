package com.prueba.prueba.tecnica.service;

import com.prueba.prueba.tecnica.model.Persona;
import com.prueba.prueba.tecnica.repository.PersonaRepository;
import org.apache.coyote.BadRequestException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

public class PersonaServiceTest {

    @InjectMocks
    private PersonaService personaService;

    @Mock
    private PersonaRepository personaRepository;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void savePersona() throws Exception {
        Persona persona = new Persona();
        persona.setId(1L);
        persona.setNombre("nombre");
        persona.setApellidoPaterno("paterno");
        persona.setApellidoMaterno("materno");
        persona.setCorreo("prueba@correo");
        persona.setSexo("FEMENINO");
        persona.setTelefono("123456");
        // persona que retorna separando la base de datos de la prueba unitaria esto hara que regrese el objeto persona en lugar de consultar a la DB
        Mockito.when(personaRepository.save(Mockito.any())).thenReturn(persona);
        // OPTIONAL PARA PROBAR REGISTRO CON NOMBRE
        Optional<Persona> peronaOptional = Optional.of(persona);
        Mockito.when(personaRepository.findPersonaByName("PRUEBA","PRUEBA","PRUEBA")).thenReturn(peronaOptional);
        // p sera la instancia con la que haremos pruebas
        Persona p = new Persona();
        p.setNombre("dfghj");
        p.setApellidoPaterno("sdfghjk");
        p.setApellidoMaterno("sdfghjk");
        p.setCorreo("sdf");
        p.setTelefono("sdfghj");
        p.setSexo("df");
        p.setFechaNacimiento(LocalDate.now());
        Persona persona1 = p;
        probar(p);

        //valores nulos
        p.setNombre("");
        probar(p);


        p.setNombre("fgh");
        p.setApellidoPaterno("");
        probar(p);

        p.setApellidoPaterno("fgh");
        p.setApellidoMaterno("");
        probar(p);

        p.setApellidoMaterno("fgh");
        p.setCorreo("");
        probar(p);

        p.setCorreo("dfghj");
        p.setSexo("");
        probar(p);

        p.setSexo("dfghj");
        p.setTelefono("");
        probar(p);

        // prueba para registro con mismo nombre no se guarde
        p.setNombre("PRUEBA");
        p.setApellidoMaterno("PRUEBA");
        p.setApellidoPaterno("PRUEBA");
        probar(p);
    }
    public void probar(Persona p){
        try{
            personaService.savePersona(p);
        }catch (NullPointerException n){
            System.out.println(n.getMessage());;
        } catch (BadRequestException b){
            System.out.println(b.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
