package utils;

import com.gbax.TopicsTestTask.dao.entity.User;
import com.gbax.TopicsTestTask.system.security.api.ISecurityService;

/**
 * SecurityService для тестов
 */
public class SecurityService implements ISecurityService {

    public User getSecurityPrincipal() {
        User user = new User();
        user.setName("Test");
        user.setId(1);
        return user;
    }


}
