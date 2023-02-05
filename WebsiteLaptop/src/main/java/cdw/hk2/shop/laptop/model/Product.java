
package cdw.hk2.shop.laptop.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

import cdw.hk2.shop.laptop._enum.EOrderStatus;
import cdw.hk2.shop.laptop.utils.UrlImageUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
@Indexed
@JsonIgnoreType
public class Product   {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY,
    cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    },
    mappedBy = "product")
	private List<Cart> cart = new ArrayList<Cart>();
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY,
		    cascade = {
		        CascadeType.PERSIST,
		        CascadeType.MERGE
		    },
		    mappedBy = "product")
	private List<Order> order = new ArrayList<Order>();
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_date")
	private Date createdDate ;

	@Column(name = "name")
    @Field(termVector = TermVector.YES)
	private String name;

	@Column(name = "description")
	private String description;
    @NumberFormat(style = Style.CURRENCY)
	@Column(name = "price")
	private double price;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "discount")
	private int discount;
	@JsonProperty("details")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_details_id")
	private ProductDetails details;
	@JsonProperty("images")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product",targetEntity = ProductImage.class, fetch=FetchType.EAGER)
	private List<ProductImage> images = new ArrayList<ProductImage>();
	@OneToMany( cascade = CascadeType.ALL, mappedBy = "product")
	private List<Review> reviews = new ArrayList<Review>();

}