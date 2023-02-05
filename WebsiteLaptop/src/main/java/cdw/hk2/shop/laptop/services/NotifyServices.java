package cdw.hk2.shop.laptop.services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdw.hk2.shop.laptop._enum.ERole;
import cdw.hk2.shop.laptop.model.Notify;
import cdw.hk2.shop.laptop.model.Order;
import cdw.hk2.shop.laptop.model.Role;
import cdw.hk2.shop.laptop.model.User;
import cdw.hk2.shop.laptop.repository.INotifyResponsitory;
import cdw.hk2.shop.laptop.utils.TimeUtlis;
@Service
public class NotifyServices {
	@Autowired
	private INotifyResponsitory iNotifyResponsitory;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private TimeUtlis time;
	@Autowired
	private UserServiceImpl userServiceImpl;
	public List<Notify> getAllList(){
		return iNotifyResponsitory.findAll();
		
	}public List<Notify> getAllLisiFindByID(Iterable<Long> id){
		return iNotifyResponsitory.findAllById(id);
		
		
	}public List<Notify> getAllListFindByCheck(long id){
		return null;
		
	}public Notify getNotify(long id) {
		return iNotifyResponsitory.findById(id).orElse(null);
		
		
	}
	public Notify Save(Notify notify) {
		return iNotifyResponsitory.save(notify);
		// TODO Auto-generated method stub
		
	}
	public List<Notify> getAllListNotifyNew() {
		List<Notify> liNotifies = iNotifyResponsitory.findAll();
		List<Notify> list= new ArrayList<Notify>();
		Collections.sort(liNotifies, new Comparator<Notify>() {
            @Override
            public int compare(Notify o1, Notify o2) {
                return o2.getTime().compareTo(o1.getTime());
            }
        });
		if(liNotifies.size()>4) {
			for(int i=0;i<liNotifies.size();i++) {
				Notify notify= liNotifies.get(i);
				list.add(notify);
				
			}
			return list;
			
		}
		return liNotifies;
	}
	public List<Notify> findAllNotifyrByIdUser(long idUser) {
		List<Notify> liNotifies = iNotifyResponsitory.findAll();
		List<Notify> list= new ArrayList<Notify>();
for (Notify n:liNotifies) {
	if(n.getUser().getId()==idUser) {
		list.add(n);
	}
}
		return list;
	}
	public void deleteNotifyById(long id) {
		iNotifyResponsitory.deleteById(id);		
	}
	public void sendNotify(Notify notify, String user) {
		System.out.println(user);
		if(user.equals("all")) {
			List<User> list=userServiceImpl.findAllUser();
			for(int i=0;i<list.size();i++) {
				String role=list.get(i).getAccountDto().getRoles().toString();
				if(role.contains("ROLE_USER")) {
					notify.setUser(list.get(i));notify.setNameId(list.get(i).getId());
					iNotifyResponsitory.save(notify);
				}
			}
		}else {
			long id=Long.parseLong(user);
			User userN=userServiceImpl.findUserById(id);
			notify.setUser(userN);notify.setNameId(id);
			iNotifyResponsitory.save(notify);
		}
		
	}
	
}
