package io.branas.shop02.tests;

import java.time.Instant;
import java.util.Set;

import io.branas.shop02.dto.OrderDTO;
import io.branas.shop02.entities.Coupon;
import io.branas.shop02.entities.Order;
import io.branas.shop02.entities.Product;

public class Factory {

	public static Order createOrder(Set<Product> produtos, Coupon coupon, Double total) {
		return new Order(1L, coupon, "69780181202", total, Instant.parse("2020-10-20T03:00:00Z"), produtos);
	}

	public static OrderDTO createOrderDTO(Order order) {
		return new OrderDTO(order);
	}

	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", 1,
				Instant.parse("2020-10-20T03:00:00Z"), 1.1, 15.1, 8.1, 0.2);
		return product;
	}

	public static Product createProduct0() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", 2,
				Instant.parse("2020-10-20T03:00:00Z"),8.1, 15.1, 8.1, 0.8);
		return product;
	}

	public static Product createProduct1() {
		Product product = new Product(10L, "Clean Code", "Livro de blá blá", 65.0, "https://img.com/img.png", 2,
				Instant.parse("2023-01-20T03:00:00Z"),0.8, 15.0, 9.1, 0.4);
		return product;
	}

	public static Product createProduct2() {
		Product product = new Product(20L, "Design vs. Arquitetura ", "Livro sobre Design vs. Arquitetura", 49.0,
				"https://img.com/img.png", 1, Instant.parse("2023-02-02T03:00:00Z"),
				0.8, 15.0, 9.1, 0.4);
		return product;
	}

	public static Coupon createCoupon(String nomeCupom, Double valor, Instant dataFim) {
		Coupon coupon = new Coupon(1l, nomeCupom, dataFim, valor);
		return coupon;
	}
}
