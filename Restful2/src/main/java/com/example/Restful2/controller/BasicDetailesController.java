package com.example.Restful2.controller;

import com.example.Restful2.model.BasicUser;
import com.example.Restful2.model.DetailedUser;
import com.example.Restful2.service.BasicDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class BasicDetailesController {
    @Autowired
    private BasicDetailService basicDetailService;

    // uri based versioning
    @GetMapping("v1/basic")
    public List<BasicUser> getAll() {
        return basicDetailService.getBasicUsers();
    }

    @GetMapping("v2/detailed")
    public List<DetailedUser> getAllD() {
        return basicDetailService.getDetailedUsers();
    }

    // param based
    @GetMapping(value = "/basic/param", params = "version=1")
    public List<BasicUser> getAllParam() {
        return basicDetailService.getBasicUsers();
    }

    @GetMapping(value = "/detailed/param",params = "version=2")
    public List<DetailedUser> getAllParamD() {
        return basicDetailService.getDetailedUsers();
    }

    // header based
    @GetMapping(value = "/basic/header", headers = "version=1")
    public List<BasicUser> getAllheader() {

        return basicDetailService.getBasicUsers();
    }

    @GetMapping(value = "/detailed/header",headers = "version=2")
    public List<DetailedUser> getAllheaders() {
        return basicDetailService.getDetailedUsers();
    }

    // mime based
    @GetMapping(value = "/basic/mime", produces = "application/version1+json")
    public List<BasicUser> getAllProduces() {
        return basicDetailService.getBasicUsers();
    }

    @GetMapping(value = "/detailed/mime",produces = "application/version2+json")
    public List<DetailedUser> getAllProducesD() {
        return basicDetailService.getDetailedUsers();
    }
}
