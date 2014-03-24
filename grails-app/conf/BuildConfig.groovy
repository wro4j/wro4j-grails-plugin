grails.project.work.dir = 'target'

grails.project.repos.grailsCentral.username = System.getenv("GRAILS_CENTRAL_USERNAME")
grails.project.repos.grailsCentral.password = System.getenv("GRAILS_CENTRAL_PASSWORD")

grails.project.dependency.resolution = {

  inherits 'global'
  log 'warn'

  String gebVersion = "0.9.1"
  String seleniumVersion = "2.31.0"
  String wro4jVersion = "1.7.4"
  
  repositories {
    mavenLocal()
    grailsCentral()
    mavenCentral()
	if(wro4jVersion.endsWith("-SNAPSHOT")){
		mavenRepo 'https://oss.sonatype.org/content/repositories/snapshots/'
	}
  }

  dependencies {
	
    runtime("ro.isdc.wro4j:wro4j-extensions:${wro4jVersion}") {
      excludes('slf4j-log4j12', 'slf4j-api', 'spring-web', 'gmaven-runtime-1.7', 'servlet-api', 'ant', 'groovy-all')
    }

    test("org.seleniumhq.selenium:selenium-firefox-driver:$seleniumVersion",
		 "org.spockframework:spock-grails-support:0.7-groovy-2.0",
         "org.gebish:geb-spock:$gebVersion") {
      exclude 'selenium-server'
      export = false
    }

    provided("org.codehaus.groovy.modules.http-builder:http-builder:0.5.2") {
      export = false
      excludes 'nekohtml', "httpclient", "httpcore","xml-apis","groovy"
    }

    provided('net.sourceforge.nekohtml:nekohtml:1.9.15') {
      export = false
      excludes "xml-apis"
    }
  }

  plugins {
    build(":tomcat:$grailsVersion")
	
    build ':release:2.2.1', ':rest-client-builder:1.0.3', {
      export = false
    }

    test(":spock:0.7") {
      export = false
      exclude "spock-grails-support"
    }
	
	test(":geb:$gebVersion") {
      export = false
    }
  }
}
