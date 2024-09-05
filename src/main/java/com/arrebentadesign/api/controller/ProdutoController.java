package com.arrebentadesign.api.controller;

import com.arrebentadesign.api.entity.Produto;
import com.arrebentadesign.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(
            @RequestBody Produto novoProduto
    ) {
        novoProduto.setId(null);
        return ResponseEntity.status(201).body(produtoRepository.save(novoProduto));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Produto>> buscarPorId(
            @PathVariable int id) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produtoEncontrado = produtoOpt.get();
            return ResponseEntity.status(200).body(produtoOpt);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{produto-mais-caro}")
    public ResponseEntity<List<Produto>> buscarPorProdutoMaisCaro(
            @RequestParam Double preco) {
        List<Produto> produtos = produtoRepository.findTopByOrderByPrecoDesc(preco);
        if (!produtos.isEmpty()) {
            return ResponseEntity.status(200).body(produtos);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{produto-mais-barato}")
    public ResponseEntity<List<Produto>> buscarPorProdutoMaisBarato(
            @RequestParam Double preco) {
        List<Produto> produtos = produtoRepository.findTopByOrderByPrecoAsc(preco);
        if (!produtos.isEmpty()) {
            return ResponseEntity.status(200).body(produtos);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/produtos-az")
    public ResponseEntity<List<Produto>> listarProdutosAZ() {
        List<Produto> produtos = produtoRepository.findAllByOrderByNomeProdutoAsc();
        if (!produtos.isEmpty()) {
            return ResponseEntity.status(200).body(produtos);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/produtos-za")
    public ResponseEntity<List<Produto>> listarProdutosZA() {
        List<Produto> produtos = produtoRepository.findAllByOrderByNomeProdutoDesc();
        if (!produtos.isEmpty()) {
            return ResponseEntity.status(200).body(produtos);
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping
    public ResponseEntity<Produto> atualizar(
            @PathVariable int id,
            @RequestBody Produto produtoAtualizado) {
        if (produtoRepository.existsById(id)) {
            produtoAtualizado.setId(id);
            return ResponseEntity.status(200).body(produtoRepository.save(produtoAtualizado));
        }
        return ResponseEntity.status(404).build();
    }

    @PatchMapping("/produtos/{id}/preco")
    public ResponseEntity<Produto> atualizarPreco(@PathVariable Integer id, @RequestParam Double novoPreco) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            produto.setPreco(novoPreco);
            produtoRepository.save(produto);
            return ResponseEntity.status(200).body(produto);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable int id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }


}
