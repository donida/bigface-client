package bigface.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import bigface.service.domain.Event;

@Component
public class RestEventSender {
	
	Logger log = Logger.getLogger(RestEventSender.class);

	private enum EventType {
		FACE
	}

	private Long partnerId = 1L;
	private EventType eventType = EventType.FACE;
	private Double latitude = new Double("-27.6333");
	private Double longitude = new Double("-48.6500"); 
	
	@Autowired
	private RestTemplate restTemplate;
	
	public void sendEvent() {
		Event event = new Event(partnerId, eventType.name(), latitude, longitude, "", 0.0, null, null);
		ResponseEntity<Event> result = restTemplate.postForEntity("http://192.168.25.32:3000/events.json", event, Event.class);
		HttpStatus statusCode = result.getStatusCode();
		log.debug("Response Status: " + statusCode);
		if (HttpStatus.CREATED.equals(statusCode)) {
			Event body = result.getBody();
			log.debug(body.toString());
		} else {
			log.error("FUCK...");
		}
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

}
