package org.elsa.valord.api.controller.root;

import org.elsa.valord.api.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author valor
 * @date 2018/9/9 22:26
 */
@RestController
@RequestMapping(value = "/root")
public abstract class AbsInitController extends BaseController {

    @RequestMapping(value = "/init")
    public abstract String init();
}
