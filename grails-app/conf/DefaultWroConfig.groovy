import ro.isdc.wro.model.resource.processor.impl.css.CssImportPreProcessor
import ro.isdc.wro.model.resource.processor.impl.css.CssUrlRewritingProcessor
import ro.isdc.wro.model.resource.processor.impl.css.CssVariablesProcessor
import ro.isdc.wro.model.resource.processor.impl.css.JawrCssMinifierProcessor
import ro.isdc.wro.model.resource.processor.impl.js.JSMinProcessor
import ro.isdc.wro.model.resource.processor.impl.js.SemicolonAppenderPreProcessor
import wro4j.grails.plugin.GrailsWroManagerFactory

/**
 * Boolean flag for enable/disable resource gzipping.
 */
wro.gzipResources = true
/**
 * integer value for specifying how often (in seconds) the resource changes should be checked. When this value is 0, the cache is never refreshed. When a resource change is detected, the cached group containing changed resource will be invalidated. This is useful during development, when resources are changed often.
 */
wro.resourceWatcherUpdatePeriod = 0
/**
 * flag indicating if the minimization is enabled. When this flag is false, the minimization will be
 *         suppressed for all resources.
 */
wro.minimizeEnabled = true
/**
 * Parameter allowing resources to be asynchronously processed
 */
wro.resourceWatcherAsync = false
/**
 * Parameter allowing to turn jmx on or off.
 */
wro.jmxEnabled = true
/**
 * Parameter containing an integer value for specifying how often (in seconds) the cache should be refreshed.
 */
wro.cacheUpdatePeriod = 0
/**
 * Parameter containing an integer value for specifying how often (in seconds) the model should be refreshed.
 */
wro.modelUpdatePeriod = 0
/**
 * Disable cache configuration option. When true, the processed content won't be cached in DEVELOPMENT mode. In
 * DEPLOYMENT mode changing this flag will have no effect.
 */
wro.disableCache = false
/**
 * Instructs wro4j to not throw an exception when a resource is missing.
 */
wro.ignoreMissingResources = true
/**
 * Encoding to use when reading and writing bytes from/to stream
 */
wro.encoding = null
/**
 * The fully qualified class name of the {@link ro.isdc.wro.manager.WroManagerFactory} implementation.
 */
wro.managerFactoryClassName = GrailsWroManagerFactory.name

/** PreProcessor used by wro4j.grails.plugin.GrailsWroManagerFactory   */
wro.grailsWroManagerFactory.preProcessors = [
    new CssUrlRewritingProcessor(),
    new CssImportPreProcessor(),
    new SemicolonAppenderPreProcessor(),
    new JSMinProcessor(),
    new JawrCssMinifierProcessor(),
]

/** postProcessor used by wro4j.grails.plugin.GrailsWroManagerFactory   */
wro.grailsWroManagerFactory.postProcessors = [
    new CssVariablesProcessor(),
]

/**
 * the name of MBean to be used by JMX to configure wro4j.
 */
wro.mbeanName = null
/**
 * The parameter used to specify headers to put into the response, used mainly for caching.
 */
wro.header = null

/**
 * The path to Wro.groovy. The file name can be located anywhere and named anything.
 */
wro.wroPath = "file:./grails-app/conf/Wro.groovy"

environments {
  production {
    wro.debug = false
  }
  development {
    wro.debug = true
  }
  test {
    wro.debug = true
  }
}
