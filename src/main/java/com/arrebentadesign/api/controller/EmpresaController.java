package com.arrebentadesign.api.controller;

import com.arrebentadesign.api.entity.Empresa;
import com.arrebentadesign.api.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {
    @Autowired
    private EmpresaRepository empresaRepository;

    @PostMapping // Definir endpoint
    public ResponseEntity<Empresa> cadastrarDadosDaEmpresa(
            @RequestBody Empresa novaEmpresa
    ) {
        novaEmpresa.setId(null);
        return ResponseEntity.status(201).body(empresaRepository.save(novaEmpresa));
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        List<Empresa> empresas = empresaRepository.findAll();
        if (empresas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Empresa>> buscarEmpresasPorId(
            @PathVariable int id) {
        Optional<Empresa> empresaOpt = empresaRepository.findById(id);
        if (empresaOpt.isPresent()) {
            Empresa empresaEncontrada = empresaOpt.get();
            return ResponseEntity.status(200).body(empresaOpt);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{dono}")
    public ResponseEntity<Optional<Empresa>> buscarEmpresasPorDono(
            @PathVariable String dono) {
        Optional<Empresa> empresaOpt = empresaRepository.findByDono(dono);
        if (empresaOpt.isPresent()) {
            Empresa empresaEncontrada = empresaOpt.get();
            return ResponseEntity.status(200).body(empresaOpt);
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping
    public ResponseEntity<Empresa> atualizar(
            @PathVariable int id,
            @RequestBody Empresa empresaAtualizada) {
        if (empresaRepository.existsById(id)) {
            empresaAtualizada.setId(id);
            return ResponseEntity.status(200).body(empresaRepository.save(empresaAtualizada));
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable int id) {
        if (empresaRepository.existsById(id)) {
            empresaRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

}
