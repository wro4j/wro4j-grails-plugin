package wro4j.grails.plugin

import org.codehaus.groovy.grails.web.context.ServletContextHolder;

import ro.isdc.wro.http.support.ServletContextAttributeHelper
import ro.isdc.wro.manager.WroManager
import ro.isdc.wro.model.resource.ResourceType;

class WroTagLib {
  static namespace = 'wro'

  static returnObjectForTags = ['wroUrl']

  /**
   * Render a WRO CSS group
   *
   * @attr group REQUIRED the group to render
   * @attr absolute if true, will return an absolute URL. If false, a relative URL is returned. Defaults to false.
   *
   * @emptyTag
   */
  def css = { attrs ->
    String group = attrs.group
	boolean absolute = attrs.boolean("absolute", false);
	if(group == null) throw new IllegalArgumentException("group is required")
	out << """<link rel="stylesheet" type="text/css" href="${wro.wroUrl(group: group, absolute: absolute, type: 'CSS')}" />"""
  }

  /**
   * Render a WRO JS group
   *
   * @attr group REQUIRED the group to render
   * @attr absolute if true, will return an absolute URL. If false, a relative URL is returned. Defaults to false.
   *
   * @emptyTag
   */
  def js = { attrs ->
    String group = attrs.group
	boolean absolute = attrs.boolean("absolute", false);
	if(group == null) throw new IllegalArgumentException("group is required")
	out << """<script type="text/javascript" src="${wro.wroUrl(group: group, absolute: absolute, type: 'JS')}"></script>"""
  }

  /**
   * Get the URL of a wro group
   *
   * @attr group REQUIRED the group to render
   * @attr type REQUIRED the type of the group to render (either 'css' or 'js')
   * @attr absolute if true, will return an absolute URL. If false, a relative URL is returned. Defaults to false.
   *
   * @emptyTag
   */
  String wroUrl = { attrs ->
	  boolean absolute = attrs.boolean("absolute", false);
	  String group = attrs.group
	  if(group == null) throw new IllegalArgumentException("group is required")
	  ResourceType type = ResourceType.get(attrs.type);

	  ServletContextAttributeHelper servletContextAttributeHelper = new ServletContextAttributeHelper(ServletContextHolder.getServletContext());
	  WroManager wroManager =
		  servletContextAttributeHelper.getManagerFactory().create()
	  String contextPath = request.contextPath
	  if(! contextPath.endsWith("/")) contextPath = contextPath + "/"
	  boolean minimize = servletContextAttributeHelper.getWroConfiguration().isMinimizeEnabled()
	  return "${contextPath}wro/${wroManager.encodeVersionIntoGroupPath(group, type, minimize)}"
  }
}
