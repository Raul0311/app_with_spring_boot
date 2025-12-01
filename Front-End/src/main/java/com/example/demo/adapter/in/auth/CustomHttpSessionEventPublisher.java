package com.example.demo.adapter.in.auth;

// import java.io.IOException;

import jakarta.servlet.http.HttpSessionEvent;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
/*import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.WebAuthenticationDetails;*/
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.example.demo.adapter.out.persistence.UserAdapterOut;

/**
 * The Class CustomHttpSessionEventPublisher.
 */
public class CustomHttpSessionEventPublisher extends HttpSessionEventPublisher {

	/** The Constant logger. */
    // private static final Logger logger = LoggerFactory.getLogger(CustomHttpSessionEventPublisher.class);
    
    /** The autenticacion client service. */
	// private AutenticacionClientService autenticacionClientService = new AutenticacionClientService();

	/** The Constant URL_SECURITY_SERVICE_ACTIVE_SESSION. */
    // private static final String URL_SECURITY_LOGOUT_UPDATE = "https://www.soy3eres.es/books-business-security-service/security-api/logout-and-update-shoppingcart-http-session/";
    
    /** The http client. */
	// private HttpClient httpClient;
	
	/** The cookie store. */
	// private CookieStore cookieStore;
	
	/** The http context. */
	// private HttpContext httpContext;
	
	/** The http uri request. */
	// private HttpUriRequest httpUriRequest;
	
	/** The http response. */
	// private org.apache.http.HttpResponse httpResponse;
	
	private final UserAdapterOut userService;
	
	public CustomHttpSessionEventPublisher(UserAdapterOut userService) {
        this.userService = userService;
    }

	/**
	 * Session created.
	 *
	 * @param event the event
	 */
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        super.sessionCreated(event);
    }

    /**
	 * Session destroyed.
	 *
	 * @param event the event
	 */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        // String userToken = (String) event.getSession().getAttribute("userToken");
        // String lastHttpSession = event.getSession().getId();
        String currentHttpSession = ((WebAuthenticationDetails) ((SecurityContextImpl) event.getSession()
				.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getDetails()).getSessionId();
        
        try {
            userService.deleteUserToken(currentHttpSession);
            // System.out.println("Sesión destruida correctamente! ID: " + currentHttpSession);
        } catch (Exception e) {
            // Manejar error sin romper el logout
            System.err.println("No se pudo eliminar el token de sesión: " + e.getMessage());
        }
        
        /* String params= "sessionId_" + userToken+"/lastHttpSession_"+lastHttpSession+"/currentHttpSession_"+currentHttpSession;
		String url = URL_SECURITY_SERVICE_LOGOUT_AND_UPDATE_SHOPPING_CART_HTTP_SESSION + params;

		httpUriRequest = new HttpGet(url);
		
		httpClient = new DefaultHttpClient();

		String result = "false";
		try {
			httpResponse = httpClient.execute(httpUriRequest, httpContext);
			String responseString = new BasicResponseHandler().handleResponse(httpResponse);
			JSONObject json = new JSONObject(responseString);

			// To reuse the same connection is needed to consume and close the content. Just
			// one petitions at same time may be done.
			// httpResponse.getEntity().getContent().close();

			result = json.getString("isSessionActive");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		logger.debug(
				"sessionDestroyed()-> Last session: " + lastHttpSession + ", Current session: " + currentHttpSession+ ", Is last session active: "+ result);*/


       super.sessionDestroyed(event);
    }
}
