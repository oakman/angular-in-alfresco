package org.redpill.alfresco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.ResultSet;
import org.alfresco.service.cmr.search.SearchParameters;
import org.alfresco.service.cmr.search.SearchService;
import org.json.JSONException;
import org.json.JSONWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import com.github.dynamicextensionsalfresco.webscripts.annotations.Authentication;
import com.github.dynamicextensionsalfresco.webscripts.annotations.AuthenticationType;
import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;
import com.github.dynamicextensionsalfresco.webscripts.resolutions.JsonWriterResolution;
import com.github.dynamicextensionsalfresco.webscripts.resolutions.Resolution;

/**
 * @author Niklas Ekman (niklas.ekman@redpill-linpro.com)
 */
@Component
@WebScript(families = { "Redpill" })
@Authentication(AuthenticationType.ADMIN)
public class UsersGet {

  @Autowired
  private SearchService _searchService;

  @Autowired
  private NodeService _nodeService;

  @Uri(method = HttpMethod.GET, value = { "/redpill/angular-demo/users" }, defaultFormat = "json")
  public Resolution users(WebScriptResponse response) {
    final List<Map<String, Serializable>> nodes = findUsers();

    return new JsonWriterResolution() {

      @Override
      protected void writeJson(JSONWriter jsonWriter) throws JSONException {
        jsonWriter.object().key("total").value(nodes.size()).key("data").value(nodes).endObject();
      }

    };

  }

  private List<Map<String, Serializable>> findUsers() {
    // String query = "ASPECT:\"cm:personDisabled\"";
    String query = "TYPE:\"cm:person\"";

    SearchParameters parameters = new SearchParameters();
    parameters.setLanguage(SearchService.LANGUAGE_FTS_ALFRESCO);
    parameters.addStore(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE);
    parameters.setQuery(query);

    ResultSet result = _searchService.query(parameters);

    List<Map<String, Serializable>> nodes = new ArrayList<Map<String, Serializable>>();

    try {
      for (NodeRef nodeRef : result.getNodeRefs()) {
        String userName = (String) _nodeService.getProperty(nodeRef, ContentModel.PROP_USERNAME);
        String firstName = (String) _nodeService.getProperty(nodeRef, ContentModel.PROP_FIRSTNAME);
        String lastName = (String) _nodeService.getProperty(nodeRef, ContentModel.PROP_LASTNAME);
        String email = (String) _nodeService.getProperty(nodeRef, ContentModel.PROP_EMAIL);
        Date modified = (Date) _nodeService.getProperty(nodeRef, ContentModel.PROP_MODIFIED);
        boolean disabled = _nodeService.hasAspect(nodeRef, ContentModel.ASPECT_PERSON_DISABLED);

        Map<String, Serializable> properties = new HashMap<String, Serializable>();
        properties.put("userName", userName);
        properties.put("firstName", firstName);
        properties.put("lastName", lastName);
        properties.put("email", email);
        properties.put("disabled", disabled);

        if (modified != null) {
          properties.put("modified", modified);
        }

        nodes.add(properties);
      }
    } finally {
      result.close();
    }
    
    return nodes;
  }

}
