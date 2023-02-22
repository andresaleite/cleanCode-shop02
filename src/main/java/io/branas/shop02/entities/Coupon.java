package io.branas.shop02.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_coupon")
public class Coupon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Instant endDate;
	private Double valor;
	@OneToMany(mappedBy = "coupon")
	private Set<Order> order = new HashSet<Order>();

	public Coupon() {}
	public Coupon(Long id, String name, Instant endDate, Double valor) {
		this.id = id;
		this.name = name;
		this.endDate = endDate;
		this.valor = valor;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name == null ? "" : name.toUpperCase().trim();
	}
	public void setName(String name) {
		this.name = name;
	}
	public Instant getEndDate() {
		return endDate;
	}
	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}
	public Double getValor() {
		if(valor == null) {
			valor = 0.0;
		}
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Set<Order> getOrder() {
		return order;
	}
	public void setOrder(Set<Order> order) {
		this.order = order;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		return Objects.equals(id, other.id);
	}
}
