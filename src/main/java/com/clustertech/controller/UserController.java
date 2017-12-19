package com.clustertech.controller;

import com.clustertech.bean.ResponseBean;
import com.clustertech.database.Service;
import com.clustertech.database.UserBean;
import com.clustertech.exception.UnauthorizedException;
import com.clustertech.util.JWTUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private Service service;

    @Autowired
    public void setService(Service service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseBean login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        UserBean userBean = service.getUser(username);
        if (userBean.getPassword().equals(password)) {
            return new ResponseBean(200, "Login success", JWTUtil.sign(username, password));
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/edit")
    public ResponseBean edit() {
        return new ResponseBean(200, "You are editing now", null);
    }

    @GetMapping("/admin/hello")
    public ResponseBean adminView() {
        return new ResponseBean(200, "You are visiting admin content", null);
    }

    @GetMapping("/annotation/require_auth")
    @RequiresAuthentication
    public ResponseBean annotationView1() {
        return new ResponseBean(200, "You are visiting require_auth", null);
    }

    @GetMapping("/annotation/require_role")
    @RequiresRoles("admin")
    public ResponseBean annotationView2() {
        return new ResponseBean(200, "You are visiting require_role", null);
    }

    @GetMapping("/annotation/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResponseBean annotationView3() {
        return new ResponseBean(200, "You are visiting permission require edit,view", null);
    }


    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized() {
        return new ResponseBean(401, "Unauthorized", null);
    }
}
