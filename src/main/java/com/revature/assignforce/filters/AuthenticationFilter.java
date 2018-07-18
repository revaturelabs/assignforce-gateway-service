package com.revature.assignforce.filters;

import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class AuthenticationFilter extends ZuulFilter {

    private static final String TRAINER_EDITABLE = "trainer-service";
	private static final String AUDIENCE = "hydra-gateway";
	private static final String ISSUER = "https://revature.auth0.com/";
	private static final String PUBLIC_KEY_LOCATION = "https://revature.auth0.com/.well-known/jwks.json";
	private static final String KID = "NkM1NzcyMEUxMzlCNzA5RTk1QkZDMDJGNUVDNjg2MEE0RTg3MTM4Mw";
	private static final String HEADER_STRING = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String SVP = "SVP of Technology";
	
	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		String token = request.getHeader(HEADER_STRING);
        System.out.println(token);
        
        if (token != null) {
        	if (token.startsWith(TOKEN_PREFIX)) {
        		token = token.substring(TOKEN_PREFIX.length());
        	}
        	
        	System.out.println(request.getContextPath());
        	
        	boolean requireSVP = true;
        	
        	if (request.getMethod().equals("GET") || request.getMethod().equals("OPTIONS")) {
        		requireSVP = false;
        	} else if (request.getRequestURL().toString().contains(TRAINER_EDITABLE)) {
        		requireSVP = false;
        	}
        	
        	if (!authenticate(token, requireSVP)) {
        		forbidden(ctx, "Unauthorized");
        	}
        } else {
			forbidden(ctx, "No Authorization"); 		
		}
		
		return null;
	}

    private static boolean authenticate(String token, boolean requireSVP) {
    	
    	boolean error = false;
    	
    	try {
            JwkProvider provider = new UrlJwkProvider(PUBLIC_KEY_LOCATION);
            com.auth0.jwk.Jwk jwk = provider.get(KID);
    		Verification ver = JWT
    				.require(Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null))
    				.withIssuer(ISSUER)
    				.withAudience(AUDIENCE);
    		
    		DecodedJWT parsed = ver.build().verify(token);
    		String[] roles = parsed.getClaim("https://revature.com/roles").asArray(String.class);
    		
    		if (requireSVP)  {
    			error = requireSVP;
    			
    			for (String s : roles) {
    				System.out.println(s);
    				if (SVP.equals(s)) {
    					error = false;
    					break;
    				}
    			}
    			
    			if (error) {
    				throw new Exception("failed to authenticate user");
    			}
    		}
    		
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    		error = true;
    	}
    	
        return !error;
    }
    
	private static void forbidden(RequestContext ctx, String message) {
		ctx.setResponseStatusCode(403);
		ctx.setResponseBody(message);
		ctx.setSendZuulResponse(false);
	}

}