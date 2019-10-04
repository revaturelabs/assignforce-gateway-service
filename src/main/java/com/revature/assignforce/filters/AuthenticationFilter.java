
package com.revature.assignforce.filters;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
@Component
public class AuthenticationFilter extends ZuulFilter {
	private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	private static final String TRAINER_EDITABLE = "trainer-service";
    //private static final String AUDIENCE = "hydra-gateway";
    //private static final String ISSUER = "https://revature.auth0.com/";
    private static final String PUBLIC_KEY_LOCATION = "https://cognito-idp.us-east-1.amazonaws.com/us-east-1_hE8EafqgV/.well-known/jwks.json";
    //private static final String KID = "NkM1NzcyMEUxMzlCNzA5RTk1QkZDMDJGNUVDNjg2MEE0RTg3MTM4Mw"; //Auth0
    private static final String KID = "ci//JbT7yfLoVz4M3dW97G5nuN62wQojW8DlJ+MFJoQ=";
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    //private static final String SVP = "SVP of Technology";
    private static final String SVP = "svp";
    
    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 1;
    }
  
  	/**
	 * Checks whether a request should be filtered.
	 */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest req = ctx.getRequest();
        if (req.getMethod().equals("OPTIONS") || req.getRequestURI().toLowerCase().contains("v2/api-docs")
				|| req.getRequestURI().toLowerCase().contains("swagger-ui")) {
			return false;
		}
        return true;
    }
  
  	/**
	 * Responsible for checking whether a request is authorized.
	 */
    @Override
    public Object run() {
        System.out.println("Starting Auth Filter");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader(HEADER_STRING);
        
        if (token != null) {
            logger.warn("Token Found on Header");
            if (token.startsWith(TOKEN_PREFIX)) {
                token = token.substring(TOKEN_PREFIX.length());
            }
                        
            boolean requireSVP = true;
            
            if (request.getMethod().equals("GET") || request.getMethod().equals("OPTIONS")
                    || request.getRequestURL().toString().contains(TRAINER_EDITABLE))
            {
                logger.warn("Request doesn't require SVP");
                requireSVP = false;
            } 
            
            if (!authenticate(token, requireSVP)) {
                logger.warn("not auth");
                forbidden(ctx, "Unauthorized");
            }
        } else {
            logger.warn("No Auth");
            forbidden(ctx, "No Authorization");       
        }
        return null;
    }
  
  	/**
	 * Checks authorization clearance..
	 * 
	 * @param token
	 * @param requireSVP
	 * @return
	 */
    @SuppressWarnings("unchecked")
    private static boolean authenticate(String token, boolean requireSVP) {
        
        boolean error = false;
        
        try {
            //com.auth0.jwk.
            JwkProvider provider = new UrlJwkProvider(PUBLIC_KEY_LOCATION);
            Jwk jwk = provider.get(KID);
            //Verification ver = JWT.require(Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null)).withIssuer(ISSUER).withAudience(AUDIENCE);
            
            Verification ver = JWT.require(Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null));
            
            DecodedJWT parsed = ver.build().verify(token);
            //String[] roles = parsed.getClaim("https://revature.com/roles").asArray(String.class);
            String[] roles = parsed.getClaim("username").asArray(String.class);
            
            if (requireSVP)  {
                error = requireSVP;
                
                for (String s : roles) {
                    if (s.contains(SVP)) {
                        error = false;
                        break;
                    }
                }
                
            }
            
        } catch (JwkException e) {
            error = true;
        }
        
        return !error;
    }
    
  	/**
	 * Sets a 403: forbidden response, and logs unauthorized entry attempt.
	 * 
	 * @param ctx
	 * @param message
	 */
    private static void forbidden(RequestContext ctx, String message) {
        logger.warn("Request not allowed " + message);
        ctx.setResponseStatusCode(403);
        ctx.setResponseBody(message);
        ctx.setSendZuulResponse(false);
    }
}