package cdw.hk2.shop.laptop.services;

import org.springframework.data.domain.Page;

import cdw.hk2.shop.laptop.model.Product;

public interface IProductServices {
	Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,long id,String keyword);

}
