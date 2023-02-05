package cdw.hk2.shop.laptop.dto;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.access.method.P;

import cdw.hk2.shop.laptop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "district")
public class DistrictDto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String districtid;
	@Column(name = "name")
	private String name;
	@OneToMany (cascade = CascadeType.ALL,mappedBy = "district")
	private List<WardDto> ward = new ArrayList<WardDto>();
	@ManyToOne
	@JoinColumn (name = "provinceid",nullable = false)
	private ProvinceDto province;
}
