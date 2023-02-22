package io.branas.shop02.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.branas.shop02.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	
	
}
