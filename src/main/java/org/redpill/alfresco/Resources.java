package org.redpill.alfresco;

import java.io.IOException;

import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import com.github.dynamicextensionsalfresco.webscripts.annotations.Authentication;
import com.github.dynamicextensionsalfresco.webscripts.annotations.AuthenticationType;
import com.github.dynamicextensionsalfresco.webscripts.annotations.FormatStyle;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Transaction;
import com.github.dynamicextensionsalfresco.webscripts.annotations.TransactionType;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.UriVariable;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;
import com.github.dynamicextensionsalfresco.webscripts.support.AbstractBundleResourceHandler;

/**
 * Web Script for handling requests for static resources for the Web Console.
 * <p>
 * This implementation maps resource paths to static.
 * 
 * @author Niklas Ekman (niklas.ekman@redpill-linpro.com)
 */
@Component
@WebScript(families = "Redpill")
@Authentication(AuthenticationType.NONE)
@Transaction(TransactionType.NONE)
public class Resources extends AbstractBundleResourceHandler {

  /* State */

  private final String _packagePath;

  /* Main operations */

  public Resources() {
    _packagePath = this.getClass().getPackage().getName().replace('.', '/') + "/angular-demo/app";
  }

  @Uri(value = "/redpill/angular-demo/app/{path}", formatStyle = FormatStyle.ARGUMENT)
  public void handleResources(@UriVariable final String path, final WebScriptResponse response) throws IOException {
    handleResource(path, response);
  }

  /* Utility operations */

  @Override
  protected String getBundleEntryPath(final String path) {
    String result = String.format("%s/%s", _packagePath, path);

    return result;
  }
}
