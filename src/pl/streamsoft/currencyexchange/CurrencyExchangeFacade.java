package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class CurrencyExchangeFacade {

	private static final String GET_URL = "http://api.nbp.pl/api/exchangerates/rates/A/";
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public BigDecimal exchangeCurrencyToPLN(String currencyCode, BigDecimal value) {
		BigDecimal rate = getExchangeRate(currencyCode);
		if(rate!=null) {
			return value.multiply(rate);
		}
		return null;
	}

	private BigDecimal getExchangeRate(String currencyCode) {
		BigDecimal rate = null;
		HttpGet request = new HttpGet(GET_URL+currencyCode);
        request.addHeader("Accept", "application/json");
        try{
        	CloseableHttpResponse response = httpClient.execute(request);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String body = EntityUtils.toString(entity);
                    JSONObject bodyJson = new JSONObject(body);
    				JSONObject ratesJson = new JSONObject(bodyJson.getJSONArray("rates").get(0).toString());
    				rate = new BigDecimal(ratesJson.get("mid").toString());
                } else {
                	System.out.println("Request doesn't contain entity");
                }
            } else {
            	System.out.println("Request failed. Status code: "+response.getStatusLine().getStatusCode());
            }

        } catch(Exception e) {
        	e.printStackTrace();
        }
		return rate;
	}
}
