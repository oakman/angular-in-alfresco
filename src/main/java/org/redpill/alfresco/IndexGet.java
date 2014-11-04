package org.redpill.alfresco;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;

import org.alfresco.service.cmr.repository.NodeService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import com.github.dynamicextensionsalfresco.webscripts.annotations.Authentication;
import com.github.dynamicextensionsalfresco.webscripts.annotations.AuthenticationType;
import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;
import com.github.dynamicextensionsalfresco.webscripts.resolutions.Resolution;

/**
 * @author Niklas Ekman (niklas.ekman@redpill-linpro.com)
 */
@Component
@WebScript(families = { "Redpill" })
@Authentication(AuthenticationType.ADMIN)
public class IndexGet {

  @Autowired
  private NodeService _nodeService;

  @Resource(name = "global-properties")
  private Properties _globalProperties;

  private Document _document;

  @Uri(method = HttpMethod.GET, value = { "/redpill/angular-demo" }, defaultFormat = "html")
  public Resolution index(WebScriptResponse response) throws IOException {
    String html = getDocument().toString();

    return new HtmlResolution(html);
  }

  public Document getDocument() throws IOException {
    if (_document == null) {
      InputStream inputStream = this.getClass().getResourceAsStream(getIndexHtmlPath());

      String html = IOUtils.toString(inputStream);

      _document = Jsoup.parse(html);

      appendBaseElement();
    }

    return _document;
  }

  private void appendBaseElement() {
    Elements baseElements = _document.getElementsByTag("base");

    Element base = baseElements.isEmpty() ? null : baseElements.get(0);

    if (base == null) {
      Element head = getHeadElement();

      if (head != null) {
        base = _document.createElement("base");

        head.child(0).before(base);
      }
    }

    if (base != null) {
      String context = _globalProperties.getProperty("alfresco.context");

      if (StringUtils.isEmpty(context)) {
        context = "alfresco";
      }

      base.attr("href", "/" + context + "/service" + getIndexAppPath());
    }
  }

  public Element getHeadElement() {
    Elements headElements = _document.getElementsByTag("head");

    return headElements.isEmpty() ? null : headElements.get(0);
  }

  public String getIndexHtmlPath() {
    return "/org/redpill/alfresco/angular-demo/app/index.html";
  }

  public String getIndexAppPath() {
    return "/redpill/angular-demo/app/";
  }

}
