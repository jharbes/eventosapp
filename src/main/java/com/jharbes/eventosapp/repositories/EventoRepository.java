package com.jharbes.eventosapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.jharbes.eventosapp.models.Evento;

public interface EventoRepository  extends CrudRepository<Evento, String>{
	
}
