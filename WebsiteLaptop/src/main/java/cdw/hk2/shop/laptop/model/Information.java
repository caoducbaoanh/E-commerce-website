package cdw.hk2.shop.laptop.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "informations")
public class Information {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "phone")
	private String phone;
	@Column(name = "country")
	private String country;
	@Column(name = "dateBrithday")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date dateBrithday;
	@Column(name = "sex")
	private String sex;
	@Column(name = "company")
	private String company;
	@Column(name = "province")
	private String province;
	@Column(name = "district")
	private String district;
	@Column(name = "ward")
	private String ward;
	@Column(name = "village")
	private String village;
	@Column(name = "note")
	private String note;

	@OneToOne(mappedBy = "information")
	private User user;

}
