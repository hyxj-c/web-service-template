package org.example.template.service.acl;

import org.example.template.common.utils.Response;
import org.springframework.web.bind.annotation.*;

@RestController
public class Test {

    @RequestMapping("/test")
    public Response test() {
        return Response.success().message("hello spring boot");
    }
}
