package cdw.hk2.shop.laptop.utils;

import java.util.Random;

import org.springframework.stereotype.Component;
@Component
public class StringUtils {
	public String getSaltString(String sku) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 9) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = sku+salt.toString();
		return saltStr;

	}

}
