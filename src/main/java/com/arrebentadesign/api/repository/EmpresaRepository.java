package com.arrebentadesign.api.repository;

import com.arrebentadesign.api.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Optional<Empresa> findByDono(String dono);
}
