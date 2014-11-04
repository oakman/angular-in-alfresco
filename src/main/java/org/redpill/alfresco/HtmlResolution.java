package org.redpill.alfresco;

import java.util.ArrayList;
import java.util.List;

import org.alfresco.repo.content.MimetypeMap;
import org.apache.commons.io.IOUtils;
import org.springframework.extensions.webscripts.Description;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.util.StringUtils;

import com.github.dynamicextensionsalfresco.webscripts.AnnotationWebScriptRequest;
import com.github.dynamicextensionsalfresco.webscripts.AnnotationWebscriptResponse;
import com.github.dynamicextensionsalfresco.webscripts.resolutions.Resolution;
import com.github.dynamicextensionsalfresco.webscripts.resolutions.ResolutionParameters;

/**
 * @author Niklas Ekman (niklas.ekman@redpill-linpro.com)
 */
public class HtmlResolution implements Resolution {

  private String _html;

  public HtmlResolution(String html) {
    _html = html;
  }

  @Override
  public void resolve(AnnotationWebScriptRequest request, AnnotationWebscriptResponse response, ResolutionParameters params) throws Exception {
    addCacheControlHeaders(response, params);
    
    response.setContentType(MimetypeMap.MIMETYPE_HTML);

    response.setContentEncoding("UTF-8");

    response.setStatus(200);

    IOUtils.write(_html, response.getOutputStream(), "UTF-8");
  }

  protected void addCacheControlHeaders(final WebScriptResponse response, ResolutionParameters params) {
    final Description.RequiredCache requiredCache = params.getDescription().getRequiredCache();
    
    if (requiredCache != null) {
      final List<String> cacheValues = new ArrayList<String>(3);
      
      if (requiredCache.getNeverCache()) {
        cacheValues.add("no-cache");
        cacheValues.add("no-store");
      }
      
      if (requiredCache.getMustRevalidate()) {
        cacheValues.add("must-revalidate");
      }
      
      if (cacheValues.isEmpty() == false) {
        response.setHeader("Cache-Control", StringUtils.collectionToDelimitedString(cacheValues, ", "));
      }
    }
  }

}
