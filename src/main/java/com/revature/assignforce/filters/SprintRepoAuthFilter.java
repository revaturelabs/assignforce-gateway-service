package com.revature.assignforce.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SprintRepoAuthFilter extends ZuulFilter {

	/**
	 * Apply filter after response has been generated.
	 */
	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * Checks whether a request should be filtered, based on whether the request's
	 * cookies match the Authorization Token.
	 */
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();

		if (req.getMethod().equals("GET")) {
			for (Cookie c : req.getCookies()) {
				if (c.getName().equals("SprintRepoAuthToken")) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Adds a cookie to the response, allowing it to bypass the filter later.
	 */
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletResponse response = ctx.getResponse();
		response.addCookie(new Cookie("SprintRepoAuthToken", sprintRepoAuthToken));

		return null;
	}

	@Value("${client.sprintRepoAuthToken}")
	private String sprintRepoAuthToken;

	/**
	 * Sets the Authorization token,
	 * 
	 * @param token
	 */
	void setSprintToken(String token) {
		sprintRepoAuthToken = token;
	}
}
