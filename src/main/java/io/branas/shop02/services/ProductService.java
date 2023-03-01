package io.branas.shop02.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.branas.shop02.dto.ProductDTO;
import io.branas.shop02.entities.Product;
import io.branas.shop02.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository repository;
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		if(validaParametrosDoProduto(dto)) {
			Product entity = new Product(dto);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		} else {
			throw new Error("Informe produtos válidos.");
		}
	}
	
	public Set<Product> returnProductsValids(Set<Product> products) {
		Set<Product> produtosValidos = new HashSet<Product>();
		for(Product produto: products) {
			if(validaParametrosDoProduto(new ProductDTO(produto))) {
				produtosValidos.add(produto);
			}
		}
		return produtosValidos;
	}

	public boolean validaParametrosDoProduto(ProductDTO produto) {
		
		if(produto.getQuantity() > 0 &&
				produto.getAltura() > 0 && 
				produto.getLargura() > 0 &&
				produto.getProfundidade() > 0 &&
				produto.getPeso() > 0
				)
			return true;
		
		throw new Error("Produto com valor negativo.");
	}
}
