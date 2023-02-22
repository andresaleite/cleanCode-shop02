package io.branas.shop02.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.branas.shop02.dto.OrderDTO;
import io.branas.shop02.entities.Coupon;
import io.branas.shop02.entities.Order;
import io.branas.shop02.entities.Product;
import io.branas.shop02.tests.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Test
	public void insertShouldInsertNoCouponResource() throws Exception {
		Set<Product> products = new HashSet<Product>();
		products.add(Factory.createProduct());
		products.add(Factory.createProduct1());
		products.add(Factory.createProduct2());
		Coupon coupon = Factory.createCoupon("", null, null);
		OrderDTO dto = Factory.createOrderDTO(Factory.createOrder(products,coupon,914.0));
		String jsonBody = objectMapper.writeValueAsString(dto);
		ResultActions result =
				mockMvc.perform(post("/orders")
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(status().isCreated());
		assertEquals(914, dto.getTotal());
	}
	
	@Test
	public void insertShouldInsertNoValidCouponResource() throws Exception {
		Set<Product> products = new HashSet<Product>();
		products.add(Factory.createProduct());
		products.add(Factory.createProduct1());
		products.add(Factory.createProduct2());
		Coupon coupon = Factory.createCoupon("DESC5", null, null);
		OrderDTO dto = Factory.createOrderDTO(Factory.createOrder(products,coupon,914.0));
		String jsonBody = objectMapper.writeValueAsString(dto);
		ResultActions result =
				mockMvc.perform(post("/orders")
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(status().isCreated());
		assertEquals(914, dto.getTotal());
	}
	
	@Test
	public void insertShouldInsertNotFoundCouponResource() throws Exception {
		Set<Product> products = new HashSet<Product>();
		products.add(Factory.createProduct());
		products.add(Factory.createProduct1());
		products.add(Factory.createProduct2());
		Coupon coupon = Factory.createCoupon("DESC10", null, null);
		OrderDTO dto = Factory.createOrderDTO(Factory.createOrder(products,coupon,914.0));
		String jsonBody = objectMapper.writeValueAsString(dto);
		ResultActions result =
				mockMvc.perform(post("/orders")
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(status().isCreated());
		assertEquals(914, dto.getTotal());
	}
	@Test
	public void insertShouldInsertNullCouponResource() throws Exception {
		Set<Product> products = new HashSet<Product>();
		products.add(Factory.createProduct());
		products.add(Factory.createProduct1());
		products.add(Factory.createProduct2());
		Coupon coupon = Factory.createCoupon(null, null, null);
		OrderDTO dto = Factory.createOrderDTO(Factory.createOrder(products,coupon,914.0));
		String jsonBody = objectMapper.writeValueAsString(dto);
		ResultActions result =
				mockMvc.perform(post("/orders")
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(status().isCreated());
		assertEquals(914, dto.getTotal());
	}
	
	@Test
	public void insertShouldInsertWithCouponResource() throws Exception {
		Set<Product> products = new HashSet<Product>();
		products.add(Factory.createProduct());
		products.add(Factory.createProduct1());
		products.add(Factory.createProduct2());
		Order order = Factory.createOrder(products, Factory.createCoupon("COMPRA10", 10.0, Instant.now()),822.6);
		OrderDTO dto = Factory.createOrderDTO(order);
		String jsonBody = objectMapper.writeValueAsString(dto);
		ResultActions result =
				mockMvc.perform(post("/orders")
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.coupon").value("COMPRA10"));
		result.andExpect(jsonPath("$.total").value(881.1));
	}	
	@Test
	public void insertShouldInsertWithProductQuantityNegative() throws Exception {
		Set<Product> products = new HashSet<Product>();
		Product product = Factory.createProduct();
		product.setQuantity(-2);
		products.add(product);
		Order order = Factory.createOrder(products, Factory.createCoupon("COMPRA10", 10.0, Instant.now()),0.0);
		OrderDTO dto = Factory.createOrderDTO(order);
		String jsonBody = objectMapper.writeValueAsString(dto);
		ResultActions result =
				mockMvc.perform(post("/orders")
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
	}	
	@Test
	public void insertShouldInsertWithCPFInvalido() throws Exception {
		Set<Product> products = new HashSet<Product>();
		products.add(Factory.createProduct());
		products.add(Factory.createProduct1());
		products.add(Factory.createProduct2());
		Order order = Factory.createOrder(products, Factory.createCoupon("COMPRA10", 10.0, Instant.now()),0.0);
		OrderDTO dto = Factory.createOrderDTO(order);
		dto.setCpf("99999999-11");
		String jsonBody = objectMapper.writeValueAsString(dto);
		ResultActions result =
				mockMvc.perform(post("/orders")
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
	}	
	@Test
	public void insertShouldInsertNoHaveAProduct() throws Exception {
		Set<Product> products = new HashSet<Product>();
		Order order = Factory.createOrder(products, Factory.createCoupon("COMPRA10", 10.0, Instant.now()),0.0);
		OrderDTO dto = Factory.createOrderDTO(order);
		String jsonBody = objectMapper.writeValueAsString(dto);
		
		ResultActions result =
				mockMvc.perform(post("/orders")
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
	}	
	@Test
	public void insertShouldInsertWithRepitedProduct() throws Exception {
		//Como o array de produto Set não repete.
	}
}