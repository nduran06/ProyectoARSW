package edu.eci.arsw.treecore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import edu.eci.arsw.treecore.exceptions.ServiciosTreeCoreException;
import edu.eci.arsw.treecore.services.TreeCoreProjectServices;
import edu.eci.arsw.treecore.services.TreeCoreUserServices;
import edu.eci.arsw.treecore.persistence.mappers.UsuarioMapper;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping(value = "/treecore")
public class TreeCoreAPIController {
    @Autowired
    UsuarioMapper usuarioMapper;
    @Autowired
    TreeCoreUserServices treeCoreUserServices;
    @Autowired
    TreeCoreProjectServices treeCoreProjectServices;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> GetAll() {
        System.out.println(usuarioMapper.getUsers());
        System.out.println("holaaaaaaaaaaaaaa");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/projects", method = RequestMethod.GET)
    public ResponseEntity<?> GetAllProyects() {
        try {
            return new ResponseEntity<>(treeCoreProjectServices.getAllProyectos(), HttpStatus.ACCEPTED);
        } catch (ServiciosTreeCoreException e) {
            Logger.getLogger(TreeCoreAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/{creator}/projects", method = RequestMethod.GET)
    public ResponseEntity<?> GetUserProjects(@PathVariable("creator") String creatorName) {
        try {
            return new ResponseEntity<>(treeCoreProjectServices.getAllProyectosUser(creatorName), HttpStatus.ACCEPTED);
        } catch (ServiciosTreeCoreException e) {
            Logger.getLogger(TreeCoreAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/projects/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> GetProject(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(treeCoreProjectServices.getProyecto(id), HttpStatus.ACCEPTED);
        } catch (ServiciosTreeCoreException e) {
            Logger.getLogger(TreeCoreAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/projects/{id}/ramas", method = RequestMethod.GET)
    public ResponseEntity<?> GetRamas(@PathVariable("id") int id) {

        return new ResponseEntity<>(treeCoreProjectServices.getRamas(id), HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/projects/{id}/team", method = RequestMethod.GET)
    public ResponseEntity<?> GetTeam(@PathVariable("id") int id) {

        try {
            return new ResponseEntity<>(treeCoreProjectServices.getParticipantes(id), HttpStatus.ACCEPTED);
        } catch (ServiciosTreeCoreException e) {
            Logger.getLogger(TreeCoreAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/projects/{id}/menssages", method = RequestMethod.GET)
    public ResponseEntity<?> GetMessages(@PathVariable("id") int id) {
        return new ResponseEntity<>(treeCoreProjectServices.getMensajes(id), HttpStatus.ACCEPTED);
    }
    
    @RequestMapping(path = "/users/{correo}", method = RequestMethod.GET)
    public ResponseEntity<?> GetUser(@PathVariable("correo") String correo) {
        try {
            return new ResponseEntity<>(treeCoreUserServices.getUsuario(correo), HttpStatus.ACCEPTED);
        } catch (ServiciosTreeCoreException e) {
            Logger.getLogger(TreeCoreAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/users/{correo}/invitations", method = RequestMethod.GET)
    public ResponseEntity<?> GetUserInvitations(@PathVariable("correo") String correo) {
        try {
            return new ResponseEntity<>(treeCoreUserServices.getInvitaciones(correo), HttpStatus.ACCEPTED);
        } catch (ServiciosTreeCoreException e) {
            Logger.getLogger(TreeCoreAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/users/{correo}/notifications", method = RequestMethod.GET)
    public ResponseEntity<?> GetUserNotificatios(@PathVariable("correo") String correo) {
        try {
            return new ResponseEntity<>(treeCoreUserServices.getNotificaciones(correo), HttpStatus.ACCEPTED);
        } catch (ServiciosTreeCoreException e) {
            Logger.getLogger(TreeCoreAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
	
}
