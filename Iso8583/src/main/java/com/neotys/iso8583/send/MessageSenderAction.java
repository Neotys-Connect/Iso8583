package com.neotys.iso8583.send;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Icon;

import com.google.common.base.Optional;
import com.neotys.extensions.action.Action;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;

public class MessageSenderAction implements Action {
	static final String ISOMTI= "MTI";	
	static final String HOST= "Host";
	static final String PORT= "Port";
	static final String ControllerCode="ControllerCode";
	static final String Header="Header";
	static final String TIMEOUT= "Timeout";
	private static final String BUNDLE_NAME = "com.neotys.iso8583.send.bundle";
    private static final String DISPLAY_NAME = ResourceBundle.getBundle("com.neotys.iso8583.send.bundle", Locale.getDefault()).getString("displayName");
    private static final String DISPLAY_PATH = ResourceBundle.getBundle("com.neotys.iso8583.send.bundle", Locale.getDefault()).getString("displayPath");

	@Override
	public List<ActionParameter> getDefaultActionParameters() {
		List<ActionParameter> actionParameters=new ArrayList<ActionParameter>();
		actionParameters.add(new ActionParameter("Host","Host of the application"));
		actionParameters.add(new ActionParameter("Port","Port"));
		actionParameters.add(new ActionParameter(TIMEOUT,"10"));
		actionParameters.add(new ActionParameter(ControllerCode,"ControllerCode"));
		actionParameters.add(new ActionParameter(Header,"Header of the message"));
		actionParameters.add(new ActionParameter("MTI","Message Type Identifier"));
		actionParameters.add(new ActionParameter("Field2","Field 2 of the ISo8583 message"));
		return actionParameters;
	}

	@Override
	public String getDescription() {
		StringBuilder description = new StringBuilder();
		description.append("ISO8583 Action Sends messages to an open socket connection.\n")
			.append("Parameters are:\n")
			.append("- "+HOST+": Host of the application.\n")
			.append("- "+PORT+": Port.\n")
			.append("- "+TIMEOUT+": the timeout when receiving the response in seconds.\n")
			.append("- "+ControllerCode+": Controller Code (VIS,CMN,FEP...etc)\n")
			.append("- "+Header+":Header of the message\n")
			.append("- "+ISOMTI+": ISO8583 MTI of the message.\n")
			.append("- Field2: Field of the messaget\n")
			.append("- Fieldxx: Field of the message\n")
			.append("This action can accept Fields from Field2 to Field128");
		
		
		return description.toString();
	}

	@Override

	public String getDisplayName()
    {
        return DISPLAY_NAME;
    }

    public String getDisplayPath()
    {
        return DISPLAY_PATH;
    }

	@Override
	public Class<? extends ActionEngine> getEngineClass() {
		// TODO Auto-generated method stub
		return MessageSenderActionEngine.class;
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<String> getMaximumNeoLoadVersion() {
		// TODO Auto-generated method stub
		return Optional.absent();
	}

	@Override
	public Optional<String> getMinimumNeoLoadVersion() {
		// TODO Auto-generated method stub
		 return Optional.of("5.1");
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "ISO8583 Sender";
	}

}
