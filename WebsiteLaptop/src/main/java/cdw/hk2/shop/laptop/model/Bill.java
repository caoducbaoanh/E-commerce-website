package cdw.hk2.shop.laptop.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "bills")
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "code_bill")
	private String codeBill;
	@OneToOne(cascade = CascadeType.ALL)
	private Transaction transaction;
	@Column (name = "name_pay")
	private String namePay;
	@Column (name = "pay_date")
	private Date payDate;
	@Column(name = "total_price")
	private double totalPrice;

}
