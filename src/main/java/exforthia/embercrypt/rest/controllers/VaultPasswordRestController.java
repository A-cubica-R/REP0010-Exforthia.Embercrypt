package exforthia.embercrypt.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exforthia.embercrypt.rest.dto.VaultPasswordDTO;
import exforthia.embercrypt.rest.services.VaultPasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/v1/vaultpassword", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Vault Password", description = "API para gestión de contraseñas del vault")
public class VaultPasswordRestController {

	private static final Logger logger = LoggerFactory.getLogger(VaultPasswordRestController.class);

	@Autowired
	private VaultPasswordService service;

	@GetMapping("")
	@Operation(summary = "Get all VaultPassword entries", description = "Returns a list of all VaultPassword entries")
	@ApiResponse(responseCode = "200", description = "Lista de contraseñas obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VaultPasswordDTO.class)))
	public ResponseEntity<List<VaultPasswordDTO>> findAll() {
		logger.debug("REST : GET - findAll");
		List<VaultPasswordDTO> list = service.findAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{idPassword}")
	@Operation(summary = "Get VaultPassword by ID", description = "Returns a single VaultPassword entry by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Contraseña encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = VaultPasswordDTO.class))),
			@ApiResponse(responseCode = "404", description = "Contraseña no encontrada", content = @Content)
	})
	public ResponseEntity<VaultPasswordDTO> findById(
			@Parameter(description = "ID de la contraseña", required = true) @PathVariable long idPassword) {
		logger.debug("REST : GET - findById");
		VaultPasswordDTO vaultPasswordDTO = service.findById(idPassword);
		if (vaultPasswordDTO != null) {
			return ResponseEntity.ok(vaultPasswordDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("")
	@Operation(summary = "Create new VaultPassword", description = "Creates a new VaultPassword entry")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Contraseña creada exitosamente", content = @Content),
			@ApiResponse(responseCode = "409", description = "Conflicto - la contraseña ya existe", content = @Content)
	})
	public ResponseEntity<Void> create(
			@Parameter(description = "Datos de la contraseña a crear", required = true) @RequestBody VaultPasswordDTO vaultPasswordDTO) {
		logger.debug("REST : POST - create");
		if (service.create(vaultPasswordDTO)) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@PutMapping("/{idPassword}")
	@Operation(summary = "Save VaultPassword", description = "Updates or creates a VaultPassword entry with the given ID")
	@ApiResponse(responseCode = "200", description = "Contraseña actualizada o creada exitosamente", content = @Content)
	public ResponseEntity<Void> save(
			@Parameter(description = "ID de la contraseña", required = true) @PathVariable long idPassword,
			@Parameter(description = "Datos de la contraseña", required = true) @RequestBody VaultPasswordDTO vaultPasswordDTO) {
		logger.debug("REST : PUT - save");
		service.save(idPassword, vaultPasswordDTO);
		return ResponseEntity.ok().build();
	}

	@PutMapping("")
	@Operation(summary = "Update VaultPassword", description = "Updates an existing VaultPassword entry")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Contraseña actualizada exitosamente", content = @Content),
			@ApiResponse(responseCode = "404", description = "Contraseña no encontrada", content = @Content)
	})
	public ResponseEntity<Void> update(
			@Parameter(description = "Datos de la contraseña a actualizar", required = true) @RequestBody VaultPasswordDTO vaultPasswordDTO) {
		logger.debug("REST : PUT - update");
		if (service.update(vaultPasswordDTO)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping("/{idPassword}")
	@Operation(summary = "Partial update VaultPassword", description = "Partially updates an existing VaultPassword entry")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Contraseña actualizada parcialmente", content = @Content),
			@ApiResponse(responseCode = "404", description = "Contraseña no encontrada", content = @Content)
	})
	public ResponseEntity<Void> partialUpdate(
			@Parameter(description = "ID de la contraseña", required = true) @PathVariable long idPassword,
			@Parameter(description = "Datos parciales de la contraseña", required = true) @RequestBody VaultPasswordDTO vaultPasswordDTO) {
		logger.debug("REST : PATCH - partialUpdate");
		if (service.partialUpdate(idPassword, vaultPasswordDTO)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{idPassword}")
	@Operation(summary = "Delete VaultPassword", description = "Deletes a VaultPassword entry by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Contraseña eliminada exitosamente", content = @Content),
			@ApiResponse(responseCode = "404", description = "Contraseña no encontrada", content = @Content)
	})
	public ResponseEntity<Void> deleteById(
			@Parameter(description = "ID de la contraseña a eliminar", required = true) @PathVariable long idPassword) {
		logger.debug("REST : DELETE - deleteById");
		if (service.deleteById(idPassword)) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
