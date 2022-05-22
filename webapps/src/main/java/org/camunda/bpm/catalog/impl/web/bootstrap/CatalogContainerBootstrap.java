/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.catalog.impl.web.bootstrap;

import org.camunda.bpm.catalog.Catalog;
import org.camunda.bpm.catalog.impl.DefaultCatalogRuntimeDelegate;
import org.camunda.bpm.container.RuntimeContainerDelegate;
import org.camunda.bpm.engine.rest.util.WebApplicationUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Daniel Meyer
 *
 */
public class CatalogContainerBootstrap implements ServletContextListener {

  private CatalogEnvironment environment;

  @Override
  public void contextInitialized(ServletContextEvent sce) {

    environment = createCatalogEnvironment();
    environment.setup();

    WebApplicationUtil.setApplicationServer(sce.getServletContext().getServerInfo());

  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

    environment.tearDown();
  }

  protected CatalogEnvironment createCatalogEnvironment() {
    return new CatalogEnvironment();
  }

  protected static class CatalogEnvironment {

    public void tearDown() {
      Catalog.setCatalogRuntimeDelegate(null);
    }

    public void setup() {
      Catalog.setCatalogRuntimeDelegate(new DefaultCatalogRuntimeDelegate());
    }

    protected RuntimeContainerDelegate getContainerRuntimeDelegate() {
      return RuntimeContainerDelegate.INSTANCE.get();
    }
  }
}
