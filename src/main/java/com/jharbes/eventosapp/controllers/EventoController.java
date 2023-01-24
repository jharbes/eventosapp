package com.jharbes.eventosapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jharbes.eventosapp.models.Convidado;
import com.jharbes.eventosapp.models.Evento;
import com.jharbes.eventosapp.repositories.ConvidadoRepository;
import com.jharbes.eventosapp.repositories.EventoRepository;

import jakarta.validation.Valid;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private ConvidadoRepository convidadoRepository;

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String form(Evento evento, BindingResult result, RedirectAttributes attributes) {
		if (evento.getNome().equals("") || evento.getData().equals("") || evento.getHorario().equals("")
				|| evento.getLocal().equals("")) {
			attributes.addFlashAttribute("flashMessage", "Verifique os campos!");
			attributes.addFlashAttribute("flashType", "danger");

			return "redirect:/cadastrarEvento";
		} else {
			eventoRepository.save(evento);
			attributes.addFlashAttribute("flashMessage", "Evento adicionado com sucesso!");
			attributes.addFlashAttribute("flashType", "success");

			return "redirect:/cadastrarEvento";
		}
	}

	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> eventos = eventoRepository.findAll();
		mv.addObject("eventos", eventos);
		return mv;
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = eventoRepository.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", evento);

		Iterable<Convidado> convidados = convidadoRepository.findByEvento(evento);
		mv.addObject("convidados", convidados);
		return mv;
	}

	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo) {
		Evento evento = eventoRepository.findByCodigo(codigo);
		eventoRepository.delete(evento);
		return "redirect:/eventos";
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado,
			BindingResult result, RedirectAttributes attributes) {
		System.out.println(result.hasFieldErrors());
		if (convidado.getNomeConvidado().equals("") || convidado.getRg().equals("")) {
			attributes.addFlashAttribute("flashMessage", "Verifique os campos!");
			attributes.addFlashAttribute("flashType", "danger");
			return "redirect:/{codigo}";
		} else {
			Evento evento = eventoRepository.findByCodigo(codigo);
			convidado.setEvento(evento);
			convidadoRepository.save(convidado);
			attributes.addFlashAttribute("flashMessage", "Convidado adicionado com sucesso!");
			attributes.addFlashAttribute("flashType", "success");

			return "redirect:/{codigo}";
		}
	}

	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidado convidado = convidadoRepository.findByRg(rg);
		convidadoRepository.delete(convidado);

		Evento evento = convidado.getEvento();
		long codigoLong = evento.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/" + codigo;
	}
}
