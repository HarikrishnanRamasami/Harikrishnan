package qa.com.qic.common.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.IClientCredential;
import com.microsoft.aad.msal4j.MsalException;
import com.microsoft.aad.msal4j.SilentParameters;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.models.Attendee;
import com.microsoft.graph.models.AttendeeType;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.DateTimeTimeZone;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.Event;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Location;
import com.microsoft.graph.models.OnlineMeetingProviderType;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;

import okhttp3.Request;



public class Graph {

    private static GraphServiceClient<Request> graphClient = null;
    private static TokenCredentialAuthProvider authProvider = null;
    public static final String MICROSOFT_GRAPH_APP_ID =   "6378ee49-7d10-4306-aa96-be2076659745";
    public static final String permissions = "User.Read,MailboxSettings.Read,Calendars.ReadWrite";
    public static final String MICROSOFT_GRAPH_APP_SCOPES = "https://graph.microsoft.com/.default";
    public static final String ClientSecret_ID = "b62ec02c-ac42-4eca-b15d-b1b596805bad";
    public static final String ClientSecret_Value = "21Z0bWwX_-IE3.J-9WLZ8N299prBOAgfUP";
    public static final String TENANT = "be15500f-4f28-4f48-9dde-c9253294e291";
    public static final String AUTHORITY = "https://login.microsoftonline.com/be15500f-4f28-4f48-9dde-c9253294e291";
    public static   final List<String> appScopes = Arrays
	 	       .asList(MICROSOFT_GRAPH_APP_SCOPES.split(","));


    public static void initializeGraphAuth() {
        // Create the auth provider

  	   final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
		        .clientId(MICROSOFT_GRAPH_APP_ID)
		        .clientSecret(ClientSecret_Value)
		        .tenantId(TENANT)
		        .build();

	   authProvider = new TokenCredentialAuthProvider(appScopes,clientSecretCredential);

        // Create default logger to only log errors
        DefaultLogger logger = new DefaultLogger();
        logger.setLoggingLevel(LoggerLevel.ERROR);

        // Build a Graph client
        graphClient = GraphServiceClient.builder()
            .authenticationProvider(authProvider)
            .logger(logger)
            .buildClient();




    }

    public static String getUserAccessToken()
    {
        try {
            URL meUrl = new URL("https://graph.microsoft.com/v1.0/me");
            SSLContext sslcontext = null;
            TrustManager[] trustAllCerts = new X509TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            try {
                sslcontext = SSLContext.getInstance("SSL");
                sslcontext.init(null, trustAllCerts, null);
                HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());

            } catch (NoSuchAlgorithmException | KeyManagementException e) {
            }
            return authProvider.getAuthorizationTokenAsync(meUrl).get();
        } catch(Exception ex) {
            return null;
        }
    }

    public static String getClientCredentialAccessToken() throws Exception{
    	IClientCredential credential = ClientCredentialFactory.createFromSecret(ClientSecret_Value);

    	ConfidentialClientApplication cca = null;
		try {
			cca = ConfidentialClientApplication
			        .builder(MICROSOFT_GRAPH_APP_ID, credential)
			        .authority(AUTHORITY)
			        .build();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	    Set<String> scopeSet = appScopes.stream().collect(Collectors.toSet());
       IAuthenticationResult result = null;
     try {
        SilentParameters silentParameters =
                SilentParameters
                        .builder(scopeSet)
                        .build();

        result = cca.acquireTokenSilently(silentParameters).join();
    } catch (Exception ex) {
        if (ex.getCause() instanceof MsalException) {

            ClientCredentialParameters parameters =
                    ClientCredentialParameters
                            .builder(scopeSet)
                            .build();

            result = cca.acquireToken(parameters).join();
        } else {
            // Handle other exceptions accordingly
            throw ex;
        }
    }

 	String accesTOKEN = result.accessToken();

    return accesTOKEN;

}

 public static void createTeamsMeetingInOutlook(){


 	   Event event = new Event();
 	   event.subject = "Test Event";
 	   ItemBody body = new ItemBody();
 	   body.contentType = BodyType.HTML;
 	   body.content = "Does next month work for you?";
 	   event.body = body;
 	   DateTimeTimeZone start = new DateTimeTimeZone();
 	   start.dateTime = "2021-06-19T12:00:00";
 	   start.timeZone = "Pacific Standard Time";
 	   event.start = start;
 	   DateTimeTimeZone end = new DateTimeTimeZone();
 	   end.dateTime = "2021-06-19T14:00:00";
 	   end.timeZone = "Pacific Standard Time";
 	   event.end = end;
 	   Location location = new Location();
 	   location.displayName = "Online";
 	   event.location = location;
 	   LinkedList<Attendee> attendeesList = new LinkedList<>();
 	   Attendee attendees = new Attendee();
 	   EmailAddress emailAddress = new EmailAddress();
 	   emailAddress.address = "karthik.j@anoudtechnologies.com";
 	   emailAddress.name = "Karthik Jayakumar";
 	   attendees.emailAddress = emailAddress;
 	   attendees.type = AttendeeType.REQUIRED;
 	   attendeesList.add(attendees);
 	   event.attendees = attendeesList;
 	   event.isOnlineMeeting = true;
 	   event.onlineMeetingProvider = OnlineMeetingProviderType.TEAMS_FOR_BUSINESS;


 		final User me = graphClient.me().buildRequest().get();
        System.out.println("Sucess####################" + me.displayName);



    }
}