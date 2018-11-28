package com.revature.assignforce.filters;
/*
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.netflix.zuul.context.RequestContext;
import com.revature.assignforce.filters.AuthenticationFilter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
public class AuthenticationFilterTest
{
    private static final String HEADER_STRING = "Authorization";

    @InjectMocks
    private AuthenticationFilter filter;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }
    /*
    Test Explanation:
    Its testing whether or not the token is valid, this is the first if statement.
    It's testing to make sure that the token is correct.

    @Test(expected = SignatureVerificationException.class)
    public void run() {
        RequestContext requestContext = mock(RequestContext.class);

        RequestContext.testSetCurrentContext(requestContext);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(requestContext.getRequest()).thenReturn(httpServletRequest);
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL3JldmF0dXJlLmF1dGgwLmNvbS8iLCJzdWIiOiJtYWlsdG86bWlrZUBleGFtcGxlLmNvbSIsIm5iZiI6MTU0MzExMzkzNSwiZXhwIjoxNTQzMTE3NTM1LCJpYXQiOjE1NDMxMTM5MzUsImp0aSI6ImlkMTIzNDU2IiwidHlwIjoiaHR0cHM6Ly9leGFtcGxlLmNvbS9yZWdpc3RlciJ9.SbTThw0Sqs2Ou1W2kxwo9_tCqSTaO2UeD7wWB63kIDwsRLkvnUyHwkxIuMe-WHJq47nLueUJ80HsMRuZHyI14s_vspTdoJAh2jBJSQ4lpg37iR4DFESUtXMJCrGoDCcLmzZuc7oLurstxB7e3TItkTBr8KoBzDteZyFWtqXRIP258CmPmymmCB6tIhkII8CM3VfJEzbHWb5wpuiAXSw8SB7Pye2FTVUEzwiiw38xDZTHX0_xKispeZQn_k5tw5BjpjQJow8tXNq5QSnD7zCCmn8PMRhBNlXphO7qfEzNnWvk1y8nv-YlxyeSuc_gMGrHAH1CFUfufIuwtPldbSIY-g";
        when(httpServletRequest.getHeader(HEADER_STRING)).thenReturn(token);
        when(httpServletRequest.getMethod()).thenReturn("GET");

        filter.run();
    }

}
 */