package com.gbax.TopicsTestTask.system.security.api;

import com.gbax.TopicsTestTask.dao.entity.User;

/**
 * Created by Баянов on 07.02.2015.
 */
public interface ISecurityService {

    public User getSecurityPrincipal();

}
