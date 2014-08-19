package com.gbax.TopicsTestTask.system.security;

import com.gbax.TopicsTestTask.dao.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by abayanov
 * Date: 18.08.14
 */
@Service
public class SecurityService {

    public User getSecurityPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof User ? (User) principal : null;
    }

}