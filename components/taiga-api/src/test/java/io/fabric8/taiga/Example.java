/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8.taiga;

import io.fabric8.kubernetes.api.KubernetesClient;

import java.util.List;
import java.util.Map;

/**
 */
public class Example {
    public static void main(String[] args) {
        String projectName = "admin-beer";
        if (args.length > 0) {
            projectName = args[0];
        }

        try {
            KubernetesClient kubernetes = new KubernetesClient();
            TaigaClient taiga = TaigaKubernetes.createTaiga(kubernetes);

            System.out.println("Connecting to taiga on: " + taiga.getAddress());

            ProjectDTO myProject = taiga.getProjectBySlug(projectName);
            System.out.println("Found project: " + myProject + " by slug: " + projectName);

            System.out.println("Project id for slug: " + myProject + " is: " + taiga.getProjectIdForSlug(projectName));

            ProjectDTO notExist = taiga.getProjectBySlug("does-not-exist");
            System.out.println("Found non existing project: " + notExist);

            Map<String, ModuleDTO> modules = taiga.getModulesForProject(projectName);
            System.out.println("Available modules for " + projectName + " are: " + modules.keySet());

            // lets try find the module for gogs
            ModuleDTO gogsModule = taiga.moduleForProject(projectName, TaigaModule.GOGS);
            System.out.println("Gogs module for " + projectName + " is " + gogsModule);

            ProjectDTO autoCreateProject = taiga.getOrCreateProject("thingy");
            System.out.println("getOrCreateProject: " + autoCreateProject);


            List<ProjectDTO> projects = taiga.getProjects();
            for (ProjectDTO project : projects) {
                System.out.println("Project " + project.getId() + " has slug: " + project.getSlug() + " name " + project.getName());
            }
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
