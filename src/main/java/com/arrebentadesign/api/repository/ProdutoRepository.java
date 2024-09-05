package com.arrebentadesign.api.repository;

import com.arrebentadesign.api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findTopByOrderByPrecoDesc(Double preco);
    List<Produto> findTopByOrderByPrecoAsc(Double preco);
    List<Produto> findAllByOrderByNomeProdutoAsc();
    List<Produto> findAllByOrderByNomeProdutoDesc();
}
