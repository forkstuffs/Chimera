/*
 * The MIT License
 *
 * Copyright 2017 Karus Labs.
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
package com.karuslabs.commons.command.parser;

import com.karuslabs.commons.annotation.Ignored;
import com.karuslabs.commons.locale.*;
import com.karuslabs.commons.locale.providers.Provider;
import com.karuslabs.commons.locale.resources.*;

import java.io.File;
import java.util.*;
import javax.annotation.Nonnull;

import org.bukkit.configuration.ConfigurationSection;

import static com.karuslabs.commons.locale.MessageTranslation.NONE;
import static java.util.Arrays.copyOf;
import static java.lang.System.arraycopy;


/**
 * A concrete subclass of {@code Element} which creates {@code MessageTranslation}s.
 */
public class TranslationElement extends Element<MessageTranslation> {
    
    private File folder;
    private Provider provider;
    
    
    /**
     * Constructs a {@code TranslationElement} with the specified folder which contains the {@code Resource}s, 
     * the locale {@code Provider} and no declarations.
     * 
     * @param folder the folder which contains the Resources
     * @param provider the Provider
     */
    public TranslationElement(File folder, Provider provider) {
        this(folder, provider, new HashMap<>());
    }

    /**
     * Constructs a {@code TraslationElement} with the specified folder which contains the {@code Resource}s, 
     * {@code Provider} and declarations.
     * 
     * @param folder the folder
     * @param provider the provider
     * @param declarations the declarations
     */
    public TranslationElement(File folder, Provider provider, Map<String, MessageTranslation> declarations) {
        super(declarations);
        this.folder = folder;
        this.provider = provider;
    }

    
    /**
     * Returns an empty {@code MessageTranslation}.
     * 
     * @param config the ConfigurationSection
     * @param key the key
     * @return an empty MessageTranslation
     */
    @Override
    protected @Nonnull MessageTranslation handleNull(@Ignored ConfigurationSection config, @Ignored String key) {
        return NONE;
    }
    
    /**
     * Checks if the specified key in the {@code ConfigurationSection} is well-formed.
     * 
     * Returns {@code true} if the specified key contains a {@code bundle} name, and either or both
     * {@code embedded} and {@code folder} paths; else throws a {@code ParserException}
     * 
     * @param config the ConfigurationSection
     * @param key the key
     * @return true if the specified key in the ConfigurationSeciton is well-formed
     * @throws ParserException if the specified key in the ConfigurationSection is malformed
     */
    @Override
    protected boolean check(@Nonnull ConfigurationSection config, @Nonnull String key) {
        config = config.getConfigurationSection(key);
        if (!config.isString("bundle")) {
            throw new ParserException("Missing/invalid value: " + config.getCurrentPath() + ".bundle");
            
        } else if (!config.isList("embedded") && !config.isList("folder")) {
            String path = config.getCurrentPath();
            throw new ParserException("Missing keys/invalid values: " + path + ".embedded and/or " + path + ".folder");
            
        } else {
            return true;
        }
    }
    
    /**
     * Returns the value associated with the specified key in the {@code ConfigurationSection}.
     * 
     * @param config the ConfigurationSection
     * @param key the key
     * @return the value associated with the specified key
     */
    @Override
    protected String getDeclaredKey(ConfigurationSection config, String key) {
        return config.getString(key);
    }

    /**
     * Creates a {@code MessageTranslation} from the specified key in the {@code ConfigurationSection}.
     * 
     * @param config the configurationSection
     * @param key the key
     * @return a MessageTranslation
     */
    @Override
    protected @Nonnull MessageTranslation handle(@Nonnull ConfigurationSection config, @Nonnull String key) {
        ConfigurationSection translation = config.getConfigurationSection(key);
        
        Resource[] embedded = translation.getStringList("embedded").stream().map(EmbeddedResource::new).toArray(Resource[]::new);
        Resource[] folders = translation.getStringList("folder").stream().map(path -> new FileResource(new File(folder, path))).toArray(Resource[]::new);
        
        Resource[] resources = copyOf(embedded, embedded.length + folders.length);
        arraycopy(folders, 0, resources, embedded.length, folders.length);
        
        return new MessageTranslation(translation.getString("bundle"), new ExternalControl(resources), provider);
    }
    
}
