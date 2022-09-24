package br.pbmulesoft.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.pbmulesoft.api.controller.dto.EstadoDto;
import br.pbmulesoft.api.controller.form.AtualizarEstadoForm;
import br.pbmulesoft.api.controller.form.EstadoForm;
import br.pbmulesoft.api.modelo.Estado;
import br.pbmulesoft.api.repository.EstadoRepository;

@RestController
@RequestMapping("/api/v1/estados")
public class EstadosController {

	@Autowired
	private EstadoRepository estadoRepository;

	@GetMapping
	@ResponseBody
	public List<EstadoDto> lista(String regiao) {

		if (regiao == null) {

			List<Estado> estados = estadoRepository.findAll(); // carregar todos os registros do banco de dados
			return EstadoDto.converter(estados);

		} else {
			List<Estado> estados = estadoRepository.findByRegiao(regiao);
			return EstadoDto.converter(estados);

		}

	}

	@PostMapping
	@Transactional
	public ResponseEntity<EstadoDto> cadastrar(@RequestBody EstadoForm form, UriComponentsBuilder uriBuilder) {
		Estado estado = form.converter();
		estadoRepository.save(estado);

		URI uri = uriBuilder.path("/api/v1/estados/{id}").buildAndExpand(estado.getId()).toUri();
		return ResponseEntity.created(uri).body(new EstadoDto(estado));
	}

	@GetMapping("{id}")
	public ResponseEntity<EstadoDto> detalhar(@PathVariable Long id) {
		Optional<Estado> estado = estadoRepository.findById(id); //Tratamento do erro 404

		if (estado.isPresent()) {
			return ResponseEntity.ok(new EstadoDto(estado.get()));
		}

		return ResponseEntity.notFound().build();

	}

	@PutMapping("{id}")
	@Transactional
	public ResponseEntity<EstadoDto> atualizar(@PathVariable Long id, @RequestBody AtualizarEstadoForm form) {

		Optional<Estado> optional = estadoRepository.findById(id); //Tratamento do erro 404

		if (optional.isPresent()) {
			Estado estado = form.atualizar(id, estadoRepository);
			return ResponseEntity.ok(new EstadoDto(estado));
		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("{id}")
	@Transactional
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		Optional<Estado> optional = estadoRepository.findById(id); //Tratamento do erro 404		
		if (optional.isPresent()) {
			estadoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();

	}
}
