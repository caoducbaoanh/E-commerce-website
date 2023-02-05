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
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "village")
public class VillageDto {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private String villageid;
	@Column (name ="name")
	private String name;
	@ManyToOne
	@JoinColumn(name = "wardid", nullable = false)
	private WardDto ward;


}
