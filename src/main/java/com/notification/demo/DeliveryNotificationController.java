package com.notification.demo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
public class DeliveryNotificationController {

	@RequestMapping(value = "/v1/notification/delivery", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<String> sendMessage(
			@NotNull @Parameter(in = ParameterIn.QUERY, description = "", required = false, schema = @Schema()) @Valid @RequestParam(value = "whtsAppNo", required = true) String whtsAppNo,
			@NotNull @Parameter(in = ParameterIn.QUERY, description = "", required = false, schema = @Schema()) @Valid @RequestParam(value = "customerName", required = true) String customerName,
			@NotNull @Parameter(in = ParameterIn.QUERY, description = "", required = false, schema = @Schema()) @Valid @RequestParam(value = "trackingId", required = true) String trackingId
			) {
		
		Twilio.init("ACf52f89bcc2450064e9d273bb046b0c55", "fd2d4b5beeb35366c76f635df246ab64");
		Message message = Message.creator(new com.twilio.type.PhoneNumber("whatsapp:+"+whtsAppNo),
				new com.twilio.type.PhoneNumber("whatsapp:+14155238886"), buildDeliveryMessage(customerName,trackingId)).create();
		System.out.println(message.getSid());
		return new ResponseEntity<String>("Messge sent successfully", HttpStatus.OK);
	}
	
	private String buildDeliveryMessage(String custName, String trackingId)
	{
		System.out.println("custName : "+custName);
		System.out.println("trackingId : "+trackingId);
		String msg= "Hi {customerName},Greetings from Fedex.We are pleased to inform you that you have an upcoming delivery with tracking ID {trackingID}.\nDo you want to change?\nDate  \nLocation ";
		msg=msg.replace("{customerName}", custName);
		msg=msg.replace("{trackingID}", trackingId);
		return msg;
	}
}
