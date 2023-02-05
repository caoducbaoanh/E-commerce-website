package cdw.hk2.shop.laptop.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

import cdw.hk2.shop.laptop._enum.EOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice.This;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "transactions")
@JsonIgnoreType
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "id")
	private long id;

	@Column(name = "sku_mvc")
	private String sku_mvc;
	@JsonProperty("user")
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@JsonProperty("orders")
	@OneToMany(mappedBy = "transaction", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	private List<Order> orders = new ArrayList<Order>();

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private EOrderStatus status;

	@Column(name = "name_shipper")
	private String shipper;
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "created_date")
	private Date createdDate;
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "updated_date")
	private Date updatedDate;
	@Column(name = "sumTotalOrder")
	public double sumTotalOrder;
	@OneToOne(mappedBy = "transaction")
	private Bill bill;
	@Column(name = "checks")
	private boolean checks;

}