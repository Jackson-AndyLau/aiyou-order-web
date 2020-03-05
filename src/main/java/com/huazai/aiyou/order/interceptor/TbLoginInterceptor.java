package com.huazai.aiyou.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.huazai.aiyou.common.response.AiyouResultData;
import com.huazai.aiyou.common.utils.CookieUtils;
import com.huazai.aiyou.sso.service.TbUserService;

/**
 * 
 * @author HuaZai
 * @contact who.seek.me@java98k.vip
 *          <ul>
 * @description 用户订单系统登录拦截器
 *              </ul>
 * @className TbLoginInterceptor
 * @package com.huazai.b2c.aiyou.interceptor
 * @createdTime 2017年06月19日
 *
 * @version V1.0.0
 */
public class TbLoginInterceptor implements HandlerInterceptor
{

	@Autowired
	private TbUserService tbUserService;

	@Value(value = "${AIYOU_TB_USER_COOKIE_TOKEN_KEY}")
	private String AIYOU_TB_USER_COOKIE_TOKEN_KEY;

	@Value(value = "${AIYOU_TB_SSO_LOGIN_BASE_URL}")
	private String AIYOU_TB_SSO_LOGIN_BASE_URL;

	/**
	 * 用户身份认证， 进入目标方法前执行，true:表示放行，false:表示拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		// 从用户本地的Cookie中获取Token值
		String token = CookieUtils.getCookieValue(request, AIYOU_TB_USER_COOKIE_TOKEN_KEY);
		AiyouResultData resultData = tbUserService.getUserInfoByToken(token);
		// 用户登录过期，或未登录
		if (resultData.getData() == null)
		{
			// 重定向到登录界面
			response.sendRedirect(AIYOU_TB_SSO_LOGIN_BASE_URL + "/user/login");
		} else
		{
			// 用户已登录，放行
			return true;
		}
		return false;
	}

	/**
	 * 进入目标方法后，返回模型视图前执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 返回模型视图后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception
	{
		// TODO Auto-generated method stub

	}

}
