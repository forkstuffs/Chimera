/*
 * The MIT License
 *
 * Copyright 2018 Karus Labs.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.karuslabs.plugin.annotations.processors;

import com.karuslabs.commons.util.Null;
import com.karuslabs.plugin.annotations.annotations.Information;

import java.util.List;

import org.apache.maven.project.MavenProject;
import org.apache.maven.model.Developer;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;


public class InformationProcessor implements Processor {
    
    private MavenProject project;
    
    
    public InformationProcessor(MavenProject project) {
        this.project = project;
    }
    
    
    @Override
    public void execute(Class<? extends JavaPlugin> plugin, ConfigurationSection config) {
        Information information = plugin.getAnnotation(Information.class);
        
        description(information, config);
        config.set("author", Null.ifEmpty(information.author()));
        authors(information, config);
        config.set("prefix", Null.ifEmpty(information.prefix()));
        website(information, config);
        config.set("database", Null.ifEquals(information.database(), false));
        config.set("depend", Null.ifEmpty(information.depend()));
        config.set("softdepend", Null.ifEmpty(information.softdepend()));
        config.set("loadbefore", Null.ifEmpty(information.loadbefore()));
        config.set("load", information.load().getName());
    }
    
    protected void description(Information information, ConfigurationSection config) {
        if (information.description().equals("${project.description}")) {
            config.set("description", project.getDescription());
            
        } else {
            config.set("description", information.description());
        }
    }
    
    protected void authors(Information information, ConfigurationSection config) {
        if (information.authors().length == 1 && information.authors()[0].equals("${project.developers}")) {
            List<Developer> developers = project.getDevelopers();
            config.set("authors", developers.stream().map(Developer::getName).collect(toList()));
            
        } else if (information.authors().length > 0) {
            config.set("authors", asList(information.authors()));
        }
    }
    
    protected void website(Information information, ConfigurationSection config) {
        if (information.website().equals("${project.url}")) {
            config.set("website", project.getUrl());
            
        } else {
            config.set("website", project.getUrl());
        }
    }

    
    @Override
    public boolean isAnnotated(Class<? extends JavaPlugin> plugin) {
        return plugin.getAnnotation(Information.class) != null;
    }
    
}