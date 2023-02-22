package io.branas.shop02.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.branas.shop02.entities.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

	public Coupon findByName(String name);
	
}
