package com.jharbes.eventosapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.jharbes.eventosapp.models.Convidado;
import com.jharbes.eventosapp.models.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {
	
	Iterable<Convidado> findByEvento(Evento evento);
}
