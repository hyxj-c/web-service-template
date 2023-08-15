package org.example.template.service.acl;

import org.example.template.common.utils.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @GetMapping("hello")
    public Response hello() {
        return Response.success().message("hello spring boot");
    }
}
