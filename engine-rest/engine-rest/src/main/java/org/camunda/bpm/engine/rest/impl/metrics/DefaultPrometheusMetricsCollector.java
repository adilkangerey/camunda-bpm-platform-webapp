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
package org.camunda.bpm.engine.rest.impl.metrics;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.metrics.Meter;

import io.prometheus.client.Collector;
import io.prometheus.client.CounterMetricFamily;

/**
 * Default collector for Camunda metrics for Prometheus
 */
public class DefaultPrometheusMetricsCollector extends Collector {

  protected final ProcessEngine processEngine;

  public DefaultPrometheusMetricsCollector(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  @Override
  public List<MetricFamilySamples> collect() {
    Map<String, Meter> dbMeters = processEngine.getManagementService().getMetrics();
    List<MetricFamilySamples> metricSamples = dbMeters.entrySet().stream().map(e -> buildCounter(e.getKey(), e.getKey(), e.getValue().get())).collect(Collectors.toList());
    // TODO demonstration: add start instance counters per process definition
    metricSamples.add(buildCounter("instancesStarted", "Number of started instances per process definition", Arrays.asList("processDefinitionKey"), Arrays.asList("testProcessA"), 55L));
    metricSamples.add(buildCounter("instancesStarted", "Number of started instances per process definition", Arrays.asList("processDefinitionKey"), Arrays.asList("testProcessB"), 22L));
    return metricSamples;
  }

  private CounterMetricFamily buildCounter(String metric, String help, Long metricValue) {
    return new CounterMetricFamily(metric, help, metricValue);
  }

  private CounterMetricFamily buildCounter(String metric, String help, List<String> labelNames, List<String> labelValues, Long metricValue) {
    final CounterMetricFamily metricFamily = new CounterMetricFamily(metric, help, labelNames);
    metricFamily.addMetric(labelValues, metricValue);
    return metricFamily;
  }
}
