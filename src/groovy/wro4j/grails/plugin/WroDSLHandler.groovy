/*
* Copyright 2011 Wro4J
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package wro4j.grails.plugin

import java.util.regex.Matcher
import java.util.regex.Pattern;

import grails.util.Holders;

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource

/**
 * Helper to have an updated version of Wro.groovy DSL
 *
 * When grails detects that Wro.groovy has changed, the classLoader is not reloaded. This is a problem.
 * So we load Wro.groovy with WroDSLHandler#loadDefaultDsl() the first time from the Class Loader
 * And, each time grails detects that Wro.groovy has changed, we manually update the DSL by calling WroDSLHandler#setDsl(Script)
 *
 * @author Filirom1
 */
class WroDSLHandler {

  private static final Log LOG = LogFactory.getLog(this)

  static private Class<Script> dslClass

  static synchronized Script getDsl() {
    if (dslClass == null) {
      dslClass = loadDefaultDsl()
    }
    dslClass.newInstance()
  }

  public static Class<Script> loadDslResource(Resource resource){
	ClassLoader classLoader = Holders.grailsApplication.classLoader
	GroovyClassLoader groovyClassLoader;
	if(classLoader instanceof GroovyClassLoader)
		groovyClassLoader = classLoader
	else
		groovyClassLoader = new GroovyClassLoader(classLoader)
    return (Class<Script>) groovyClassLoader.parseClass(resource.getFile())
  }

  /** Load the DSL from the default class loader      */
  private static Class<Script> loadDefaultDsl() {
	Pattern pattern = Pattern.compile('$file:\\./grails-app/conf/(.+)\\.groovy^')
	Matcher matcher = pattern.matcher(WroConfigHandler.config.wroPath)
	if(matcher.matches()){
		String clazzName = matcher.group(1).replace('/', '.').replace('\\', '.')
		LOG.debug("wroPath is configured to point to a location which is commonly compiled. Loading the compiled class named '${clazzName}'")
		try{
			return Holders.grailsApplication.classLoader.loadClass(clazzName)
		}catch(ClassNotFoundException e){
			LOG.debug("Failed to load class '${clazzName}', falling back to reading the uncompiled file", e)
		}
	}
	try{
		return loadDslResource(Holders.applicationContext.getResource(WroConfigHandler.config.wroPath))
	}catch(Throwable e){
		LOG.fatal("Failed to load configuration from wroPath. wroPath: ${WroConfigHandler.config.wroPath}", e)
		throw e
	}
  }

  static synchronized void setDslClass(Class<Script> dslClass) {
    this.dslClass = dslClass
  }
}
