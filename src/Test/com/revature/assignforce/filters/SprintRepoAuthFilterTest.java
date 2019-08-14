package com.revature.assignforce.filters;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.revature.assignforce.filters.AuthenticationFilter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class SprintRepoAuthFilterTest
{
	private static final String HEADER_STRING = "Authorization";

	@InjectMocks
	private SprintRepoAuthFilter filter;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test()
	public void testFilterNoCookie() throws ZuulException
	{
		RequestContext requestContext = mock(RequestContext.class);
		RequestContext.testSetCurrentContext(requestContext);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(requestContext.getRequest()).thenReturn(request);
		when(requestContext.getResponse()).thenReturn(response);

		when(request.getMethod()).thenReturn("GET");

		given(request.getCookies()).willReturn(new Cookie[] {});



		filter.setSprintToken("Value");
		filter.run();



		final ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);

		verify(response).addCookie(captor.capture());

		final Cookie cookie = captor.getValue();



		assertEquals("SprintRepoAuthToken",cookie.getName());
		assertEquals("Value",cookie.getValue());
	}


	@Test()
	public void testFilterWithCookie()
	{
		RequestContext requestContext = mock(RequestContext.class);
		RequestContext.testSetCurrentContext(requestContext);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(requestContext.getRequest()).thenReturn(request);
		when(requestContext.getResponse()).thenReturn(response);

		when(request.getMethod()).thenReturn("GET");

		Cookie c = new Cookie("SprintRepoAuthToken","Value");

		given(request.getCookies()).willReturn(new Cookie[] {c});


		assertFalse(filter.shouldFilter());
	}
}
