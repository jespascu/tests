package com.personal.baby.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateResolver {
	
	private static Logger logger = LoggerFactory.getLogger(TemplateResolver.class);

	private List<String> tokensToReplace;
	
	private String templatePath;

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public void setTokensToReplace(List<String> tokensToReplace) {
		this.tokensToReplace = tokensToReplace;
	}


	public String resolveTemplate(Map<String,String> params, String template) throws IOException{
		String resval = "";
		

		InputStream fileOK = this.getClass().getClassLoader().getResourceAsStream("/META-INF/".concat(template));
			
		
		String templateHtmlText = this.readFile(fileOK);
		
	
	    resval = this.populateTemplate(templateHtmlText, params);
			
			
		return resval;
	}

	private String populateTemplate(String templateHtmlText, Map<String,String> params) {
		String result = templateHtmlText;

		if(this.tokensToReplace != null && !tokensToReplace.isEmpty() && params != null){
			VelocityEngine ve = new VelocityEngine();
			ve.setProperty( RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.NullLogChute" );
			ve.init();

			VelocityContext context = new VelocityContext();
			for(String token : tokensToReplace){
				if(params.containsKey(token)){
					context.put(token, params.get(token));
				}
			}
			StringWriter writer = new StringWriter();
			Velocity.evaluate(context, writer, "TemplateReplacement", templateHtmlText);
			
			result = writer.toString();
		}

		return result;
	}

	private String readFile( InputStream file ) throws IOException {
		
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(file, "UTF8"));
		
		String         line = null;
		StringBuilder  stringBuilder = new StringBuilder();
		String         ls = System.getProperty("line.separator");

		try {
			while( ( line = reader.readLine() ) != null ) {
				stringBuilder.append( line );
				stringBuilder.append( ls );
			}

			return stringBuilder.toString();
		}catch(IOException e){ 
			logger.error(e.getLocalizedMessage(), e);
			throw e;
		}finally {
			reader.close();
		}
	}


}
