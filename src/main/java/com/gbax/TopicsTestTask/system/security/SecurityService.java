package com.gbax.TopicsTestTask.system.security;

import com.gbax.TopicsTestTask.dao.entity.User;
import com.gbax.TopicsTestTask.system.security.api.ISecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by abayanov
 * Date: 18.08.14
 */
@Service
public class SecurityService implements ISecurityService {

    public User getSecurityPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( authentication == null ) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        return principal instanceof User ? (User) principal : null;
    }

}