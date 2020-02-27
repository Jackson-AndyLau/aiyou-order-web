package com.huazai.b2c.aiyou.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huazai.b2c.aiyou.pojo.TbUser;
import com.huazai.b2c.aiyou.repo.AiyouResultData;
import com.huazai.b2c.aiyou.service.TbItemCartService;
import com.huazai.b2c.aiyou.service.TbUserService;
import com.huazai.b2c.aiyou.utils.CookieUtils;
import com.huazai.b2c.aiyou.utils.JsonUtils;
import com.huazai.b2c.aiyou.vo.TbItemCartVO;

/**
 * 
 * @author HuaZai
 * @contact who.seek.me@java98k.vip
 *          <ul>
 * @description 订单Controller
 *              </ul>
 * @className TbOrderController
 * @package com.huazai.b2c.aiyou.service.impl
 * @createdTime 2017年06月19日
 *
 * @version V1.0.0
 */
@Controller
@RequestMapping(value = "/order")
public class TbOrderController
{
	@Autowired
	private TbUserService tbUserService;
	
	@Autowired
	private TbItemCartService tbItemCartService;

	@Value(value = "${TB_ITEM_CART_LOCAL_KEY}")
	private String TB_ITEM_CART_LOCAL_KEY;

	@Value(value = "${TB_LOGIN_USER_INFO_KEY}")
	private String TB_LOGIN_USER_INFO_KEY;

	@Description(value = "显示订单详情")
	@RequestMapping(value = "/order-cart")
	public String showOrderInfo(HttpServletRequest request, HttpServletResponse response)
	{
		// 从用户本地的Cookie中获取登录后的Token
		String token = CookieUtils.getCookieValue(request, TB_LOGIN_USER_INFO_KEY);
		TbUser tbUser = new TbUser();
		if (!StringUtils.isEmpty(token))
		{
			// 通过Token获取用户信息
			AiyouResultData resultData = tbUserService.getUserInfoByToken(token);
			if (resultData.getStatus() == 200)
			{
				tbUser = (TbUser) resultData.getData();
			}
		}
		// 展示用户配置过的地址列表，根据UserId获取
		// TODO
		
		// 展示订单支付方式（银联、微信、支付宝等），从库中获取配置的支付方式
		// TODO
		
		// 从Cookie中获取购物车列表信息
		return "";
	}
	
	/**
	 * 
	 * @author HuaZai
	 * @contact who.seek.me@java98k.vip
	 * @title getTbItemCartByCookie
	 *        <ul>
	 * @description 从用户本地的Cookie中获取商品列表信息
	 *              </ul>
	 * @createdTime 2017年06月18日
	 * @param request
	 * @return
	 * @return List<TbItemCartVO>
	 *
	 * @version : V1.0.0
	 */
	private List<TbItemCartVO> getTbItemCartByCookie(HttpServletRequest request)
	{
		// 从Cookie中获取商品信息
		String resultData = CookieUtils.getCookieValue(request, TB_ITEM_CART_LOCAL_KEY, true);
		// 将商品转换成列表并返回
		List<TbItemCartVO> tbItemCartVOs = new ArrayList<TbItemCartVO>();
		if (!StringUtils.isEmpty(resultData))
		{
			tbItemCartVOs = JsonUtils.jsonToList(resultData, TbItemCartVO.class);
		}
		return tbItemCartVOs;
	}

}
