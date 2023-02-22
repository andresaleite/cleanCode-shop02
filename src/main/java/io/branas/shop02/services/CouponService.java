package io.branas.shop02.services;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.branas.shop02.entities.Coupon;
import io.branas.shop02.repositories.CouponRepository;

@Service
public class CouponService {

	@Autowired
	private CouponRepository repositoryCoupon;
	
	@Transactional
	public Coupon findByName(String name) {
		return repositoryCoupon.findByName(name);
	}
	
	public Coupon isTheCouponValid(String name) {
		Coupon coupon = new Coupon(1L, name, null, 0.0);
		if(name == null || "".equals(name)) {
			return coupon;
		}
		coupon = this.findByName(name);
		if(coupon == null) 
			return new Coupon(1L, name, null, 0.0);
		return validaDataCoupon(coupon);
	}

	private Coupon validaDataCoupon(Coupon coupon) {
		if(coupon.getEndDate().isAfter(Instant.now())) {
			return coupon;
		} else {
			coupon.setValor(0.0);
			return coupon;
		}
	}
	
}
