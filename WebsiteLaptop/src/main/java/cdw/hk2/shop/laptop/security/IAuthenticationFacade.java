package cdw.hk2.shop.laptop.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
@Configurable
public interface IAuthenticationFacade {
Authentication getAuthentication();
}

