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

module.exports = function(config, watchConf) {
  'use strict';

  var options = {
    livereload: false
  };

  watchConf.catalog_assets = {
    options: options,
    files: [
      '<%= pkg.gruntConfig.catalogSourceDir %>/{fonts,images}/**/*',
      '<%= pkg.gruntConfig.catalogSourceDir %>/scripts/index.html',
      '<%= pkg.gruntConfig.catalogSourceDir %>/scripts/favicon.ico'
    ],
    tasks: ['copy:catalog_assets', 'copy:catalog_index']
  };

  watchConf.catalog_styles = {
    options: options,
    files: [
      '<%= pkg.gruntConfig.catalogSourceDir %>/styles/**/*.{css,less}',
      '<%= pkg.gruntConfig.catalogSourceDir %>/scripts/**/*.{css,less}'
    ],
    tasks: ['less:catalog_styles']
  };

  watchConf.catalog_plugin_styles = {
    options: options,
    files: [
      '<%= pkg.gruntConfig.pluginSourceDir %>/catalog/plugins/**/*.{css,less}'
    ],
    tasks: ['less:catalog_plugin_styles']
  };

  watchConf.catalog_scripts_lint = {
    options: options,
    files: ['<%= pkg.gruntConfig.catalogSourceDir %>/scripts/**/*.js'],
    tasks: ['newer:eslint:catalog_scripts']
  };

  watchConf.catalog_plugins_lint = {
    options: options,
    files: ['<%= pkg.gruntConfig.pluginSourceDir %>/catalog/plugins/**/*.js'],
    tasks: ['newer:eslint:catalog_plugins']
  };

  watchConf.catalog_dist = {
    options: {
      livereload: config.livereloadPort || false
    },
    files: [
      '<%= pkg.gruntConfig.catalogBuildTarget %>/**/*.{css,html,js}',
      '<%= pkg.gruntConfig.pluginBuildTarget %>/catalog/**/*.{css,html,js}'
    ]
  };
};
