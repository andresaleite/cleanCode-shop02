package io.branas.shop02.services;
import java.time.Instant;

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
	@Autowired
	private ProductService produtoService;
	
	private static Double VALOR_FIXO_DISTANCIA = 1000.0;
	private static int VALOR_DENOMINADOR = 100;
	private static Double VALOR_MINIMO_FRETE = 10.0;
	private static Double VALOR_FORMULA_FRETE = 100.0;
	
	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		try {
			dto.setProducts(produtoService.returnProductsValids(dto.getProducts()));
		} catch (Throwable e) {
			return null;
		}
		if(ValidaCPF.isCpfValido(dto.getCpf()) && dto.getProducts().size() > 0) {
			Order entity = new Order();
			entity.setCpf(dto.getCpf());
			entity.setId(dto.getId());
			entity.setProducts(dto.getProducts());
			entity.setCoupon(serviceCoupon.isTheCouponValid(dto.getCoupon()));
			entity.setDate(Instant.now());
			entity.setFrete(this.calculaFrete(dto));
			entity.setDistancia(VALOR_FIXO_DISTANCIA);
			entity.setTotal(this.calculaTotal(entity.getCoupon(), dto, entity.getFrete()));
			entity = repository.save(entity);
			return new OrderDTO(entity);
		} else {
			return null;
		}
	}
	
	private Double calculaFrete(OrderDTO dto) {
		Double frete = 0.0;
		for(Product produto: dto.getProducts()) {
			Double larguraEmMetro = transformaCentimetroEmMetro(produto.getLargura());
			Double alturaEmMetro = transformaCentimetroEmMetro(produto.getAltura());
			Double profundidadeEmMetro = transformaCentimetroEmMetro(produto.getProfundidade());
			Double volume = larguraEmMetro * alturaEmMetro * profundidadeEmMetro;
			Double densidade = produto.getPeso() / volume;
			frete += VALOR_FIXO_DISTANCIA * volume * densidade/VALOR_FORMULA_FRETE;
		}
		System.out.println("Frete: "+frete);
		return frete < VALOR_MINIMO_FRETE ? VALOR_MINIMO_FRETE : frete;
	}

	private Double transformaCentimetroEmMetro(Double valor) {
		return valor / VALOR_DENOMINADOR;
	}

	private Double calculaTotal(Coupon coupon, OrderDTO dto, Double frete) {
		Double total = 0.0;
		for(Product produto: dto.getProducts()) {
			total += produto.getPrice() * produto.getQuantity();
		}
		if(coupon.getValor() > 0) {
			total = total - coupon.getValor()/100;
			return total + frete;
		}
		return total + frete;
	}
	
}
