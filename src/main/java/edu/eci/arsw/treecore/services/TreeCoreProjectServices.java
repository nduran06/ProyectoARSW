package edu.eci.arsw.treecore.services;

import java.util.ArrayList;

import edu.eci.arsw.treecore.exceptions.ProjectNotFoundException;
import edu.eci.arsw.treecore.exceptions.ServiciosTreeCoreException;
import edu.eci.arsw.treecore.model.impl.Proyecto;
import edu.eci.arsw.treecore.model.impl.Usuario;
import edu.eci.arsw.treecore.model.impl.Rama;
import edu.eci.arsw.treecore.model.impl.Mensaje;

import org.springframework.stereotype.Service;

//import edu.eci.arsw.treecore.persistence.TreeCorePersistence;

@Service
public interface TreeCoreProjectServices {
	public Proyecto getProyecto(int identificador) throws ProjectNotFoundException;
	public ArrayList<Proyecto> getAllProyectos() throws ProjectNotFoundException;
	public ArrayList<Proyecto> getAllProyectosUser() throws ProjectNotFoundException;
	public ArrayList<Usuario> getParticipantes(int identificador) throws ServiciosTreeCoreException;
	public boolean estaParticipando (String correo, int identificador);
	public ArrayList<Rama> getRamas (int identificador);
	public ArrayList<Mensaje> getMensajes (int identificador);
}