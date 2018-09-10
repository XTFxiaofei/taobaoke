package org.elsa.valord.api.controller.app;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elsa.valord.api.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author valor
 * @date 2018/9/6 10:49
 */
@RestController
@Api(tags = "LinkController", description = "返利、优惠券等商品相关api")
@RequestMapping(value = "/common/link")
public abstract class AbsLinkController extends BaseController {

    /**
     * @return 获取商品返利 优惠券相关信息
     */
    @ApiOperation(value = "getAuctionCode.json")
    @RequestMapping(value = "/getAuctionCode.json", method = RequestMethod.POST)
    public abstract DeferredResult<String> getAuctionCode();
}
