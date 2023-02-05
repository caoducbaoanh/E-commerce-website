package cdw.hk2.shop.laptop.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cdw.hk2.shop.laptop._enum.ERole;
import cdw.hk2.shop.laptop.dto.AccountDto;
import cdw.hk2.shop.laptop.model.Cart;
import cdw.hk2.shop.laptop.model.Information;
import cdw.hk2.shop.laptop.model.Notify;
import cdw.hk2.shop.laptop.model.Product;
import cdw.hk2.shop.laptop.model.Role;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.repository.IUserRepository;
import cdw.hk2.shop.laptop.utils.TimeUtlis;

@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private TimeUtlis timeUtlis;
	@Autowired
	private NotifyServices notifyServices;

	public UserServiceImpl(IUserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	@Override
	public User save(AccountDto accountDto) {
		Role roles = new Role(null, ERole.ROLE_USER);
		accountDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		accountDto.setRoles(Arrays.asList(roles));
		accountDto.setActivity(true);
		Date time = timeUtlis.convertToDateViaSqlTimestamp();
		Information information = new Information();
		Cart cart = new Cart();
		List<Product> listp = new ArrayList<Product>();
		cart.setProduct(listp);
		Notify notify = new Notify();
		notify.setChecks(false);notify.setContent("Tài khoản mới được đăng ký");notify.setKeyword("User");notify.setTime(timeUtlis.convertToDateViaSqlTimestamp());
		List<Notify> list = new ArrayList<Notify>();
		User user = new User(null, accountDto, cart, time, null, information, null, null, null, null, null,list);
		User user2= userRepository.save(user);
		notify.setUser(user2);
		notifyServices.Save(notify);
		return user2;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AccountDto user = getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));

	}
	public AccountDto getUserByUsername(String username) {
		List<User> user = userRepository.findAll();
		System.out.println(username);
		AccountDto accountDto = null;
		for (User user2 : user) {
			if (user2.getAccountDto().getEmail().equals(username)) {
				accountDto = user2.getAccountDto();
			}
		}
		// TODO Auto-generated method stub
		return accountDto;
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
	}

	public User findUserById(long id) {
		return userRepository.findById(id).orElse(null);
	}

	public User updatePass(String passNew, long id) {
		User userOld = userRepository.findById(id).orElse(null);
		userOld.getAccountDto().setPassword(passNew);
		;
		return userRepository.save(userOld);

	}

	public boolean checkUserName(String username) {
		Object user = userRepository.findAccountByEmail(username);
		if (user != null) {
			return false;
		}
		return true;

	}

	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	public String deleteUserById(long id) {
		userRepository.deleteById(id);
		return "detele user";
	}

	public User activityUserById(long id, boolean activity) {
		User userOld = userRepository.findById(id).orElse(null);
		userOld.getAccountDto().setActivity(activity);
		return userRepository.save(userOld);
	}

	public User setUserRole(long id, String rolee) {
		User userOld = userRepository.findById(id).orElse(null);
		Object[] role = userOld.getAccountDto().getRoles().toArray();
		Role roles = (Role) role[0];
		if(rolee.equals("ROLE_ADMIN")) {
			roles.setName(ERole.ROLE_ADMIN);
		}else {
			roles.setName(ERole.ROLE_USER);
		}
		userOld.getAccountDto().setRoles(new ArrayList<>(Arrays.asList(roles)));
		return userRepository.save(userOld);

	}

	public User saveUser(User user) {
		return userRepository.save(user);
		
	}

	public List<User> findAllByDate(Date date) {
		return userRepository.findAllByDate(date);
	}

}
