package com.neotys.iso8583.customActions;

import java.io.IOException;
import java.util.List;
import org.jpos.iso.BaseChannel;
import com.google.common.base.Strings;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.SampleResult;


public class ISO8583DisconnectActionEngine implements ActionEngine {
	String HOST;
	String PORT;
	String ControllerCode;
	BaseChannel  channel;
	@Override
	public SampleResult execute(Context context, List<ActionParameter> parameters) {
		SampleResult result = new SampleResult();

		for(ActionParameter parameter:parameters) {
		
			switch(parameter.getName()) {
			
			
			case ISO8583DisconnectAction.HOST:
				HOST = parameter.getValue();
				break;
			case ISO8583DisconnectAction.PORT:
				PORT = parameter.getValue();
				break;
			case ISO8583DisconnectAction.ControllerCode:
				ControllerCode = parameter.getValue();
				break;
			
			}
			
		}
		
		if (Strings.isNullOrEmpty(HOST)) {
			return getErrorResult(context, result, "Invalid argument: Missing parameter "
					+ ISO8583DisconnectAction.HOST + ".");
		}
		if (Strings.isNullOrEmpty(PORT)) {
			return getErrorResult(context, result, "Invalid argument: Missing parameter "
					+ ISO8583DisconnectAction.PORT + ".");
		}
		if (Strings.isNullOrEmpty(ControllerCode)) {
			return getErrorResult(context, result, "Invalid argument: Missing parameter "
					+ ISO8583DisconnectAction.ControllerCode + ".");
		}
		
		try{
			channel=(BaseChannel )context.getCurrentVirtualUser().get(ControllerCode+"_"+HOST+":"+PORT+".SocketObj");
			if(channel.isConnected())
			{
				result.sampleStart();
				channel.disconnect();
				result.sampleEnd();
				result.setRequestContent("disconnected to :" + HOST);
				
				
			}
			
			
		} 
		catch (IOException  e) {
			return getErrorResult(context, result, "error socket"+ e.toString() );
		}
		catch(Exception e)
		{
			return getErrorResult(context, result, "Socket connection error"
					+ e.toString() );
			
		}
		return result;
	}
	private static SampleResult getErrorResult(final com.neotys.extensions.action.engine.Context context,
			final SampleResult result, final String errorMessage) {
		result.setError(true);
		result.setStatusCode("NL-ISO8583disConnect_ACTION");
		result.setResponseContent(errorMessage);
		context.getLogger().error(errorMessage);
		return result;
	}
	@Override
	public void stopExecute() {
		// TODO Auto-generated method stub

	}

}
