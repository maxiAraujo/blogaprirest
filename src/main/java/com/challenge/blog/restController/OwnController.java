package com.challenge.blog.restController;

import com.challenge.blog.entity.Client;
import com.challenge.blog.service.JwtUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class OwnController {

    @Autowired
    private JwtUserDetailsService userService;

    protected Log log;

    public OwnController() {
        this.log = LogFactory.getLog(getClass());
    }

    public Client getUserEntity() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByMail(auth.getName());
    }

}
