package cdw.hk2.shop.laptop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "address_orders")
public class AddressOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne(mappedBy = "address")
	private Order order;
	@Column(name = "recever")
	private String recever;
	@Column(name = "email")
	private String email;
	@Column(name = "phone")
	private String phone;
	@Column(name = "province")
	private String province;
	@Column(name = "district")
	private String district;
	@Column(name = "ward")
	private String ward;
	@Column(name = "village")
	private String village;
	@Column(name = "address_house")
	private String address_house;
	@Column (name = "note")
	private String note;
}
