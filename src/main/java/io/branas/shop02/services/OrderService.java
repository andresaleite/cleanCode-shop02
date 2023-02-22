package io.branas.shop02.services;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.branas.shop02.dto.OrderDTO;
import io.branas.shop02.entities.Coupon;
import io.branas.shop02.entities.Order;
import io.branas.shop02.entities.Product;
import io.branas.shop02.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	@Autowired
	private CouponService serviceCoupon;
	
	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		dto.setProducts(returnProductsValids(dto.getProducts()));
		if(ValidaCPF.isCpfValido(dto.getCpf()) && dto.getProducts().size() > 0) {
			Order entity = new Order();
			entity.setCpf(dto.getCpf());
			entity.setId(dto.getId());
			entity.setProducts(dto.getProducts());
			entity.setCoupon(serviceCoupon.isTheCouponValid(dto.getCoupon()));
			entity.setDate(Instant.now());
			entity.setTotal(this.calculaTotal(entity.getCoupon(), dto));
			entity = repository.save(entity);
			return new OrderDTO(entity);
		} else {
			return null;
		}
	}
	
	private Set<Product> returnProductsValids(Set<Product> products) {
		Set<Product> produtosValidos = new HashSet<Product>();
		for(Product produto: products) {
			if(produto.getQuantity()> 0) {
				produtosValidos.add(produto);
			}
		}
		return produtosValidos;
	}

	private Double calculaTotal(Coupon coupon, OrderDTO dto) {
		Double total = 0.0;
		for(Product produto: dto.getProducts()) {
			total += produto.getPrice() * produto.getQuantity();
		}
		if(coupon.getValor() > 0) {
			return total - total/coupon.getValor();
		}
		
		return total;
	}
	
}
