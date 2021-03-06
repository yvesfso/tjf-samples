package br.com.star.wars.messaging;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.totvs.tjf.core.message.TOTVSMessage;
import com.totvs.tjf.sgdp.services.SGDPRabbitExchange;
import com.totvs.tjf.sgdp.services.SGDPRabbitExchangeChannel;
import com.totvs.tjf.sgdp.services.data.SGDPDataResponse;
import com.totvs.tjf.sgdp.services.mask.SGDPMaskResponse;

@EnableBinding(SGDPRabbitExchange.class)
public class SGDPSubscriber {

	@StreamListener(target = SGDPRabbitExchangeChannel.Channel.INPUT, condition = "headers['type']=='SGDPMaskResponse'")
	public void maskResponse(TOTVSMessage <SGDPMaskResponse> message) {
		System.out.println("***** MASK RESPONSE *****");
		System.out.println("ROWS: " + message.getContent().getRows());
		System.out.println(message);
	}

	@StreamListener(target = SGDPRabbitExchangeChannel.Channel.INPUT, condition = "headers['type']=='SGDPDataResponse'")
	public void dataResponse(TOTVSMessage <SGDPDataResponse> message) {
		System.out.println("***** DATA RESPONSE *****");
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(message.getContent().getData()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(message);
	}
	
}