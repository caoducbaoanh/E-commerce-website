package cdw.hk2.shop.laptop.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cdw.hk2.shop.laptop._enum.EOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product_details")
public class ProductDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne(mappedBy = "details")
	private Product product;
	@Column(name = "trademark")
	private String trademark;
	@Column(name = "guarantee")
	private double guarantee;
	@Column(name = "color")
	private String color;
	@Column(name = "seriesLaptop")
	private String seriesLaptop;
	@Column(name = "part_number")

	private String part_number;
	@Column(name = "CPUgeneration")

	private String CPUgeneration;
	@Column(name = "CPU")

	private String CPU;
	@Column(name = "graphics_chip")

	private String graphics_chip;
	@Column(name = "RAM")

	private String RAM;
	@Column(name = "screen")

	private String screen;
	@Column(name = "storage")

	private String storage;
	@Column(name = "maximum_number_of_storage_port")

	private String maximum_number_of_storage_port;
	@Column(name = "supportedM_2slot_type")

	private String supportedM_2slot_type;
	@Column(name = "output_port")

	private String output_port;
	@Column(name = "connector")

	private String connector;
	@Column(name = "wireless_Connectivity")

	private String wireless_Connectivity;
	@Column(name = "keyboard")

	private String keyboard;
	@Column(name = "operating_system")

	private String operating_system;
	@Column(name = "size")

	private String size;
	@Column(name = "weight")
	private double weight;
	@Column(name = "Pin")

	private String Pin;
	@Column(name = "security")

	private String security;
	@Column(name = "LED_light_on_machine")

	private String LED_light_on_machine;
	@Column (name = "included")
	private String included;
}
